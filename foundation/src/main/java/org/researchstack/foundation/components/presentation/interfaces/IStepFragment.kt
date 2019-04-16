package org.researchstack.foundation.components.presentation.interfaces

import android.support.v4.app.Fragment
import org.researchstack.foundation.components.common.ui.callbacks.StepCallbacks
import org.researchstack.foundation.components.presentation.TaskActionManager
import org.researchstack.foundation.core.interfaces.IResult
import org.researchstack.foundation.core.interfaces.IStep

interface IStepFragment {
    fun initialize(step: IStep, result: IResult?)
    //TODO: JDK - 4/13/19 - Understand if we need to change StepCallbacks
    //This interface may change depending on work on Actions
    fun setActionManager(actionManager: TaskActionManager)

    val step: IStep
    val result: IResult?

    fun onBackPressed()
    val fragment: Fragment
}