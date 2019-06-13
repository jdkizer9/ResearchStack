package org.researchstack.feature.survey.answerformat;

import android.content.Context;

import org.researchstack.feature.survey.R;
import org.researchstack.foundation.components.utils.ResUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by TheMDP on 3/14/17.
 */

public class MoodScaleAnswerFormat extends ImageChoiceAnswerFormat {

    public static final String OVERALL                  = "rsb_mood_survey_mood";
    public static final String CLARITY                  = "rsb_mood_survey_clarity";
    public static final String PAIN                     = "rsb_mood_survey_pain";
    public static final String SLEEP                    = "rsb_mood_survey_sleep";
    public static final String EXERCISE                 = "rsb_mood_survey_exercise";
    public static final String CUSTOM                   = "rsb_mood_survey_custom";

    /**
     * @param root One of the constants above, like, OVERALL, PAIN, etc.
     * @param index the index of the image (1 through 5)
     * @return The full drawable image res based on the root
     */
    public static String normal(String root, int index) {
        return String.format(Locale.getDefault(), "%s_%dg", root, index);
    }

    public static final int MOOD_IMAGE_COUNT = 5;

    /* Default constructor needed for serialization/deserialization of object */
    public MoodScaleAnswerFormat() {
        super();
    }

    /**
     * Creates a MoodScaleAnswerFormat using the CUSTOM images, but with different hint text
     * and step result values
     * @param customTextChoices list of custom hint text that will be displayed
     *                          when the corresponding mood image is selected
     * @param customValues      lost of custom values that will be saved to the step result
     *                          when the corresponding mood image is selected
     */
    public MoodScaleAnswerFormat(List<String> customTextChoices, List<Serializable> customValues) {
        if (customTextChoices.size() != MOOD_IMAGE_COUNT ||
            customValues.size() != MOOD_IMAGE_COUNT)
        {
            throw new IllegalStateException("Both customTextChoices and customValues must" +
                    "be length " + MOOD_IMAGE_COUNT + " to match the images");
        }

        createImageChoiceList(CUSTOM, customTextChoices, customValues);
    }

    /**
     * @param context Can be app or activity, used to grab string resources
     * @param moodQuestionType the type of mood question to base images and text on
     */
    public MoodScaleAnswerFormat(Context context, MoodQuestionType moodQuestionType) {
        super();

        String imageName = null;
        List<String> textChoices = new ArrayList<>();

        switch (moodQuestionType) {
            case CLARITY:
                imageName = CLARITY;
                textChoices = Arrays.asList(
                        context.getString(R.string.rsb_MOOD_CLARITY_GREAT),
                        context.getString(R.string.rsb_MOOD_CLARITY_GOOD),
                        context.getString(R.string.rsb_MOOD_CLARITY_AVERAGE),
                        context.getString(R.string.rsb_MOOD_CLARITY_BAD),
                        context.getString(R.string.rsb_MOOD_CLARITY_TERRIBLE));
                break;
            case OVERALL:
                imageName = OVERALL;
                textChoices = Arrays.asList(
                        context.getString(R.string.rsb_MOOD_OVERALL_GREAT),
                        context.getString(R.string.rsb_MOOD_OVERALL_GOOD),
                        context.getString(R.string.rsb_MOOD_OVERALL_AVERAGE),
                        context.getString(R.string.rsb_MOOD_OVERALL_BAD),
                        context.getString(R.string.rsb_MOOD_OVERALL_TERRIBLE));
                break;
            case PAIN:
                imageName = PAIN;
                textChoices = Arrays.asList(
                        context.getString(R.string.rsb_MOOD_PAIN_GREAT),
                        context.getString(R.string.rsb_MOOD_PAIN_GOOD),
                        context.getString(R.string.rsb_MOOD_PAIN_AVERAGE),
                        context.getString(R.string.rsb_MOOD_PAIN_BAD),
                        context.getString(R.string.rsb_MOOD_PAIN_TERRIBLE));
                break;
            case SLEEP:
                imageName = SLEEP;
                textChoices = Arrays.asList(
                        context.getString(R.string.rsb_MOOD_SLEEP_GREAT),
                        context.getString(R.string.rsb_MOOD_SLEEP_GOOD),
                        context.getString(R.string.rsb_MOOD_SLEEP_AVERAGE),
                        context.getString(R.string.rsb_MOOD_SLEEP_BAD),
                        context.getString(R.string.rsb_MOOD_SLEEP_TERRIBLE));
                break;
            case EXERCISE:
                imageName = EXERCISE;
                textChoices = Arrays.asList(
                        context.getString(R.string.rsb_MOOD_EXERCISE_GREAT),
                        context.getString(R.string.rsb_MOOD_EXERCISE_GOOD),
                        context.getString(R.string.rsb_MOOD_EXERCISE_AVERAGE),
                        context.getString(R.string.rsb_MOOD_EXERCISE_BAD),
                        context.getString(R.string.rsb_MOOD_EXERCISE_TERRIBLE));
                break;
            case CUSTOM:
                imageName = CUSTOM;
                textChoices = Arrays.asList(
                        context.getString(R.string.rsb_MOOD_CUSTOM_GREAT),
                        context.getString(R.string.rsb_MOOD_CUSTOM_GOOD),
                        context.getString(R.string.rsb_MOOD_CUSTOM_AVERAGE),
                        context.getString(R.string.rsb_MOOD_CUSTOM_BAD),
                        context.getString(R.string.rsb_MOOD_CUSTOM_TERRIBLE));
                break;
        }

        createImageChoiceList(imageName, textChoices, null);
    }

    private void createImageChoiceList(
            String imageName, List<String> textChoices, List<Serializable> values)
    {
        List<ImageChoice> imageChoiceList = new ArrayList<>();
        int count = textChoices.size();
        for (int i = 0; i < count; i++) {
            String normalNameRes = normal(imageName, (i + 1));
            Serializable value = Integer.valueOf(count - i);
            if (values != null) {
                value = values.get(i);
            }
            ImageChoice answerOption = new ImageChoice(
                    normalNameRes,
                    null,
                    textChoices.get(i),
                    value);
            imageChoiceList.add(answerOption);
        }
        setImageChoiceList(imageChoiceList);
    }

    /**
     * Type of mood survey question.
     */
    public enum MoodQuestionType {
        CUSTOM,
        CLARITY,
        OVERALL,
        PAIN,
        SLEEP,
        EXERCISE
    }
}
