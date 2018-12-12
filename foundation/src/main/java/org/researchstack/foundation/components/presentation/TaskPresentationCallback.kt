package org.researchstack.foundation.components.presentation

import org.researchstack.foundation.core.interfaces.IResult
import org.researchstack.foundation.core.interfaces.IStep
import org.researchstack.foundation.core.interfaces.ITask

abstract class TaskPresentationCallback<StepType: IStep, ResultType: IResult, TaskType: ITask<StepType, ResultType>> {
    abstract fun onTaskPresentationFinished(task: TaskType, result: ResultType?)
}