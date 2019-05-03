package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.Post;
import online.reiam.share.response.PostResponse;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 贴子表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface PostMapper extends BaseMapper<Post> {

    PostResponse selectPostById(@Param("id") Integer id);

    IPage<PostResponse> selectPostListByUserId(Page page, @Param("userId") Integer userId);

    IPage<PostResponse> selectPostListByFollowId(Page page, List<Integer> list, LocalDateTime startTime, LocalDateTime endTime);

    List<PostResponse> selectPostListByIdList(List<Integer> list);

}
