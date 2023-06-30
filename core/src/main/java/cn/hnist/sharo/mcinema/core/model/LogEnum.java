package cn.hnist.sharo.mcinema.core.model;

public class LogEnum {
    public enum Type {
        GENERAL(0, "一般日志"),
        SECURITY(1, "安全日志"),
        TICKET(2, "票务日志"),
        OTHER(3, "其他日志");
        private final Integer record;
        private final String desc;

        Type(Integer record, String desc) {
            this.record = record;
            this.desc = desc;
        }

        public Integer getRecord() {
            return record;
        }
        public String getDesc() {
            return desc;
        }
    }

    public enum Action {
        LOGIN("管理员登录"),
        LOGOUT("管理员登出"),
        UPDATE_PWD("修改密码"),
        UNSUBSCRIBE("注销账户"),
        CHANGING_ROLES("修改角色"),
        INSERT_ADMIN("添加管理员账户"),
        UPDATE_ADMIN_INFO("修改管理员信息"),
        UPDATE_ADMIN_ACCOUNT("修改管理员账号"),
        UPDATE_ADMIN_ROLE("修改管理员角色"),
        DELETE_ADMIN("注销管理员账号"),
        SET_SYSTEM_CONSTANT("设置系统变量"),
        DELETE_SYSTEM_CONSTANT("删除系统变量");

        private final String action;

        Action(String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }
    }
}
