package survey;

import javax.persistence.Entity;
import java.util.ArrayList;

/**
 * A question type that allows selecting one option from a dropdown of many
 */

@Entity
public class DropdownQuestion extends Question {

    private ArrayList<String> options;
    private ArrayList<String> answers = new ArrayList<>();

    /**
     * Default constructor
     */
    public DropdownQuestion() {
        super();
    }

    /**
     * Constructor with specified question
     * @param question the question text
     * @param options the possible options to choose from
     */
    public DropdownQuestion(String question, ArrayList<String> options) {

        super(question);
        this.options = options;

    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    /**
     * Adds a new answer
     * @param answer {String}
     */
    public void addAnswer(String answer) {
        this.answers.add(answer);
    }
}
