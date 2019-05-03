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
public class UserCollectionRequest implements Serializable {

    private static final long serialVersionUID = -2114734548735412244L;

    public interface Collect {
    }

    public interface ListCollect {
    }

    @NotNull(message = "post_id不能为空。", groups = Collect.class)
    @JsonProperty(value = "post_id")
    private Integer postId;

    @NotNull(message = "collect不能为空。", groups = Collect.class)
    @JsonProperty(value = "collect")
    private Boolean collect;

    @NotNull(message = "page_num不能为空。", groups = ListCollect.class)
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    @NotNull(message = "page_size不能为空。", groups = ListCollect.class)
    @JsonProperty(value = "page_size")
    private Integer pageSize;

}
