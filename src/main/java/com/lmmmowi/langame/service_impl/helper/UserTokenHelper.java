package com.lmmmowi.langame.service_impl.helper;

import com.lmmmowi.langame.model.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class UserTokenHelper {

    public static final String SECRET = "langame_jwt";
    public static final Long DEFAULT_EXPIRATION = null;
    public static final String PAYLOAD_KEY_USER = "user";

    private UserTokenHelper() {
    }

    public static UserTokenHelper getDefault() {
        return new UserTokenHelper();
    }

    public String generate(User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(PAYLOAD_KEY_USER, user.getId());
        return signJWT(payload, null);
    }

    public Integer verify(String token) {
        try {
            Map<String, Object> payload = verifyJWT(token);
            return (Integer) payload.get(PAYLOAD_KEY_USER);
        } catch (JwtException e) {
            return null;
        }
    }

    private String signJWT(Map<String, Object> payload, Long expiration) {
        if (expiration == null) {
            expiration = DEFAULT_EXPIRATION;
        }

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(payload);

        if (expiration != null) {
            Date expirationDate = new Date(System.currentTimeMillis() + expiration);
            jwtBuilder.setExpiration(expirationDate);
        }

        return jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET.getBytes()).compact();
    }

    private Map<String, Object> verifyJWT(String token) throws JwtException {
        JwtParser parser = Jwts.parser();
        // 允许误差一分钟
        parser.setAllowedClockSkewSeconds(60);

        Jws<Claims> jws = parser.setSigningKey(SECRET.getBytes()).parseClaimsJws(token);
        Map<String, Object> map = new HashMap<>();
        jws.getBody().forEach((k, v) -> map.put(k, v));
        return map;
    }
}
