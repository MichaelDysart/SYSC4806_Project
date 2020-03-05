package webApp;

import java.util.Collection;

/*
 * Message between client and server describing a survey
 */
public class SurveyMessage {

    private final String name;
    private final Collection<QuestionMessage> questions;

    /*
     * A constructor
     * @param name {String}
     * @param questions {Collection<QuestionMessage>}
     */
    public SurveyMessage(String name, Collection<QuestionMessage> questions) {
        this.questions = questions;
        this.name = name;
    }

    /*
     * Retrieve the survey name
     * @returns {string}
     */
    public String getName() {
        return name;
    }

    /*
     * Retrieve the list of questions
     * @returns {Collection<QuestionMessage>}
     */
    public Collection<QuestionMessage> getQuestions() {
        return questions;
    }
}
