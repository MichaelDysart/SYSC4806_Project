package webApp;

public class QuestionMessage {

    private final String type;
    private final String question;
    private final String min;
    private final String max;

    public QuestionMessage(String type, String question, String min, String max) {
        this.type = type;
        this.question = question;
        this.min = min;
        this.max = max;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String getMin() { return min; }

    public String getMax() { return max; }
}
