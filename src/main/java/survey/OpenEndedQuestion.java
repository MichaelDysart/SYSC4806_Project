package survey;

import java.util.ArrayList;

public class OpenEndedQuestion extends Question {
    private ArrayList<String> answers = new ArrayList<String>();

    /*
     * A constructor
     * @param question {String}
     */
    public OpenEndedQuestion(String question) {
        super("open_ended", question);
    }

    /*
     * Retrieves the answers
     * @return {ArrayList<String>}
     */
    public ArrayList<String> getAnswers() {
        return answers;
    }

    /*
     * Sets the answers
     * @param answers {ArrayList<String>}
     */
    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
    }
}
