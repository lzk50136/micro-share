package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostLikesResponse implements Serializable {

    private static final long serialVersionUID = -5304804242546325692L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户信息
     */
    private UserInfoResponse userInfoResponse;

}
