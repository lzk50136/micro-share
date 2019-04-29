package online.reiam.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.ResourceDetail;
import online.reiam.share.entity.UserInfo;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.service.ResourceDetailCustomService;
import online.reiam.share.service.ResourceDetailService;
import online.reiam.share.service.UserInfoService;
import online.reiam.share.util.FileUtil;
import online.reiam.share.util.ImageUtil;
import online.reiam.share.util.StringUtil;
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

import static online.reiam.share.constants.Constants.DOWNLOAD_LINK;
import static online.reiam.share.constants.Constants.RESOURCE_PATH;

@Service
public class ResourceDetailCustomServiceImpl implements ResourceDetailCustomService {
    @Autowired
    private ResourceDetailService resourceDetailService;
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
        resourceDetailService.save(resourceDetail);
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
