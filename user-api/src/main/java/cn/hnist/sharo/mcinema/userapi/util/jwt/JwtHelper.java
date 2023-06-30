package cn.hnist.sharo.mcinema.userapi.util.jwt;

import cn.hnist.sharo.mcinema.core.util.DataUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtHelper {

    /**
     * <h3>JWT(Java Web Token)</h3>
     * 构成: header.payload.signature
     * <ul>
     *     <li>header</li>
     *     <li>payload(claims)</li>
     *     <li>signature</li>
     * </ul>
     */
    // 密钥
    @Value("${mcinema.auth.jwt.secret-key}")
    private String SECRET_KEY;
    // jwt header
    @Value("#{${mcinema.auth.jwt.header}}")
    private Map<String, Object> header;

    // jwt payload(jwt claims)
    @Value("#{${mcinema.auth.jwt.claims}}")
    private Map<String, String> claim;
    // JWT signature
    // HMAC256(SECRET_KEY)
    @Value("${mcinema.auth.jwt.user-key}")
    private String userKey;
    @Value("${mcinema.auth.jwt.exp-time}")
    private String expTime;

    public String createUserIdToken(Long userId) {
        try {
            Date nowTime = new Date();
            Date expiresTime = DataUtil.calculateTime(nowTime, expTime);
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withHeader(header)
                    .withClaim(userKey, userId)
                    .withIssuer(claim.get("Issuer").toString())
                    .withIssuedAt(nowTime)
                    .withSubject(claim.get("Subject").toString())
                    .withAudience(claim.get("Audience").toString())
                    .withExpiresAt(expiresTime)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(claim.get("Issuer"))
                    .build();
            return verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
