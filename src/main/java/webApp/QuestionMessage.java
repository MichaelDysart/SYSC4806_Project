package webApp;

/*
 * Message between client and server describing a question
 */
public class QuestionMessage {

    private final String type;
    private final String question;

    /*
     * A constructor
     * @param type {String}
     * @param question {String}
     */
    public QuestionMessage(String type, String question) {
        this.type = type;
        this.question = question;
    }

    /*
     * Retrieve the question type
     * @returns {string}
     */
    public String getType() {
        return type;
    }

    /*
     * Retrieve the question string
     * @returns {string}
     */
    public String getQuestion() {
        return question;
    }
}
