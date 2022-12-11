package common.dto;

import java.util.Date;

/**
 * @title: ReservationDTO
 * @Author Qihang Yin
 * @Date: 2022/12/11 21:27
 * @Version 1.0
 */
public class ReservationDTO {

    private int id;

    private String userId;

    private String userName;

    private int numberOfPeople;

    private Date time;

    public ReservationDTO(String userId, String userName, int numberOfPeople, Date time) {
        this.userId = userId;
        this.userName = userName;
        this.numberOfPeople = numberOfPeople;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public Date getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
