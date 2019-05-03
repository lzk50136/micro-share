package online.reiam.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.reiam.share.entity.Resource;
import online.reiam.share.response.ResourceResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    ResourceResponse selectResourceById(@Param("id") Integer id);

}
