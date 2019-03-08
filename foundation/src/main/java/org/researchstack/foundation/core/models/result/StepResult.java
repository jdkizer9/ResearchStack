package org.researchstack.foundation.core.models.result;

//import org.researchstack.foundation.step.QuestionStep;
//import org.researchstack.foundation.step.Step;

import org.researchstack.foundation.core.models.step.Step;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The StepResult class represents a result produced by a {@link org.researchstack.foundation.ui.step.layout.StepLayout}
 * to hold all child results of type <code>T</code> produced by the step.
 * <p>
 * A step result is typically generated by the framework as the task proceeds. When the task
 * completes, it may be appropriate to serialize it for transmission to a server, or to immediately
 * perform analysis on it.
 * <p>
 * For example, an {@link QuestionStep} object produces a result of type <code>T</code> that becomes
 * a child of the {@link StepResult} object.
 */
public class StepResult<T> extends Result {
    /**
     * When StepResult only has a single value, pair that value with the following key
     */
    public static final String DEFAULT_KEY = "answer";

    private Map<String, T> results;

    private Step step;

    //TODO: See if we can remove this... What if we remove this for foundation, but add it for backwards compat foundation
//    private AnswerFormat answerFormat;

    /**
     * Creates a StepResult from a {@link Step}.
     * <p>
     * Using this constructor ensures that the StepResult has the correct identifier and answer
     * format for the corresponding step.
     *
     * @param step the step from which to create the StepResult
     */
    public StepResult(Step step) {
        super(step.getIdentifier());
        this.results = new HashMap<>();
        this.step = step;

//        if (step instanceof QuestionStep) {
//            answerFormat = ((QuestionStep) step).getAnswerFormat();
//        }
        setStartDate(new Date());
        // this will be updated when the result is set
        updateEndDate();
    }

    public Map<String, T> getResults() {
        return results;
    }

    public void setResults(Map<String, T> results) {
        this.results = results;
    }

    /**
     * Returns the result stored using {@link #setResult}.
     *
     * @return the result with the default identifier
     */
    public T getResult() {
        return getResultForIdentifier(DEFAULT_KEY);
    }

    /**
     * Sets the result using the default key, useful when there is only a single result.
     *
     * @param result the result to save with the default key
     */
    public void setResult(T result) {
        setResultForIdentifier(DEFAULT_KEY, result);
        updateEndDate();
    }

    /**
     * Returns the result for the given identifier, use this when there are multiple results for the
     * step.
     *
     * @param identifier the identifier used as the key for storing this result
     * @return the result for the given identifier
     */
    public T getResultForIdentifier(String identifier) {
        return results.get(identifier);
    }

    /**
     * Sets the result for the given identifier, use when there are multiple results for the step.
     * <p>
     * If there is only one result, use the {@link #setResult} convenience method instead.
     *
     * @param identifier the identifier for the result
     * @param result     the result to save
     */
    public void setResultForIdentifier(String identifier, T result) {
        results.put(identifier, result);
    }

    public void updateEndDate() {
        setEndDate(new Date());
    }

    //TODO: See if we can remove this... What if we remove this for foundation, but add it for backwards compat foundation
//    /**
//     * Gets the {@link AnswerFormat} for this step result. May be useful when processing the
//     * result.
//     *
//     * @return the answer format associated with the step
//     */
//
//    public AnswerFormat getAnswerFormat() {
//        return answerFormat;
//    }

    public Step getStep() {
        return this.step;
    }
}

