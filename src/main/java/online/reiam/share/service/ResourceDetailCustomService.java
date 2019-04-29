package online.reiam.share.service;

import org.apache.tika.mime.MimeTypeException;

import java.io.IOException;

public interface ResourceDetailCustomService {

    String uploadProfilePhoto(byte[] bytes, Integer userId) throws IOException, MimeTypeException;

}
