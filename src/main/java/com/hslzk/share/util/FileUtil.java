package com.hslzk.share.util;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

public class FileUtil {

    public static String getFileType(byte[] bytes) throws MimeTypeException {
        Tika tika = new Tika();
        String type = tika.detect(bytes);
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = mimeTypes.forName(type);
        return mimeType.getExtension();
    }

}
