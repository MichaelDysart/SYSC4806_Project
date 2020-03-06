package survey;

import javax.persistence.Entity;
import java.util.ArrayList;

/**
 * Numerical type question
 */

@Entity
public class NumberQuestion extends Question {

    private ArrayList<String> answers = new ArrayList<>();
    private int min;
    private int max;

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
    public NumberQuestion(String question, int min, int max) {
        super("number_question", question);
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /*
     * Retrieves the answers
     * @return {ArrayList<String>}
     */
    public ArrayList<String> getAnswers() {
        return answers;
    }

}
