package survey;

import javax.persistence.Entity;
import java.util.ArrayList;

/*
 * A class to store open ended questions
 */
@Entity
public class OpenEndedQuestion extends Question {

    private ArrayList<String> answers = new ArrayList<>();

    /*
     * A default constructor
     * @param question {String}
     */
    public OpenEndedQuestion() {
        super();
    }

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


    /*
     * Adds a new answer
     * @param answer {String}
     */
    public void addAnswer(String answer) {
        this.answers.add(answer);
    }

}
