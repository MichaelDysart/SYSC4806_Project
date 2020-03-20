package webApp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Message between client and server describing a question
 */
public class QuestionMessage {

    private final String type;
    private final String question;
    private final ArrayList<String> options;
    private final int min;
    private final int max;
    private final String stringAnswer;
    private final int numberAnswer;
    private final Collection<String> stringAnswerList;
    private final Collection<Integer> numberAnswerList;

    /**
     * A numeric based question message constructor with boundaries
     * @param type  the question type
     * @param question  the question text
     * @param options  the options to display to the user for the question
     * @param min   the minimum value
     * @param max   the maximum value
     * @param stringAnswer   the answer if the type is string
     * @param numberAnswer   the answer if the type is numerical
     */
    public QuestionMessage(String type, String question, ArrayList<String> options, int min, int max, String stringAnswer, int numberAnswer, Collection<String> stringAnswerList, Collection<Integer> numberAnswerList) {
        this.type = type;
        this.question = question;
        this.options = options;
        this.min = min;
        this.max = max;
        this.stringAnswer = stringAnswer;
        this.numberAnswer = numberAnswer;
        this.stringAnswerList = stringAnswerList;
        this.numberAnswerList = numberAnswerList;
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
     * Retrieve the question options
     * @returns {ArrayList<String>}
     */
    public ArrayList<String> getOptions() { return options; }

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
     * Retrieve the new answer if the type is a string
     * @returns {String}
     */
    public String getStringAnswer() { return stringAnswer; }

    /*
     * Retrieve the new answer if the type is a number
     * @returns {int}
     */
    public int getNumberAnswer() { return numberAnswer; }

    /*
     * Retrieve the list of existing answers for string types
     * @returns {Collection<String>}
     */
    public Collection<String> getStringAnswerList() { return stringAnswerList; }

    /*
     * Retrieve the list of existing answers for number types
     * @returns {Collection<Integer>}
     */
    public Collection<Integer> getNumberAnswerList() { return numberAnswerList; }
}
