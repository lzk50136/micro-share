package online.reiam.share.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.Resource;
import online.reiam.share.entity.ResourceDetail;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.response.ResourceResponse;
import online.reiam.share.service.ResourceCustomService;
import online.reiam.share.service.ResourceDetailCustomService;
import online.reiam.share.service.ResourceDetailService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import online.reiam.share.util.ImageUtil;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;

@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceCustomService resourceCustomService;
    @Autowired
    private ResourceDetailCustomService resourceDetailCustomService;
    @Autowired
    private ResourceDetailService resourceDetailService;

    /**
     * 上传图片
     */
    @PostMapping(value = "/upload_resource", produces = APPLICATION_JSON)
    @ResponseBody
    public ApiResult uploadResource(@RequestParam("file") MultipartFile multipartFile, @RequestHeader("Authorization") String authorization) throws IOException, MimeTypeException {
        if (!ImageUtil.isImage(multipartFile.getInputStream())) {
            throw new MicroShareException(10019, "图片格式有误。");
        }
        byte[] bytes = multipartFile.getBytes();
        Resource resource = resourceCustomService.uploadResource(bytes, JwtTokenUtil.getUserId(authorization));
        ResourceResponse resourceResponse = new ResourceResponse();
        BeanUtils.copyProperties(resource, resourceResponse);
        return ApiResultUtil.success(resourceResponse);
    }

    /**
     * 上传头像
     */
    @PostMapping(value = "/upload_profile_photo", produces = APPLICATION_JSON)
    @ResponseBody
    public ApiResult uploadProfilePhoto(@RequestParam("file") MultipartFile multipartFile, @RequestHeader("Authorization") String authorization) throws IOException, MimeTypeException {
        if (!ImageUtil.isImage(multipartFile.getInputStream())) {
            throw new MicroShareException(10019, "图片格式有误。");
        }
        byte[] bytes = multipartFile.getBytes();
        ResourceDetail resourceDetail = resourceDetailCustomService.uploadProfilePhoto(bytes, JwtTokenUtil.getUserId(authorization));
        return ApiResultUtil.success(resourceDetail.getName());
    }

    /**
     * 获取图片
     */
    @GetMapping(value = "/access")
    public void access(@RequestParam("name") String name, HttpServletResponse response) {
        QueryWrapper<ResourceDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ResourceDetail::getName, name);
        ResourceDetail resourceDetail = resourceDetailService.getOne(queryWrapper);
        if (resourceDetail == null) {
            throw new MicroShareException(10020, "资源不存在。");
        } else {
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + resourceDetail.getName());
            response.setHeader("X-Accel-Redirect", resourceDetail.getLink() + resourceDetail.getName());
        }
    }

}
