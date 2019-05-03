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
public class TopicPostRequest implements Serializable {

    private static final long serialVersionUID = 8212990009741842517L;

    public interface ListPostByTopicName {
    }

    @NotNull(message = "name不能为空。", groups = ListPostByTopicName.class)
    @JsonProperty(value = "name")
    private String name;

    @NotNull(message = "page_num不能为空。", groups = ListPostByTopicName.class)
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    @NotNull(message = "page_size不能为空。", groups = ListPostByTopicName.class)
    @JsonProperty(value = "page_size")
    private Integer pageSize;

}
