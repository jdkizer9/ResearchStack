package org.researchstack.foundation.components.presentation

import java.lang.ref.WeakReference

open class TaskAction
public class GoBackwardTaskAction: TaskAction()
public class GoForwardTaskAction: TaskAction()

public interface TaskActionHandler {
    fun canHandleAction(action: TaskAction, actionManager: TaskActionManager): Boolean
    fun handleAction(action: TaskAction, actionManager: TaskActionManager): Boolean
}

public class NavigationTaskActionHandler(val presentationDelegate: WeakReference<PresentationDelegate>): TaskActionHandler {

    public interface PresentationDelegate {
        fun showNextStep()
        fun showPreviousStep()
    }

    override fun canHandleAction(action: TaskAction, actionManager: TaskActionManager): Boolean {
        return action is GoBackwardTaskAction || action is GoForwardTaskAction
    }

    override fun handleAction(action: TaskAction, actionManager: TaskActionManager): Boolean {
        val handled: Boolean? = this.presentationDelegate.get()?.let {
            if (action is GoBackwardTaskAction) {
                it.showPreviousStep()
                true
            }
            else if (action is GoForwardTaskAction) {
                it.showNextStep()
                true
            }
            else {
                false
            }
        }

        return handled ?: false
    }

}

open class TaskActionManager(val actionHandlers: List<TaskActionHandler>) {

    open fun handleAction(action: TaskAction): Boolean {

        for (actionHandler in actionHandlers) {
            if (actionHandler.canHandleAction(action, this)) {
                val handled = actionHandler.handleAction(action, this)
                if (handled) {
                    return true
                }
            }
        }

        return false

    }

}