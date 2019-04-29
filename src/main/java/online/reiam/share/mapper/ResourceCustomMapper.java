package online.reiam.share.mapper;

import online.reiam.share.response.ResourceResponse;
import org.apache.ibatis.annotations.Param;

public interface ResourceCustomMapper {

    ResourceResponse selectResourceById(@Param("id") Integer id);

}
