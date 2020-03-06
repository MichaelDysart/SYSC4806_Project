package survey;

import javax.persistence.Entity;
import java.util.ArrayList;

/**
 * Numerical type question
 */

@Entity
public class NumberQuestion extends Question {

    private ArrayList<Integer> answers = new ArrayList<>();
    protected String min;
    protected String max;

    /**
     * Default constructor
     */
    public NumberQuestion() {
        super();
    }

    /**
     * Constructor with specified question
     * @param question
     */
    public NumberQuestion(String question, String min, String max) {

        super("number_question", question);
        this.min = min;
        this.max = max;

    }

    /**
     * Get the minimum boundary for a question of type number_question
     * @return String
     */
    public String getMin() {
        return min;
    }

    /**
     * Set the minimum boundary for a question of type number_question
     * @param min
     */
    public void setMin(String min) {
        this.min = min;
    }

    /**
     * Get the maximum boundary for a question of type number_question
     * @return
     */
    public String getMax() { return max; }

    /**
     * Set the maximum boundary for a question of type number_question
     */
    public void setMax() { this.max = max; }


    /**
     * Retrieves the answers
     * @return {ArrayList<Integer>}
     */
    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    /**
     * Sets the answers
     * @param answers {ArrayList<Integer>}
     */
    public void setAnswers(ArrayList<Integer> answers) {
        this.answers = answers;
    }


    /**
     * Adds a new answer
     * @param answer {Integer}
     */
    public void addAnswer(Integer answer) {
        this.answers.add(answer);
    }
}
