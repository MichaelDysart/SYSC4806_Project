package webApp;

import java.util.Collection;

/*
 * Message between client and server describing a survey
 */
public class SurveyMessage {

    private final Integer id;
    private final String name;
    private final String status;
    private final Collection<QuestionMessage> questions;
    private final boolean closed;

    /*
     * A constructor
     * @param id {Integer}
     * @param status {String}
     * @param name {String}
     * @param questions {Collection<QuestionMessage>}
     */
    public SurveyMessage(Integer id, String status, String name, boolean closed, Collection<QuestionMessage> questions) {
        this.questions = questions;
        this.name = name;
        this.id = id;
        this.status = status;
        this.closed = closed;
    }

    /*
     * Retrieve the survey id
     * @returns {Integer}
     */
    public Integer getId() {
        return id;
    }

    /*
     * Retrieve the status of the operation
     * @returns {string}
     */
    public String getStatus() {
        return status;
    }

    /*
     * Retrieve the survey name
     * @returns {string}
     */
    public String getName() {
        return name;
    }

    /*
     * Retrieve the closed status
     * @returns {boolean}
     */
    public boolean getClosed() {
        return closed;
    }

    /*
     * Retrieve the list of questions
     * @returns {Collection<QuestionMessage>}
     */
    public Collection<QuestionMessage> getQuestions() {
        return questions;
    }
}
