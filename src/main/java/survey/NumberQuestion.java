package survey;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class NumberQuestion extends Question {

    private ArrayList<String> answers = new ArrayList<>();

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
    public NumberQuestion(String question) {
        super("number_question", question);
    }

}
