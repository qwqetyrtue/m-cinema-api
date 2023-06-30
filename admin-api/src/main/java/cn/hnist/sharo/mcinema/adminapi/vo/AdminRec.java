package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.*;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class AdminRec extends AdminBase implements VoRec<AdminBase> {
    @NotNull(message = "管理员ID不能为空", groups = {OneBy.PK.class})
    private Integer adminId;
    @NotBlank(message = "姓名不能为空", groups = {InsertBy.class})
    private String name;
    @NotBlank(message = "账号不能为空", groups = {InsertBy.class})
    private String account;
    @NotBlank(message = "密码不能为空", groups = {InsertBy.class})
    private String pwd;
    @NotBlank(message = "手机号不能为空", groups = {InsertBy.class})
    private String phone;

    @NotBlank(message = "用户名不能为空", groups = {Login.class})
    private String username;
    @NotBlank(message = "密码不能为空", groups = {Login.class})
    private String password;
    @NotBlank(message = "验证码不能为空", groups = {Login.class})
    private String captcha;
    @NotBlank(message = "UUID不能为空", groups = {Login.class})
    private String UUID;
    @NotNull(message = "缺失必须的参数", groups = {ListBy.Deleted.class})
    private Boolean deleted;

    @Override
    public AdminBase adder(Function<AdminBase, AdminBase> set) {
        LocalDateTime now = LocalDateTime.now();
        AdminBase add = new AdminBase();
        add.setName(name);
        add.setAccount(account);
        add.setPhone(phone);
        add.setPwd(pwd);
        add.setCreateTime(now);
        add.setUpdateTime(now);
        add.setDeleted(false);
        add.setAvatar("");
        return set.apply(add);
    }

    public AdminBase adder() {
        return adder(add -> add);
    }

    @Override
    public AdminBase updater(Function<AdminBase, AdminBase> set) {
        AdminBase update = new AdminBase();
        // pk
        update.setAdminId(adminId);
        // 更新修改时间
        update.setUpdateTime(LocalDateTime.now());
        // 可修改属性
        return set.apply(update);
    }

    public AdminBase accountUpdater() {
        return updater((update) -> {
            update.setAccount(account);
            update.setPhone(phone);
            update.setPwd(pwd);
            return update;
        });
    }

    public AdminBase profileUpdater() {
        return updater((update) -> {
            update.setName(name);
            return update;
        });
    }

    public AdminBase roleListUpdater() {
        return updater((update) -> {
            update.setRoleIdList(getRoleIdList());
            return update;
        });
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Integer getAdminId() {
        return adminId;
    }

    @Override
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
