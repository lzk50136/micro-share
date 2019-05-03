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

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 用户贴子id
     */
    private Integer postId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 贴子图片id
     */
    private Integer resourceId;

    /**
     * 贴子图片详情
     */
    private ResourceResponse resourceResponse;

}
