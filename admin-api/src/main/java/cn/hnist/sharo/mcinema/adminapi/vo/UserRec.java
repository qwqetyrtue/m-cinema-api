package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.UserBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.function.Function;

public class UserRec extends UserBase implements VoRec<UserBase> {

    @NotNull(message = "用户Id不能为空", groups = {OneBy.PK.class})
    private Long userId;
    @Size(min = 0, max = 49, message = "姓名长度超出范围", groups = {InsertBy.class, UpdateFor.Profile.class, ListBy.Name.class})
    @NotBlank(message = "昵称不能为空", groups = {InsertBy.class})
    private String name;
    @Range(min = 1, max = 3, message = "性别可取值为:1,2,3", groups = {InsertBy.class, UpdateFor.Profile.class})
    @NotNull(message = "性别不能为空", groups = {InsertBy.class})
    private Integer gender;
    @Size(min = 10, max = 13, message = "手机号长度超出范围", groups = {InsertBy.class, UpdateFor.Secure.class})
    @NotBlank(message = "手机号不能为空", groups = {InsertBy.class})
    private String phone;
    @NotBlank(message = "密码不能为空", groups = {InsertBy.class})
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$", message = "密码格式错误", groups = {UpdateFor.Secure.class})
    private String pwd;
    @Range(min = 8, max = 100, message = "年龄值超出范围", groups = {InsertBy.class, UpdateFor.Profile.class})
    private Integer age;
    @Email(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",
            message = "邮箱格式错误", groups = {InsertBy.class, UpdateFor.Secure.class})
    private String email;

    @Override
    public UserBase updater(Function<UserBase, UserBase> set) {
        UserBase update = new UserBase();
        // pk
        update.setUserId(userId);
        // 更新修改时间
        update.setUpdateTime(LocalDateTime.now());
        // 可修改属性
        return set.apply(update);
    }

    public UserBase profileUpdater() {
        return updater(update -> {
            update.setName(name);
            update.setGender(gender);
            update.setAge(age);
            update.setSign(getSign());
            return update;
        });
    }

    public UserBase accountUpdater() {
        return updater(update -> {
            update.setPhone(phone);
            update.setPwd(pwd);
            update.setEmail(email);
            return update;
        });
    }

    @Override
    public UserBase adder(Function<UserBase, UserBase> set) {
        UserBase add = new UserBase();
        LocalDateTime now = LocalDateTime.now();
        add.setCreateTime(now);
        add.setUpdateTime(now);
        add.setName(name);
        add.setGender(gender);
        add.setPhone(phone);
        add.setPwd(pwd);
        add.setAge(age);
        add.setEmail(email);
        add.setSign(getSign());
        add.setAvatar(getAvatar());
        add.setDeleted(false);
        return set.apply(add);
    }

    public UserBase adder() {
        return adder(add -> add);
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeDown;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTimeDown;
    private Integer ageUp;
    private Integer ageDown;


    public LocalDateTime getCreateTimeUp() {
        return createTimeUp;
    }

    public void setCreateTimeUp(LocalDateTime createTimeUp) {
        this.createTimeUp = createTimeUp;
    }

    public LocalDateTime getCreateTimeDown() {
        return createTimeDown;
    }

    public void setCreateTimeDown(LocalDateTime createTimeDown) {
        this.createTimeDown = createTimeDown;
    }

    public Integer getAgeUp() {
        return ageUp;
    }

    public void setAgeUp(Integer ageUp) {
        this.ageUp = ageUp;
    }

    public Integer getAgeDown() {
        return ageDown;
    }

    public void setAgeDown(Integer ageDown) {
        this.ageDown = ageDown;
    }

    public LocalDateTime getLastLoginTimeUp() {
        return lastLoginTimeUp;
    }

    public void setLastLoginTimeUp(LocalDateTime lastLoginTimeUp) {
        this.lastLoginTimeUp = lastLoginTimeUp;
    }

    public LocalDateTime getLastLoginTimeDown() {
        return lastLoginTimeDown;
    }

    public void setLastLoginTimeDown(LocalDateTime lastLoginTimeDown) {
        this.lastLoginTimeDown = lastLoginTimeDown;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
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
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
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
    public Integer getAge() {
        return age;
    }

    @Override
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
}
