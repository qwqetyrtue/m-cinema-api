package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.Login;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.UserBase;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.function.Function;

public class UserRec extends UserBase implements VoRec<UserBase> {
    @NotBlank(message = "账号不能能为空", groups = {Login.Account.class, InsertBy.class})
    private String username;
    @NotBlank(message = "手机号不能为空", groups = {Login.SMS.class})
    private String phone;
    @NotBlank(message = "密码不能为空", groups = {Login.Account.class, InsertBy.class, UpdateFor.Pwd.class})
    private String password;
    @NotBlank(message = "验证码不能为空", groups = {Login.SMS.class})
    private String captcha;
    @NotBlank(message = "UUID不能为空", groups = {Login.SMS.class})
    private String UUID;
    @NotBlank(message = "新密码不能为空", groups = {UpdateFor.Pwd.class})
    private String newPassword;
    @Size(max = 20, min = 1, message = "姓名长度超出范围", groups = {UpdateFor.Profile.class})
    private String name;
    @Range(min = 0, max = 3, message = "性别值超出范围", groups = {UpdateFor.Profile.class})
    private Integer gender;
    @Range(min = 10, max = 100, message = "年龄值超出范围", groups = {UpdateFor.Profile.class})
    private Integer age;
    @Size(min = 0, max = 254, message = "个性签名长度超出范围", groups = {UpdateFor.Profile.class})
    private String sign;

    @Override
    public UserBase adder(Function<UserBase, UserBase> fuc) {
        UserBase add = new UserBase();
        add.setCreateTime(LocalDateTime.now());
        return fuc.apply(add);
    }

    public UserBase adder() {
        return adder(add -> add);
    }

    @Override
    public UserBase updater(Function<UserBase, UserBase> fuc) {
        UserBase update = new UserBase();
        update.setUpdateTime(LocalDateTime.now());
        return fuc.apply(update);
    }

    public UserBase updaterProfile(Long userId) {
        return updater((update) -> {
            update.setUserId(userId);
            update.setName(name);
            update.setGender(gender);
            update.setAge(age);
            update.setSign(sign);
            return update;
        });
    }

    public UserBase updaterPwd(Long userId) {
        return updater((update) -> {
            update.setUserId(userId);
            update.setPwd(password);
            return update;
        });
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

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
    public Integer getGender() {
        return gender;
    }

    @Override
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getSign() {
        return sign;
    }

    @Override
    public void setSign(String sign) {
        this.sign = sign;
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
