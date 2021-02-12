package nutee.nutee_m.json;

import java.io.Serializable;

/**
 * Created by cc-a1286-02 on 15. 6. 6..
 */
public class categories implements Serializable {
    private String id;
    private String category_name;
    private String category_boardId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_boardId() {
        return category_boardId;
    }

    public void setCategory_boardId(String category_boardId) {
        this.category_boardId = category_boardId;
    }
}
