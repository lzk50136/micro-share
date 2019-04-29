package online.reiam.share.service;

import online.reiam.share.response.ResourceResponse;
import org.apache.tika.mime.MimeTypeException;

import java.io.IOException;

public interface ResourceCustomService {

    ResourceResponse uploadResource(byte[] bytes, Integer userId) throws IOException, MimeTypeException;

}
