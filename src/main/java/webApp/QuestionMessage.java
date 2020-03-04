package webApp;

public class QuestionMessage {

    private final String type;
    private final String question;

    public QuestionMessage(String type, String question) {
        this.type = type;
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }
}
