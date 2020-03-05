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
    protected long id;
    protected String type;
    protected String question;
    protected String min;
    protected String max;

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

    /**
     * A constructor with boundary parameters
     * @param type
     * @param question
     * @param min
     * @param max
     */
    public Question(String type, String question, String min, String max) {
        this.type = type;
        this.question = question;
        this.max = max;
        this.min = min;
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
}
