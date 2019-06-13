package org.researchstack.feature.survey.answerformat;


@Deprecated
public class UnknownAnswerFormat extends AnswerFormat {
    public UnknownAnswerFormat() {
    }

    @Override
    public QuestionType getQuestionType() {
        return Type.None;
    }
}
