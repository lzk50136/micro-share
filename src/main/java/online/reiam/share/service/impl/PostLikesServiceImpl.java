package online.reiam.share.service.impl;

import online.reiam.share.entity.PostLikes;
import online.reiam.share.mapper.PostLikesMapper;
import online.reiam.share.service.PostLikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 贴子点赞表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class PostLikesServiceImpl extends ServiceImpl<PostLikesMapper, PostLikes> implements PostLikesService {

}
