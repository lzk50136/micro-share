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
public class TopicRequest implements Serializable {

    private static final long serialVersionUID = -5621331165927416498L;

    public interface GetTopic {
    }

    @NotNull(message = "name不能为空。", groups = GetTopic.class)
    @JsonProperty(value = "name")
    private String name;

}
