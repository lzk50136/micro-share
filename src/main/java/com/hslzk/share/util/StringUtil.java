package com.hslzk.share.util;

import java.util.UUID;

public class StringUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

}
