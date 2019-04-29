package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostDetailResponse implements Serializable {

    private static final long serialVersionUID = 1388990602484094731L;

    private Integer id;

    private Integer postId;

    private Integer sort;

    private Integer resourceId;

    private ResourceResponse resourceResponse;

}
