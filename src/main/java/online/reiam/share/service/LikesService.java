package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Likes;
import online.reiam.share.request.LikesRequest;
import online.reiam.share.response.LikesResponse;

/**
 * <p>
 * 贴子点赞表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface LikesService extends IService<Likes> {

    void likes(Integer userId, LikesRequest likesRequest);

    IPage<LikesResponse> listUserInfoByLikesAndType(LikesRequest likesRequest);

}
