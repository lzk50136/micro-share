package online.reiam.share.service.impl;

import online.reiam.share.entity.Comment;
import online.reiam.share.mapper.CommentMapper;
import online.reiam.share.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 贴子评论表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
