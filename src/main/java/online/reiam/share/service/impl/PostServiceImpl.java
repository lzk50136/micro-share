package online.reiam.share.service.impl;

import online.reiam.share.entity.Post;
import online.reiam.share.mapper.PostMapper;
import online.reiam.share.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户贴子表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-28
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
