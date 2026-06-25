package com.example.restapiuser.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="TUSER")
public class UserEntity {

    @Id
    @Column(name = "USERID", length = 50, nullable = false)
    private String userid;

    @Column(name = "USERNAME", length = 100, nullable = false)
    private String username;

    @Column(name = "PASSWD", length = 100, nullable = false)
    private String passwd;

    @Column(name = "EMAIL", length = 200)
    private String email;

    @Column(name = "INDATE", nullable = false, updatable = false)
    private LocalDateTime indate;

    // 기본생성자
    public UserEntity() {}

    // 생성자 override
    public UserEntity(String userid, String username, String passwd, String email) {
        this.userid = userid;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
    }

    @PrePersist
    public void prePersist() {
        if (this.indate == null) {
            this.indate = LocalDateTime.now();
        }
    }

    // Getter
    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getIndate() {
        return indate;
    }

    // Setter
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIndate(LocalDateTime indate) {
        this.indate = indate;
    }

    // toString

    @Override
    public String toString() {
        return "UserEntity{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                ", email='" + email + '\'' +
                ", indate=" + indate +
                '}';
    }
}