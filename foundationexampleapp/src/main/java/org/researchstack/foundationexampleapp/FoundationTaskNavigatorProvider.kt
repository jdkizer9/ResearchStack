package org.researchstack.foundationexampleapp

import android.content.Context
import org.researchstack.foundation.components.presentation.interfaces.ITaskNavigator
import org.researchstack.foundation.components.presentation.interfaces.ITaskNavigatorProvider
import org.researchstack.foundation.core.models.result.TaskResult
import org.researchstack.foundation.core.models.step.Step
import org.researchstack.foundation.core.models.task.Task
import org.researchstack.kotlinbackbonesampleapp.ConditionalTaskNavigator
import org.researchstack.kotlinbackbonesampleapp.FoundationTaskProvider
import org.researchstack.kotlinbackbonesampleapp.FoundationTaskProvider.Companion.NAVIGATION_TASK
import java.util.concurrent.locks.Condition

open class FoundationTaskNavigatorProvider(val context: Context, val taskProvider: FoundationTaskProvider): ITaskNavigatorProvider<Step, TaskResult> {
    override fun taskNavigator(identifier: String): ITaskNavigator<Step, TaskResult>? {
        val task = taskProvider.task(identifier) as? Task ?: return null

        return if (identifier == NAVIGATION_TASK) {
            ConditionalTaskNavigator(task, mapOf())
        }
        else {
            task
        }
    }
}