package webApp;

/*
 * Message between client and server describing a question
 */
public class QuestionMessage {

    private final String type;
    private final String question;
    private final String min;
    private final String max;

    /**
     * An opened ended question message constructor
     * @param type
     * @param question
     */
    public QuestionMessage(String type, String question) {
        this.type = type;
        this.question = question;
    }

    /**
     * A numeric based question message constructor with boundaries
     * @param type
     * @param question
     * @param min
     * @param max
     */
    public QuestionMessage(String type, String question, String min, String max) {
        this.type = type;
        this.question = question;
        this.min = min;
        this.max = max;
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

    public String getMin() { return min; }

    public String getMax() { return max; }
}
