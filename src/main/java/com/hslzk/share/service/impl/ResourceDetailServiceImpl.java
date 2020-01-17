package com.hslzk.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hslzk.share.entity.ResourceDetail;
import com.hslzk.share.entity.UserInfo;
import com.hslzk.share.exception.MicroShareException;
import com.hslzk.share.mapper.ResourceDetailMapper;
import com.hslzk.share.service.ResourceDetailService;
import com.hslzk.share.service.UserInfoService;
import com.hslzk.share.util.FileUtil;
import com.hslzk.share.util.ImageUtil;
import com.hslzk.share.util.StringUtil;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static com.hslzk.share.constants.Constants.DOWNLOAD_LINK;
import static com.hslzk.share.constants.Constants.RESOURCE_PATH;

/**
 * <p>
 * 资源详情表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class ResourceDetailServiceImpl extends ServiceImpl<ResourceDetailMapper, ResourceDetail> implements ResourceDetailService {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 修改头像
     */
    @Override
    public String uploadProfilePhoto(byte[] bytes, Integer userId) throws IOException, MimeTypeException {
        if (!ImageUtil.isImage(new ByteArrayInputStream(bytes))) {
            throw new MicroShareException(10019, "头像格式有误。");
        }
        // 原始文件写入硬盘
        String name = StringUtil.getUUID();
        String type = FileUtil.getFileType(bytes);
        String fileName = name + type;
        Path path = Paths.get(RESOURCE_PATH + fileName);
        Files.write(path, bytes);
        // 原始文件信息保存到数据库
        String md5 = DigestUtils.md5DigestAsHex(bytes);
        ResourceDetail resourceDetail = new ResourceDetail();
        resourceDetail.setMd5(md5)
                .setPath(RESOURCE_PATH)
                .setLink(DOWNLOAD_LINK)
                .setName(fileName)
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        save(resourceDetail);
        // 修改用户信息的头像信息
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userId));
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setId(userInfo.getId())
                .setProfilePhoto(fileName)
                .setVersion(userInfo.getVersion())
                .setModifiedTime(LocalDateTime.now());
        userInfoService.updateById(userInfo2);
        return resourceDetail.getName();
    }

}
