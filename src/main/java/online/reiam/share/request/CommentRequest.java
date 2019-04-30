package online.reiam.share.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentRequest implements Serializable {

    private static final long serialVersionUID = -2490277950401223687L;

    public interface Comment {
    }

    public interface Delete {
    }

    public interface ListCommentByType {
    }

    public interface ListCommentByUserId {
    }

    @NotNull(message = "id不能为空。", groups = Delete.class)
    @JsonProperty(value = "id")
    private Integer id;

    @NotNull(message = "type_id不能为空。", groups = {Comment.class, ListCommentByType.class})
    @JsonProperty(value = "type_id")
    private Integer typeId;

    @NotNull(message = "content不能为空。", groups = Comment.class)
    @JsonProperty(value = "content")
    private String content;

    @NotNull(message = "comment_type不能为空。", groups = {Comment.class, Delete.class, ListCommentByType.class})
    @JsonProperty(value = "comment_type")
    private Integer commentType;

    @NotNull(message = "page_num不能为空。", groups = {ListCommentByType.class, ListCommentByUserId.class})
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    @NotNull(message = "page_size不能为空。", groups = {ListCommentByType.class, ListCommentByUserId.class})
    @JsonProperty(value = "page_size")
    private Integer pageSize;

}
