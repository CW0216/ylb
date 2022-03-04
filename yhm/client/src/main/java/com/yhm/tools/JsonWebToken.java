package com.yhm.tools;

import io.jsonwebtoken.*;

import java.util.Date;

public class JsonWebToken {

    private static long time=1000*60*60*24;
    private static String signature="xch";

    public static String jwt(String username,String nickname){
        JwtBuilder jwtBuilder= Jwts.builder();
        String jwtToken=jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username",username)
                .claim("nickname",nickname)
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId("lyl")
                //signature
                .signWith(SignatureAlgorithm.ES256,signature)
                .compact();
        return jwtToken;
    }

    public static boolean checkToken(String token){
        if(token==null){
            return false;
        }
        JwtParser jwtParser=Jwts.parser();
        try {
            Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getUsername(String token){
        JwtParser jwtParser=Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        return (String) claimsJws.getBody().get("username");
    }

    public static String getNickname(String token){
        JwtParser jwtParser=Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        return (String) claimsJws.getBody().get("nickname");
    }
}
