package cn.hnist.sharo.mcinema.adminapi.config.shiro;

import cn.hnist.sharo.mcinema.db.redis.RedisSessionUtil;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import io.github.classgraph.json.JSONDeserializer;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AdminSessionDAO extends AbstractSessionDAO {
    private final RedisSessionUtil redisUtil;
    private final String sessionRoot;
    private final Long expireTime;
    private final TimeUnit timeUnit;

    public AdminSessionDAO(RedisSessionUtil redisUtil, String sessionRoot, Long sessionTimeout, TimeUnit timeUnit) {
        this.redisUtil = redisUtil;
        this.sessionRoot = sessionRoot;
        this.expireTime = sessionTimeout;
        this.timeUnit = timeUnit;
    }


    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("do create");
        Serializable sessionId = generateSessionId(session);
        this.assignSessionId(session, sessionId);
        redisUtil.set(sessionRoot + sessionId, session, expireTime, timeUnit);
        return sessionId;
    }


    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("do read " + sessionId);
//        Throwable stack = new Throwable();
//        stack.printStackTrace();
        try {
            return (Session) redisUtil.get(sessionRoot + sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        System.out.println("do update");
        try {
            redisUtil.set(sessionRoot + session.getId(), session, expireTime, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Session session) {
        System.out.println("do delete");
        System.out.println(((SimpleSession) session).isExpired());
        try {
            redisUtil.delete(sessionRoot + session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        try {
            Collection<Session> collection = new HashSet<>();
            Set<String> keys = redisUtil.getKeys(sessionRoot + "?");
            for (String each : keys) {
                collection.add((Session) redisUtil.get(each));
            }
            return collection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
