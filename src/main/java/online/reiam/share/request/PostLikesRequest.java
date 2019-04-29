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
public class PostLikesRequest implements Serializable {

    private static final long serialVersionUID = -2490277950401223687L;

    public interface Likes {
    }

    public interface ListLikesByPostId {
    }

    public interface ListLikesByUserId {
    }

    @NotNull(message = "post_id不能为空。", groups = {Likes.class, ListLikesByPostId.class})
    @JsonProperty(value = "post_id")
    private Integer postId;

    @NotNull(message = "likes不能为空。", groups = Likes.class)
    @JsonProperty(value = "likes")
    private Boolean likes;

    @NotNull(message = "page_num不能为空。", groups = {ListLikesByPostId.class, ListLikesByUserId.class})
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    @NotNull(message = "page_size不能为空。", groups = {ListLikesByPostId.class, ListLikesByUserId.class})
    @JsonProperty(value = "page_size")
    private Integer pageSize;

}
