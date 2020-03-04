package survey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    protected String type;
    protected String question;

    /*
     * A default constructor
     */
    public Question(){ }

    /*
     * A constructor
     * @param name {String}
     * @param question {String}
     */
    public Question(String type, String question) {
        this.type = type;
        this.question = question;
    }

    /*
     * Retrieve the id
     * @returns {long}
     */
    public long getId() {
        return id;
    }

    /*
     * Sets the id
     * @param id {long}
     */
    public void setId(long id) {
        this.id = id;
    }

    /*
     * Retrieve the type of the question
     * @returns {String}
     */
    public String getType() {
        return type;
    }

    /*
     * Sets the type
     * @param type {String}
     */
    public void setType(String type) {
        this.type = type;
    }

    /*
     * Retrieve the question
     * @returns {String}
     */
    public String getQuestion() {
        return question;
    }

    /*
     * Sets the question
     * @param question {String}
     */
    public void setQuestion(String question) {
        this.question = question;
    }
}
