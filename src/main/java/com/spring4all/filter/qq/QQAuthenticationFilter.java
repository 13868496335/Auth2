package com.spring4all.filter.qq;

import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QQAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final static String CODE = "code";

    /**
     * 获取 Token 的 API
     */
    private final static String accessTokenUri = "https://graph.qq.com/oauth2.0/token";

    /**
     * grant_type 由腾讯提供
     */
    private final static String grantType = "authorization_code";

    /**
     * client_id 由腾讯提供
     */
    static final String clientId = "101386962";

    /**
     * client_secret 由腾讯提供
     */
    private final static String clientSecret = "2a0f820407df400b84a854d054be8b6a";

    /**
     * redirect_uri 腾讯回调地址
     */
    private final static String redirectUri = "http://www.ictgu.cn/login/qq";

    /**
     * 获取 OpenID 的 API 地址
     */
    private final static String openIdUri = "https://graph.qq.com/oauth2.0/me?access_token=";

    /**
     * 获取 token 的地址拼接
     */
    private final static String TOKEN_ACCESS_API = "%s?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    /**
     * 设置自定义链接url
      * @param defaultFilterProcessesUrl
     */
    public QQAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "GET"));
    }

    /**
     *  如果用户成功登陆并授权，
     *  则会跳转到回调地址,在url中也会返回 Authorization  code
     *  通过它，可以获取访问令牌。
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        /**
         *  1.跳转到回调地址,在url中也会返回 Authorization  code，再次带上Code请求，就会返回qq令牌
         */
        String code = request.getParameter(CODE);
        System.out.println("Code : " + code);
        String tokenAccessApi = String.format(TOKEN_ACCESS_API, accessTokenUri, grantType, clientId, clientSecret, code, redirectUri);
        QQToken qqToken = this.getToken(tokenAccessApi);
        System.out.println(JSON.toJSONString(qqToken));
        if (qqToken != null){
            /**
             * 通过 令牌里面的 AccessToken ，带上它请求  openIdUri 会返回openId
             */
            String openId = getOpenId(qqToken.getAccessToken());
            System.out.println(openId);
            if (openId != null){
                // 生成验证 authenticationToken
                // AccessToken是账号 openId是密码传入认证器里面认证
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(qqToken.getAccessToken(), openId);
                // 返回验证结果
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        return null;
    }

    /**
     *   通过授权code拼接起来的URL,来获取 accessToken
     * @param tokenAccessApi
     * @return
     * @throws IOException
     */
    private QQToken getToken(String tokenAccessApi) throws IOException{
        Document document = Jsoup.connect(tokenAccessApi).get();
        String tokenResult = document.text();
        String[] results = tokenResult.split("&");
        if (results.length == 3){
            QQToken qqToken = new QQToken();
            String accessToken = results[0].replace("access_token=", "");
            int expiresIn = Integer.valueOf(results[1].replace("expires_in=", ""));
            String refreshToken = results[2].replace("refresh_token=", "");
            qqToken.setAccessToken(accessToken);
            qqToken.setExpiresIn(expiresIn);
            qqToken.setRefresh_token(refreshToken);
            return qqToken;
        }
        return null;
    }

    /**
     *   通过 accessToken 来获取 OpenId
     * @param accessToken
     * @return
     * @throws IOException
     */
    private String getOpenId(String accessToken) throws IOException{
        String url = openIdUri + accessToken;
        Document document = Jsoup.connect(url).get();
        String resultText = document.text();
        Matcher matcher = Pattern.compile("\"openid\":\"(.*?)\"").matcher(resultText);
        if (matcher.find()){
            return matcher.group(1);
        }
        return null;
    }

    class QQToken {

        /**
         * token
         */
        private String accessToken;

        /**
         * 有效期
         */
        private int expiresIn;

        /**
         * 刷新时用的 token
         */
        private String refresh_token;

        String getAccessToken() {
            return accessToken;
        }

        void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }
    }


}
