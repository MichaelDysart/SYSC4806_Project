package webApp;

import java.util.UUID;

/*
 * A response message to report status data to the client
 */
public class Response {

    private final String message;
    private final String content;
    private final Integer id;
    private final UUID link;

    /*
     * A constructor
     * @param message {String}
     * @param content {String}
     */
    public Response(Integer id, UUID link, String message, String content) {
        this.message = message;
        this.content = content;
        this.id = id;
        this.link = link;
    }

    /*
     * Retrieve the survey id
     * @returns {Integer}
     */
    public Integer getId() {
        return id;
    }

    /*
     * Retrieve the survey link
     * @returns {UUID}
     */
    public UUID getLink() {
        return link;
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
