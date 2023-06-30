package cn.hnist.sharo.mcinema.adminapi.config.shiro;

import cn.hnist.sharo.mcinema.core.util.DataUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 设置 shiro会话管理
 */
public class AdminSessionManagement extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(AdminSessionManagement.class);

    // session的键值
    private final String SESSION_TOKEN_KEY;
    private final Long SESSION_TIMEOUT;
    // session的来源
    private static final String SESSION_SOURCE = "request header";

    public AdminSessionManagement(String session_token_key, Long session_timeout) {
        this.SESSION_TOKEN_KEY = session_token_key;
        this.SESSION_TIMEOUT = session_timeout;
        this.setGlobalSessionTimeout(SESSION_TIMEOUT * 1000); // 毫秒为单位
        SimpleCookie mCookie = new SimpleCookie(SESSION_TOKEN_KEY);
        mCookie.setHttpOnly(false); // 关闭cookie的http-only,使客户端可以读取
        this.setSessionIdCookie(mCookie);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(SESSION_TOKEN_KEY);
        if (DataUtil.checkEmpty(id)) {
            WebUtils.toHttp(response).setHeader(SESSION_TOKEN_KEY, id);
            // 设置session来源
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, SESSION_SOURCE);
            // 设置sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            // 设置标记此sessionId为有效
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
        return super.getSessionId(request, response);
    }

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        if (sessionId == null) {
            log.debug("Unable to resolve session ID from SessionKey [{}].  Returning null to indicate a "
                    + "session could not be found.", sessionKey);
            return null;
        }

//         ***************Add By Goma****************
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (request != null) {
            Object s = request.getAttribute(sessionId.toString());
            if (s != null) {
                return (Session) s;
            }
        }
        // ***************Add By Goma****************

        Session s = retrieveSessionFromDataSource(sessionId);
        if (s == null) {
            // session ID was provided, meaning one is expected to be found, but
            // we couldn't find one:
            String msg = "Could not find session with ID [" + sessionId + "]";
            throw new UnknownSessionException(msg);
        }

        // ***************Add By Goma****************
        if (request != null) {
            request.setAttribute(sessionId.toString(), s);
        }
        // ***************Add By Goma****************
        return s;
    }

    public String getSESSION_TOKEN_KEY() {
        return SESSION_TOKEN_KEY;
    }

    public Long getSESSION_TIMEOUT() {
        return SESSION_TIMEOUT;
    }
}
