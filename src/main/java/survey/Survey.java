package survey;

import javax.persistence.*;
import java.util.Collection;

/*
 * A class to store surveys, comprising a list of questions
 */
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Question> questions;

    /*
     * A default constructor
     */
    public Survey(){ }

    /*
     * A constructor
     * @param name {String}
     */
    public Survey(String name){ this.name = name; }

    /*
     * A constructor
     * @param name {String}
     * @param questions {Collection<Question>}
     */
    public Survey(String name, Collection<Question> questions){
        this.name = name;
        this.questions = questions;
    }

    /*
     * Sets the id
     * @param id {int}
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /*
     * Retrieve the id
     * @returns {int}
     */
    public int getId() {
        return this.id;
    }


    /*
     * Sets the name
     * @param name {String}
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Retrieve the name
     * @returns {String}
     */
    public String getName() {
        return this.name;
    }

    /*
     * Sets the list of questions
     * @param questions {Collection<Question>}
     */
    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    /*
     * Retrieve the list of questions
     * @returns {Collection<Question>}
     */
    public Collection<Question> getQuestions() {
        return questions;
    }

}
