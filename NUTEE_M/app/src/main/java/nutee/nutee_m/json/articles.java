package nutee.nutee_m.json;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class articles {
    private String id;
    private String article_boardId;
    private String article_title;
    private String article_writerId;
    private String user_auth;
    private String user_nickname;
    private String article_commentCount;
    private String TIMESTAMP;
    private String category_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle_boardId() {
        return article_boardId;
    }

    public void setArticle_boardId(String article_boardId) {
        this.article_boardId = article_boardId;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_writerId() {
        return article_writerId;
    }

    public void setArticle_writerId(String article_writerId) {
        this.article_writerId = article_writerId;
    }

    public String getUser_auth() {
        return user_auth;
    }

    public void setUser_auth(String user_auth) {
        this.user_auth = user_auth;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getArticle_commentCount() {
        return article_commentCount;
    }

    public void setArticle_commentCount(String article_commentCount) {
        this.article_commentCount = article_commentCount;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
