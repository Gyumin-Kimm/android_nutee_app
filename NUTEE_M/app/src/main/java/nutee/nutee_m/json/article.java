package nutee.nutee_m.json;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class article {
    private String id;
    private String article_writer;
    private String article_title;
    private String TIMESTAMP;
    private String board_permalink;
    private String board_name;
    private String user_nickname;
    private String comments;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle_writer() {
        return article_writer;
    }

    public void setArticle_writer(String article_writer) {
        this.article_writer = article_writer;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getBoard_permalink() {
        return board_permalink;
    }

    public void setBoard_permalink(String board_permalink) {
        this.board_permalink = board_permalink;
    }

    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
