package online.reiam.share.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.entity.ResourceDetail;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.service.ResourceDetailService;
import online.reiam.share.service.ResourceService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.apache.tika.mime.MimeTypeException;
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
    private ResourceService resourceService;
    @Autowired
    private ResourceDetailService resourceDetailService;

    /**
     * 上传图片
     */
    @PostMapping(value = "/upload_resource", produces = APPLICATION_JSON)
    @ResponseBody
    public ApiResult uploadResource(@RequestParam("file") MultipartFile multipartFile, @RequestHeader("Authorization") String authorization) throws IOException, MimeTypeException {
        byte[] bytes = multipartFile.getBytes();
        return ApiResultUtil.success(resourceService.uploadResource(bytes, JwtTokenUtil.getUserId(authorization)));
    }

    /**
     * 上传头像
     */
    @PostMapping(value = "/upload_profile_photo", produces = APPLICATION_JSON)
    @ResponseBody
    public ApiResult uploadProfilePhoto(@RequestParam("file") MultipartFile multipartFile, @RequestHeader("Authorization") String authorization) throws IOException, MimeTypeException {
        byte[] bytes = multipartFile.getBytes();
        return ApiResultUtil.success(resourceDetailService.uploadProfilePhoto(bytes, JwtTokenUtil.getUserId(authorization)));
    }

    /**
     * 获取图片
     */
    @GetMapping(value = "/download_resource")
    public void downloadResource(@RequestParam("name") String name, HttpServletResponse response) {
        ResourceDetail resourceDetail = resourceDetailService.getOne(new QueryWrapper<ResourceDetail>().lambda().eq(ResourceDetail::getName, name));
        if (resourceDetail == null) {
            throw new MicroShareException(10020, "资源不存在。");
        } else {
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + resourceDetail.getName());
            response.setHeader("X-Accel-Redirect", resourceDetail.getLink() + resourceDetail.getName());
        }
    }

}
