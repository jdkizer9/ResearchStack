package org.researchstack.foundation.components.survey.ui.body;

import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;
import org.researchstack.foundation.components.survey.answerformat.BooleanAnswerFormat;
import org.researchstack.foundation.components.survey.step.FormStep;
import org.researchstack.foundation.components.survey.step.QuestionStep;
import org.researchstack.foundation.core.models.result.StepResult;

import static org.junit.Assert.*;

public class FormBodyTest {
    @Test
    public void shouldUpdateResultEndDate() {
        QuestionStep questionStep = new QuestionStep("any");
        questionStep.setAnswerFormat(new BooleanAnswerFormat("Y", "N"));
        SingleChoiceQuestionBody<Boolean> singleChoiceQuestionBody = new SingleChoiceQuestionBody<>(questionStep, null);

        FormStep formStep = new FormStep("any", "any", "any");
        formStep.setFormSteps(questionStep);

        FormBody formBody = new FormBody(formStep, null);
        formBody.result.setEndDate(null);
        formBody.formStepChildren = new ArrayList<>();

        formBody.formStepChildren.add(singleChoiceQuestionBody);

        StepResult stepResult = formBody.getStepResult(false);
        Date endDate = stepResult.getEndDate();
        assertNotEquals(endDate, null);
    }
}
