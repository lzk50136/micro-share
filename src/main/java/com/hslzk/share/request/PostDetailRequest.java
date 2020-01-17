package com.hslzk.share.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostDetailRequest implements Serializable {

    private static final long serialVersionUID = 1388990602484094731L;

    @Max(value = 9, message = "只能为小于10的数字", groups = PostRequest.Create.class)
    @Min(value = 1, message = "只能为大于0的数字", groups = PostRequest.Create.class)
    @NotNull(message = "sort不能为空。", groups = PostRequest.Create.class)
    @JsonProperty(value = "sort")
    private Integer sort;

    @NotNull(message = "resource_id不能为空。", groups = PostRequest.Create.class)
    @JsonProperty(value = "resource_id")
    private Integer resourceId;

}
