package survey;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Survey {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Question> questions;

    public Survey(String name){ this.name = name; }

    public Survey(String name, Collection<Question> questions){
        this.name = name;
        this.questions = questions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

}
