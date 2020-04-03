package survey;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Numerical type question
 */

@Entity
public class NumberQuestion extends Question {

    @ElementCollection
    private Collection<Integer> answers = new ArrayList<>();
    protected int min;
    protected int max;

    /**
     * Default constructor
     */
    public NumberQuestion() {
        super();
    }

    /**
     * Constructor with specified question
     * @param question the question text
     */
    public NumberQuestion(String question, int min, int max) {

        super(question);
        this.min = min;
        this.max = max;

    }

    /**
     * Get the minimum boundary for a question of type number_question
     * @return String
     */
    public int getMin() {
        return min;
    }

    /**
     * Set the minimum boundary for a question of type number_question
     * @param min the minimum value for the question
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get the maximum boundary for a question of type number_question
     * @return the maximum value
     */
    public int getMax() { return max; }

    /**
     * Set the maximum boundary for a question of type number_question
     */
    public void setMax(int max) { this.max = max; }


    /**
     * Retrieves the answers
     * @return {ArrayList<Integer>}
     */
    public Collection<Integer> getAnswers() {
        return answers;
    }

    /**
     * Sets the answers
     * @param answers {ArrayList<Integer>}
     */
    public void setAnswers(Collection<Integer> answers) {
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
