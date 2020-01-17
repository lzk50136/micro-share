package com.hslzk.share.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    /**
     * JWT签发者
     */
    private static final String ISS = "Lzk";
    /**
     * 服务端的私钥
     */
    private static final String SECRET = "13126208653";
    /**
     * 过期时间是3600秒，既是1个小时
     */
    public static final long EXPIRATION = 3600L * 1000;

    /**
     * 创建token
     */
    public static String createToken(Integer userId) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JwtTokenUtil.SECRET)
                .setIssuer(ISS)
                .setSubject(userId + "")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .compact();
    }

    public static Integer getUserId(String token) {
        try {
            return Integer.valueOf(Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject());
        } catch (JwtException e) {
            return null;
        }
    }

}
