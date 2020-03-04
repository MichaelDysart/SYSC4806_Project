package survey;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Survey {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String name;

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
     * Sets the id
     * @param id {long}
     */
    public void setId(long id) {
        this.id = id;
    }

    /*
     * Retrieve the id
     * @returns {long}
     */
    public long getId() {
        return this.id;
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
