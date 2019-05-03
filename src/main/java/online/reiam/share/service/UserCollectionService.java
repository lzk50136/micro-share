package online.reiam.share.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.UserCollection;
import online.reiam.share.request.UserCollectionRequest;
import online.reiam.share.response.PostResponse;

/**
 * <p>
 * 用户收藏表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface UserCollectionService extends IService<UserCollection> {

    void collect(Integer userId, UserCollectionRequest userCollectionRequest);

    IPage<PostResponse> listPostByUserCollection(UserCollectionRequest userCollectionRequest, Integer userId);

}
