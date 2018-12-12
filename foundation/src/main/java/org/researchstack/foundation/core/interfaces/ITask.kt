package org.researchstack.foundation.core.interfaces

import android.content.Context
import org.researchstack.foundation.core.models.task.Task

interface ITask<StepType: IStep, ResultType: IResult> {
    val identifier: String
    @Deprecated("Only here for backwards compatibilty. Will be removed soon")
    fun getTitleForStep(context: Context, step: StepType): String
    fun getTitleForStep(step: StepType): String
    fun getStepWithIdentifier(identifier: String): StepType?
    fun getStepAfterStep(step: StepType?, result: ResultType): StepType?
    fun getStepBeforeStep(step: StepType?, result: ResultType): StepType?
    fun getProgressOfCurrentStep(step: StepType, result: ResultType): Task.TaskProgress
    fun validateParameters()
}