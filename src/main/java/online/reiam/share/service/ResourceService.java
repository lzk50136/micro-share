package online.reiam.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.Resource;
import online.reiam.share.response.ResourceResponse;
import org.apache.tika.mime.MimeTypeException;

import java.io.IOException;

/**
 * <p>
 * 贴子资源表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface ResourceService extends IService<Resource> {

    ResourceResponse uploadResource(byte[] bytes, Integer userId) throws IOException, MimeTypeException;

}
