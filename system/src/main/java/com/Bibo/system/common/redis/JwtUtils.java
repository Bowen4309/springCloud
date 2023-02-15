package com.Bibo.system.common.redis;

import cn.hutool.core.util.StrUtil;
import com.Bibo.common.pojo.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Date;

/**
 * @Author: CGF
 * @CreateTime: 2021/8/13 14:45
 * @Description:
 */
public class JwtUtils {

    //设置token过期时间
//    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final long EXPIRE = 2000 * 36000;

    /**
     * 请求头
     */
    private static final String HEADER = "RZZX-USERTOKEN";

    //加密秘钥,可以随便写
    public static final String KAI_SECRET = "traffic";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "user_token:";


    /**
     * 盐
     */
    public static final String SALT_BYTE = "abcdef";


    /**
     * 加密类型
     */
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * 生成密文长度
     */
    public static final int HASH_BIT_SIZE = 128;

    /**
     * 迭代次数
     */
    public static final int PBKDF2_ITERATIONS = 10;


    //这个是生成token的方法，参数可多个
    public static String getJwtToken(LoginUser loginUser){

        String JwtToken = Jwts.builder()
                //这个是头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                //这里是设置过期时间
                .setSubject("kj-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                //设置token的主体信息，存储用户信息
                .claim("username", loginUser.getUserName())
                //.claim("xxx", xxx)

                //签发hash  根据KAI_SECRET秘钥  HS256方式生成
                .signWith(SignatureAlgorithm.HS256, KAI_SECRET)
                .compact();

        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE);
        String userKey = LOGIN_TOKEN_KEY + JwtToken;

        RedisUtil.set(userKey, loginUser, EXPIRE);
        return JwtToken;
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public static LoginUser getTokenUser(String token) {
        LoginUser user = (LoginUser) RedisUtil.get(LOGIN_TOKEN_KEY + token);
        if (ObjectUtils.isEmpty(user)){
            return new LoginUser();
        }
        return user;
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public static void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE);
        // 根据uuid将loginUser缓存
        String userKey = LOGIN_TOKEN_KEY + loginUser.getToken();
        RedisUtil.set(userKey, loginUser, EXPIRE);
    }


    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StrUtil.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(KAI_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader(HEADER);
            if(StrUtil.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(KAI_SECRET).parseClaimsJws(jwtToken);//根据秘钥解析，如果异常，则返回false
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 根据token获取nickname
     * @param request
     * @return
     */
    public static String getNameByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER);
        if(StrUtil.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KAI_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("username");
    }


    /**
     * 生成加密密码
     */
    public static String encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(SALT_BYTE), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return toHex(f.generateSecret(spec).getEncoded());
    }

    /**
     * 十六进制字符串转成二进制字符串
     */
    private static byte[] fromHex(String hex){
        byte[] binary = new byte[hex.length()/2];
        for (int i = 0; i <  binary.length; i++){
            binary[i] = (byte) Integer.parseInt(hex.substring(2*i,2*i+2),16);
        }
        return binary;
    }

    private static String toHex(byte[] array){
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength >0){
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }else {
            return hex;
        }
    }

    /**
     * 判断密码是否相同
     * @param rawPassword 真实密码
     * @param encodePassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodePassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String encryptRawPassword = encryptPassword(rawPassword);
        return encryptRawPassword.equals(encodePassword);
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println(encryptPassword("123"));
    }
}

