package webApp;

public class Response {

    private final String message;
    private final String content;

    public Response(String message, String content) {
        this.message = message;
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }

}
