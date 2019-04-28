package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResourceResponse implements Serializable {

    private static final long serialVersionUID = -3788498317517624015L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 原始图片
     */
    private String originalName;

    /**
     * 水印图片
     */
    private String watermarkName;

    /**
     * 缩略图片
     */
    private String thumbnailName;

}
