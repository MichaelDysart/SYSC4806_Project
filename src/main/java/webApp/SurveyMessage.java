package webApp;

import java.util.Collection;

/*
 * Message between client and server describing a survey
 */
public class SurveyMessage {

    private final Integer id;
    private final String name;
    private final Collection<QuestionMessage> questions;

    /*
     * A constructor
     * @param name {String}
     * @param questions {Collection<QuestionMessage>}
     */
    public SurveyMessage(Integer id, String name, Collection<QuestionMessage> questions) {
        this.questions = questions;
        this.name = name;
        this.id = id;
    }

    /*
     * Retrieve the survey id
     * @returns {int}
     */
    public Integer getId() {
        return id;
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
