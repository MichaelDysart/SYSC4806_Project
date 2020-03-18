package survey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * A class to represent all questions
 */
@Entity
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected String question;

    /*
     * A default constructor
     */
    public Question() {
    }

    /*
     * A constructor
     * @param name {String}
     * @param question {String}
     */
    public Question(String question) {
        this.question = question;
    }

    /*
     * Retrieve the id
     * @returns {int}
     */
    public int getId() {
        return id;
    }

    /*
     * Sets the id
     * @param id {int}
     */
    public void setId(int id) {
        this.id = id;
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
