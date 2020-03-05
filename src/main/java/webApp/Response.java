package webApp;

/*
 * A response message to report status data to the client
 */
public class Response {

    private final String message;
    private final String content;

    /*
     * A constructor
     * @param message {String}
     * @param content {String}
     */
    public Response(String message, String content) {
        this.message = message;
        this.content = content;
    }

    /*
     * Retrieve the message
     * @returns {string}
     */
    public String getMessage() {
        return message;
    }

    /*
     * Retrieve the content
     * @returns {string}
     */
    public String getContent() {
        return content;
    }

}
