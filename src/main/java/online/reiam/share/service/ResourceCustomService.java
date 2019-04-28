package online.reiam.share.service;

import online.reiam.share.entity.Resource;
import org.apache.tika.mime.MimeTypeException;

import java.io.IOException;

public interface ResourceCustomService {

    Resource uploadResource(byte[] bytes, Integer userId) throws IOException, MimeTypeException;

}
