package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.Comment;
import online.reiam.share.response.CommentResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贴子评论表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<CommentResponse> selectCommentListByLikesNum(Page page, @Param("typeId") Integer typeId, @Param("commentType") Integer commentType);

    IPage<CommentResponse> selectCommentListByModifiedTime(Page page, @Param("typeId") Integer typeId, @Param("commentType") Integer commentType);

    /*List<CommentResponse> selectCommentByIdList(List<Integer> list);

    IPage<Integer> selectCommentIdByReplyTo(Page page, @Param("userId") Integer userId);*/

}
