package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostAtResponse implements Serializable {

    private static final long serialVersionUID = 6382689767888772012L;

    /**
     * 贴子id
     */
    private Integer postId;

    /**
     * 贴子详情
     */
    private PostResponse postResponse;

}
