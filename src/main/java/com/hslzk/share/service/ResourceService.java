package com.hslzk.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hslzk.share.entity.Resource;
import com.hslzk.share.response.ResourceResponse;
import org.apache.tika.mime.MimeTypeException;

import java.io.IOException;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
public interface ResourceService extends IService<Resource> {

    ResourceResponse uploadResource(byte[] bytes, Integer userId) throws IOException, MimeTypeException;

}
