package com.hslzk.share.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import com.hslzk.share.entity.Resource;
import com.hslzk.share.entity.ResourceDetail;
import com.hslzk.share.entity.UserInfo;
import com.hslzk.share.exception.MicroShareException;
import com.hslzk.share.mapper.ResourceMapper;
import com.hslzk.share.response.ResourceResponse;
import com.hslzk.share.service.ResourceDetailService;
import com.hslzk.share.service.ResourceService;
import com.hslzk.share.service.UserInfoService;
import com.hslzk.share.util.FileUtil;
import com.hslzk.share.util.ImageUtil;
import com.hslzk.share.util.StringUtil;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static com.hslzk.share.constants.Constants.DOWNLOAD_LINK;
import static com.hslzk.share.constants.Constants.RESOURCE_PATH;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Autowired
    private ResourceDetailService resourceDetailService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 资源文件上传
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResourceResponse uploadResource(byte[] bytes, Integer userId) throws IOException, MimeTypeException {
        if (!ImageUtil.isImage(new ByteArrayInputStream(bytes))) {
            throw new MicroShareException(10019, "图片格式有误。");
        }
        // 原始文件写入硬盘
        String name = StringUtil.getUUID();
        String type = FileUtil.getFileType(bytes);
        String originalName = name + type;
        Path path = Paths.get(RESOURCE_PATH + originalName);
        Files.write(path, bytes);
        // 原始文件信息保存到数据库
        String md5 = DigestUtils.md5DigestAsHex(bytes);
        ResourceDetail resourceDetail = new ResourceDetail();
        resourceDetail.setMd5(md5)
                .setPath(RESOURCE_PATH)
                .setLink(DOWNLOAD_LINK)
                .setName(originalName)
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        resourceDetailService.save(resourceDetail);

        File file = new File(RESOURCE_PATH + originalName);

        // 文字转水印图片
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getUserId, userId));
        ByteArrayInputStream byteArrayInputStream = ImageUtil.createImage(userInfo.getNickname());
        if (byteArrayInputStream == null) {
            throw new MicroShareException(10021, "文字转换失败！");
        }
        BufferedImage bufferedImage2 = ImageIO.read(byteArrayInputStream);

        // 生成水印文件，写入硬盘
        String name2 = StringUtil.getUUID();
        String watermarkName = name2 + type;
        File file2 = new File(RESOURCE_PATH + watermarkName);
        BufferedImage bufferedImage = ImageIO.read(file);
        Thumbnails.of(file)
                .size(bufferedImage.getWidth(), bufferedImage.getHeight())
                // 水印透明度不能写固定值
                .watermark(Positions.BOTTOM_RIGHT, bufferedImage2, 0.25f)
                // 图片输出质量100%，可以写固定值
                .outputQuality(1.0f)
                .toFile(file2);
        // 水印文件信息保存到数据库
        String md52 = DigestUtils.md5DigestAsHex(new FileInputStream(file2));
        ResourceDetail resourceDetail2 = new ResourceDetail();
        resourceDetail2.setMd5(md52)
                .setPath(RESOURCE_PATH)
                .setLink(DOWNLOAD_LINK)
                .setName(watermarkName)
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        resourceDetailService.save(resourceDetail2);

        // 生成缩略图文件，写入硬盘
        String name3 = StringUtil.getUUID();
        String thumbnailName = name3 + type;
        File file3 = new File(RESOURCE_PATH + thumbnailName);
        Thumbnails.of(file2)
                // 缩放比例不能写固定值
                .scale(0.25f)
                .toFile(file3);
        // 缩略图文件信息保存到数据库
        String md53 = DigestUtils.md5DigestAsHex(new FileInputStream(file3));
        ResourceDetail resourceDetail3 = new ResourceDetail();
        resourceDetail3.setMd5(md53)
                .setPath(RESOURCE_PATH)
                .setLink(DOWNLOAD_LINK)
                .setName(thumbnailName)
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        resourceDetailService.save(resourceDetail3);

        // 汇总文件信息保存到数据库
        Resource resource = new Resource();
        resource.setOriginalName(originalName)
                .setWatermarkName(watermarkName)
                .setThumbnailName(thumbnailName)
                .setDeleted(false)
                .setVersion(0)
                .setCreateTime(LocalDateTime.now())
                .setModifiedTime(LocalDateTime.now());
        save(resource);

        ResourceResponse resourceResponse = new ResourceResponse();
        BeanUtils.copyProperties(resource, resourceResponse);
        return resourceResponse;
    }

}
