package survey;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A question type that allows selecting one option from a dropdown of many
 */

@Entity
public class DropdownQuestion extends Question {

    @ElementCollection
    private Collection<String> options;
    @ElementCollection
    private Collection<String> answers = new ArrayList<>();

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
    public DropdownQuestion(String question, Collection<String> options) {

        super(question);
        this.options = options;

    }

    public Collection<String> getOptions() {
        return options;
    }

    public void setOptions(Collection<String> options) {
        this.options = options;
    }

    public Collection<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<String> answers) {
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
