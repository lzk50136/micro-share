package com.hslzk.share.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostRequest implements Serializable {

    private static final long serialVersionUID = 1388990602484094731L;

    public interface Create {
    }

    public interface Delete {
    }

    public interface ListPostByNickname {
    }

    public interface ListPostByFollowId {
    }

    @NotBlank(message = "id不能为空。", groups = Delete.class)
    @JsonProperty(value = "id")
    private Integer id;

    @NotBlank(message = "title不能为空。", groups = Create.class)
    @JsonProperty(value = "title")
    private String title;

    @Valid
    @Size(min = 1, max = 9, message = "post_detail_list必须大于0和小于10。", groups = Create.class)
    @NotNull(message = "post_detail_list不能为空。", groups = Create.class)
    @JsonProperty(value = "post_detail_list")
    private List<PostDetailRequest> postDetailRequestList;

    @NotNull(message = "nickname不能为空。", groups = ListPostByNickname.class)
    @JsonProperty(value = "nickname")
    private String nickname;

    @NotNull(message = "allow_comment不能为空。", groups = Create.class)
    @JsonProperty(value = "allow_comment")
    private Boolean allowComment;

    @NotNull(message = "page_num不能为空。", groups = {ListPostByNickname.class, ListPostByFollowId.class})
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    @NotNull(message = "page_size不能为空。", groups = {ListPostByNickname.class, ListPostByFollowId.class})
    @JsonProperty(value = "page_size")
    private Integer pageSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "start_time不能为空。", groups = ListPostByFollowId.class)
    @JsonProperty(value = "start_time")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "end_time不能为空。", groups = ListPostByFollowId.class)
    @JsonProperty(value = "end_time")
    private LocalDateTime endTime;

}
