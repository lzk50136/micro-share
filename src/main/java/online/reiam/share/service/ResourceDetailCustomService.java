package online.reiam.share.service;

import online.reiam.share.entity.ResourceDetail;
import org.apache.tika.mime.MimeTypeException;

import java.io.IOException;

public interface ResourceDetailCustomService {

    ResourceDetail uploadProfilePhoto(byte[] bytes, Integer userId) throws IOException, MimeTypeException;

}
