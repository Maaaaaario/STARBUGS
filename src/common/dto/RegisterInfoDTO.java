package common.dto;

import java.util.Date;

/**
 * @title: RegisterInfoDto
 * @Author Qihang Yin
 * @Date: 2022/12/6 15:02
 * @Version 1.0
 */
public class RegisterInfoDto {

    private String id;

    private int stamps;

    private boolean vipStatus;

    private Date vipExpireDate;

    public RegisterInfoDto(String id, int stamps, boolean vipStatus, Date vipExpireDate) {
        this.id = id;
        this.stamps = stamps;
        this.vipStatus = vipStatus;
        this.vipExpireDate = vipExpireDate;
    }

    public String getId() {
        return id;
    }

    public int getStamps() {
        return stamps;
    }

    public boolean getVipStatus() {
        return vipStatus;
    }

    public Date getVipExpireDate() {
        return vipExpireDate;
    }
}
