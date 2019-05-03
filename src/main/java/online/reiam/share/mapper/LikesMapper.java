package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.Likes;
import online.reiam.share.response.LikesResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贴子点赞表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface LikesMapper extends BaseMapper<Likes> {

    IPage<LikesResponse> selectUserInfoListByLikesAndType(Page page, @Param("typeId") Integer typeId, @Param("likesType") Integer likesType);

}
