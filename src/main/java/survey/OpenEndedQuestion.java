package survey;

import java.util.ArrayList;

public class OpenEndedQuestion extends Question {

    private ArrayList<String> answers = new ArrayList<String>();

    public OpenEndedQuestion(String question) {
        super("open_ended", question);
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
    }
}
