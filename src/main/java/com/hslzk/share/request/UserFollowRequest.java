package com.hslzk.share.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserFollowRequest implements Serializable {

    private static final long serialVersionUID = -2490277950401223687L;

    public interface Follow {
    }

    public interface ListFollow {
    }

    @NotNull(message = "nickname不能为空。", groups = {Follow.class, ListFollow.class})
    @JsonProperty(value = "nickname")
    private String nickname;

    @NotNull(message = "follow不能为空。", groups = Follow.class)
    @JsonProperty(value = "follow")
    private Boolean follow;

    @NotNull(message = "page_num不能为空。", groups = ListFollow.class)
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    @NotNull(message = "page_size不能为空。", groups = ListFollow.class)
    @JsonProperty(value = "page_size")
    private Integer pageSize;

}
