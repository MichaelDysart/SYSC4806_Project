package webApp;

/*
 * A response message to report status data to the client
 */
public class Response {

    private final String message;
    private final String content;
    private final Integer id;

    /*
     * A constructor
     * @param message {String}
     * @param content {String}
     */
    public Response(Integer id, String message, String content) {
        this.message = message;
        this.content = content;
        this.id = id;
    }

    /*
     * Retrieve the survey id
     * @returns {int}
     */
    public Integer getId() {
        return id;
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
