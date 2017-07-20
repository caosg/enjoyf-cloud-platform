package com.enjoyf.platform.userservice.domain;

import com.enjoyf.platform.autoconfigure.context.ApplicationContextProvider;
import com.enjoyf.platform.userservice.config.ApplicationProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RedisHash("userProfiles")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "profile_no", nullable = false, updatable = false)
    @Indexed
    private String profileNo;

    @Size(max = 24)
    @Column(name = "mobile_no")
    private String mobileNo;

    @NotNull
    @Column(name = "nick")
    private String nick;

    @Column(name = "lowercase_nick")
    private String lowercaseNick;

    @Column(name = "discription")
    private String discription;

    @Column(name = "icon")
    private String icon;

    @Column(name = "icons")
    private String icons;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "profile_key")
    private String profileKey;

    @Column(name = "flag")
    private Integer flag = 0;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "sex")
    private Integer sex = -1;

    @Column(name = "birth_day")
    private String birthDay;

    @Column(name = "province_id")
    private Integer provinceId = 0;

    @Column(name = "city_id")
    private Integer cityId = 0;

    @Column(name = "qq")
    private String qq;

    @Size(max = 32)
    @Column(name = "domain")
    private String domain;

    @Size(max = 64)
    @Column(name = "signature")
    private String signature;//签名

    @Column(name = "level")
    private Integer level = 0;//等级

    @Column(name = "experience")
    private Integer experience = 0;//经验

    @Size(max = 64)
    @Column(name = "back_pic")
    private String backPic;//背景图

    @Size(max = 64)
    @Column(name = "hobby")
    private String hobby;//兴趣爱好

    @NotNull
    @Column(name = "created_time")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createdTime;

    @Column(name = "updated_time")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime updatedTime;

    @Column(name = "created_ip")
    private String createdIp;

    @NotNull
    @Size(max = 32)
    @Column(name = "app_key", length = 32, nullable = false)
    private String appKey = "default";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileNo() {
        return profileNo;
    }

    public UserProfile profileNo(String profileNo) {
        this.profileNo = profileNo;
        return this;
    }

    public void setProfileNo(String profileNo) {
        this.profileNo = profileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public UserProfile mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getNick() {
        return nick;
    }

    public UserProfile nick(String nick) {
        this.nick = nick;
        return this;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLowercaseNick() {
        return lowercaseNick;
    }

    public UserProfile lowercaseNick(String lowercaseNick) {
        this.lowercaseNick = lowercaseNick;
        return this;
    }

    public void setLowercaseNick(String lowercaseNick) {
        this.lowercaseNick = lowercaseNick;
    }

    public String getDiscription() {
        return discription;
    }

    public UserProfile discription(String discription) {
        this.discription = discription;
        return this;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getIcon() {
        //处理默认头像
        if (StringUtils.isEmpty(icon)) {
            ApplicationProperties applicationProperties = ApplicationContextProvider.getApplicationContext().getBean(ApplicationProperties.class);
            switch (getSex()) {
                case 0:
                    icon = applicationProperties.getIcon().getGirlIcon();
                    break;
                case 1:
                    icon = applicationProperties.getIcon().getBodyIcon();
                    break;
                default:
                    icon = applicationProperties.getIcon().getDefaultIcon();
            }

        }
        return icon;
    }

    public UserProfile icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcons() {
        return icons;
    }

    public UserProfile icons(String icons) {
        this.icons = icons;
        return this;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public UserProfile accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public UserProfile profileKey(String profileKey) {
        this.profileKey = profileKey;
        return this;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public Integer getFlag() {
        return flag;
    }

    public UserProfile flag(Integer flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRealName() {
        return realName;
    }

    public UserProfile realName(String realName) {
        this.realName = realName;
        return this;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        if (sex == null) {
            sex = -1;
        }
        return sex;
    }

    public UserProfile sex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public UserProfile birthDay(String birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public UserProfile provinceId(Integer provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public UserProfile cityId(Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getQq() {
        return qq;
    }

    public UserProfile qq(String qq) {
        this.qq = qq;
        return this;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public UserProfile createdTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getUpdatedTime() {
        return updatedTime;
    }

    public UserProfile updatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(ZonedDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public UserProfile createdIp(String createdIp) {
        this.createdIp = createdIp;
        return this;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getAppKey() {
        return appKey;
    }

    public UserProfile appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfile userProfile = (UserProfile) o;
        return !(userProfile.id == null || id == null) && Objects.equals(id, userProfile.id);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", profileNo='" + getProfileNo() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", nick='" + getNick() + "'" +
            ", lowercaseNick='" + getLowercaseNick() + "'" +
            ", discription='" + getDiscription() + "'" +
            ", icon='" + getIcon() + "'" +
            ", icons='" + getIcons() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", profileKey='" + getProfileKey() + "'" +
            ", flag='" + getFlag() + "'" +
            ", realName='" + getRealName() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthDay='" + getBirthDay() + "'" +
            ", provinceId='" + getProvinceId() + "'" +
            ", cityId='" + getCityId() + "'" +
            ", qq='" + getQq() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", createdIp='" + getCreatedIp() + "'" +
            ", appKey='" + getAppKey() + "'" +
            "}";
    }


}
