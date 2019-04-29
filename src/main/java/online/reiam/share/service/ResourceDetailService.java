package online.reiam.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.reiam.share.entity.ResourceDetail;
import org.apache.tika.mime.MimeTypeException;

import java.io.IOException;

/**
 * <p>
 * 资源详情表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface ResourceDetailService extends IService<ResourceDetail> {

    String uploadProfilePhoto(byte[] bytes, Integer userId) throws IOException, MimeTypeException;

}
