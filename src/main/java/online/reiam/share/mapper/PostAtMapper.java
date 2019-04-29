package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.reiam.share.entity.PostAt;
import online.reiam.share.response.PostAtResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贴子艾特表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface PostAtMapper extends BaseMapper<PostAt> {

    IPage<PostAtResponse> selectPostListByUserId(Page page, @Param("userId") Integer userId);

}
