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
public class TopicFollowRequest implements Serializable {

    private static final long serialVersionUID = 4864521812323529681L;

    public interface Follow {
    }

    public interface ListTopicByUserFollow {
    }

    @NotNull(message = "name不能为空。", groups = TopicFollowRequest.Follow.class)
    @JsonProperty(value = "name")
    private String name;

    @NotNull(message = "follow不能为空。", groups = TopicFollowRequest.Follow.class)
    @JsonProperty(value = "follow")
    private Boolean follow;

    @NotNull(message = "page_num不能为空。", groups = TopicFollowRequest.ListTopicByUserFollow.class)
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    @NotNull(message = "page_size不能为空。", groups = TopicFollowRequest.ListTopicByUserFollow.class)
    @JsonProperty(value = "page_size")
    private Integer pageSize;

}
