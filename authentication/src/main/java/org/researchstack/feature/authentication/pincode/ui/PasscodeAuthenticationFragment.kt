package org.researchstack.feature.authentication.pincode.ui

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding.widget.RxTextView
import org.researchstack.feature.authentication.R
import org.researchstack.feature.authentication.pincode.PasscodeAuthenticator
import org.researchstack.foundation.components.utils.ObservableUtils
import org.researchstack.foundation.components.utils.ThemeUtils
import rx.Observable
import rx.functions.Func1

open class PasscodeAuthenticationFragment: Fragment() {

    open class AuthenticationCallback {
        open fun onAuthenticationFailed() {}
        open fun onAuthenticationSucceeded() {}
    }

    companion object {
        fun newInstance(
                authenticator: PasscodeAuthenticator,
                callback: AuthenticationCallback
        ): PasscodeAuthenticationFragment {
            val fragment = PasscodeAuthenticationFragment()
            fragment.authenticator = authenticator
            fragment.callback = callback
            return fragment
        }
    }

    private var authenticator: PasscodeAuthenticator? = null
    private var callback: AuthenticationCallback? = null
    private var toggleKeyboardAction: ((Boolean) -> Unit)? = null

    override open fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Show pincode layout
        val config = this.authenticator!!.passcodeConfig

        val themeResId = ThemeUtils.getPassCodeTheme(this.activity as Context)
        val context = ContextThemeWrapper(this.activity, themeResId)
        val pinCodeLayout = PinCodeLayout(context)
        pinCodeLayout.setBackgroundColor(Color.WHITE)

        val errorColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(R.color.rsf_error, context.theme)
        } else {
            resources.getColor(R.color.rsf_error)
        }

        val summary = pinCodeLayout.findViewById(R.id.text) as TextView
        val pincode = pinCodeLayout.findViewById(R.id.pincode) as EditText

        this.toggleKeyboardAction = { enable ->
            pincode.isEnabled = enable
            pincode.setText("")
            pincode.requestFocus()
            if (enable) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(pincode, InputMethodManager.SHOW_FORCED)
            }
        }

        //Refactor this to not use RX
        RxTextView.textChanges(pincode).map<String>(Func1<CharSequence, String> { it.toString() }).doOnNext { pin ->
            if (summary.currentTextColor == errorColor) {
                summary.setTextColor(ThemeUtils.getTextColorPrimary(this.activity))
                pinCodeLayout.resetSummaryText()
            }
        }.filter { pin -> pin != null && pin.length == config.pinLength }.doOnNext { pin ->
            pincode.isEnabled = false
            pinCodeLayout.showProgress(true)
        }.flatMap { pin ->
            //flatMap is returning an observable
            Observable.fromCallable {
                this.authenticator!!.store.checkPasscode(pin)
                true
            }.compose(ObservableUtils.applyDefault()).doOnError { throwable ->
                this.toggleKeyboardAction?.invoke(true)
                throwable.printStackTrace()
                summary.setText(R.string.rsfa_pincode_enter_error)
                summary.setTextColor(errorColor)
                pinCodeLayout.showProgress(false)
            }.onErrorResumeNext { throwable1 -> Observable.empty() }
        }.subscribe { success ->
            if (!success) {
                this.toggleKeyboardAction?.invoke(true)
                this.callback!!.onAuthenticationFailed()
            } else {
                this.callback!!.onAuthenticationSucceeded()
            }
        }

        return pinCodeLayout
    }

}