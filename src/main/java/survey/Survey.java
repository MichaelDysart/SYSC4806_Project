package survey;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

/*
 * A class to store surveys, comprising a list of questions
 */
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected UUID link;

    protected String name;

    protected boolean closed;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Question> questions;

    /*
     * A default constructor
     */
    public Survey(){
        this(null);
    }

    /*
     * A constructor
     * @param name {String}
     */
    public Survey(String name){
        this(name, null);
    }

    /*
     * A constructor
     * @param name {String}
     * @param questions {Collection<Question>}
     */
    public Survey(String name, Collection<Question> questions){
        this.name = name;
        this.questions = questions;
        this.link = UUID.randomUUID();
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
     * Sets the link
     * @param link {UUID}
     */
    public void setLink(UUID link) {
        this.link = link;
    }

    /*
     * Retrieve the link
     * @returns {UUID}
     */
    public UUID getLink() {
        return this.link;
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

    /*
     * Sets the closed status of the survey
     * @returns {boolean}
     */
    public boolean getClosed() {
        return closed;
    }

    /*
     * Tests if the survey is closed
     * @param closed {boolean}
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}
