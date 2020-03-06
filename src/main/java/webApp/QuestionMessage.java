package webApp;

/*
 * Message between client and server describing a question
 */
public class QuestionMessage {

    private final String type;
    private final String question;
    private final int min;
    private final int max;

    /**
     * A numeric based question message constructor with boundaries
     * @param type  the question type
     * @param question  the question text
     * @param min   the minimum value
     * @param max   the maximum value
     */
    public QuestionMessage(String type, String question, int min, int max) {
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

    public int getMin() { return min; }

    public int getMax() { return max; }
}
