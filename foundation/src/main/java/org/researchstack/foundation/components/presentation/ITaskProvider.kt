package org.researchstack.foundation.components.presentation

import org.researchstack.foundation.core.interfaces.IResult
import org.researchstack.foundation.core.interfaces.IStep
import org.researchstack.foundation.core.interfaces.ITask

interface ITaskProvider<StepType: IStep, ResultType: IResult> {
    fun task(identifier: String): ITask<StepType, ResultType>?
}