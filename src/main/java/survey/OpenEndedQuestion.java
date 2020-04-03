package survey;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;

/*
 * A class to store open ended questions
 */
@Entity
public class OpenEndedQuestion extends Question {

    @ElementCollection
    private Collection<String> answers = new ArrayList<>();

    /*
     * A default constructor
     */
    public OpenEndedQuestion() {
        super();
    }

    /*
     * A constructor
     * @param question {String}
     */
    public OpenEndedQuestion(String question) {
        super(question);
    }

    /*
     * Retrieves the answers
     * @return {ArrayList<String>}
     */
    public Collection<String> getAnswers() {
        return answers;
    }

    /*
     * Sets the answers
     * @param answers {ArrayList<String>}
     */
    public void setAnswers(Collection<String> answers) {
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
