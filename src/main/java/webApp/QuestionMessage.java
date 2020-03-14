package webApp;

/*
 * Message between client and server describing a question
 */
public class QuestionMessage {

    private final String type;
    private final String question;
    private final int min;
    private final int max;
    private final String stringAnswer;
    private final int numberAnswer;

    /**
     * A numeric based question message constructor with boundaries
     * @param type  the question type
     * @param question  the question text
     * @param min   the minimum value
     * @param max   the maximum value
     * @param stringAnswer   the answer if the type is string
     * @param numberAnswer   the answer if the type is numerical
     */
    public QuestionMessage(String type, String question, int min, int max, String stringAnswer, int numberAnswer) {
        this.type = type;
        this.question = question;
        this.min = min;
        this.max = max;
        this.stringAnswer = stringAnswer;
        this.numberAnswer = numberAnswer;
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

    /*
     * Retrieve the minimum value
     * @returns {int}
     */
    public int getMin() { return min; }

    /*
     * Retrieve the maximum value
     * @returns {int}
     */
    public int getMax() { return max; }

    /*
     * Retrieve the answer if the type is a string
     * @returns {String}
     */
    public String getStringAnswer() { return stringAnswer; }

    /*
     * Retrieve the answer if the type is a number
     * @returns {int}
     */
    public int getNumberAnswer() { return numberAnswer; }
}
