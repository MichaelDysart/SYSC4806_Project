package survey;

import java.util.ArrayList;

public class Survey {
    private Integer id;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public Survey(){}

    public Survey(ArrayList<Question> questions){
        this.questions = questions;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

}
