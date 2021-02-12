package nutee.nutee_m.json;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class comments {
    private String id;
    private String comment_content;
    private String comment_articleId;
    private String comment_userId;
    private String TIMESTAMP;
    private String user_nickname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_articleId() {
        return comment_articleId;
    }

    public void setComment_articleId(String comment_articleId) {
        this.comment_articleId = comment_articleId;
    }

    public String getComment_userId() {
        return comment_userId;
    }

    public void setComment_userId(String comment_userId) {
        this.comment_userId = comment_userId;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }
}
