package com.example.CodeJavaApp.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "bill")
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mahd", unique = true)
    private String mahd;
    @Column(name = "date")
    private Date date;
    @OneToOne
    @JoinColumn(name = "id_user") //Thong qua khoa ngoai id_user
    private User user;
    @Column(name = "note")
    private String note;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address1;
    @Column(name = "status")
    private Integer status;
    @Column(name = "user_id")
    private Integer userid;


    @OneToMany(mappedBy = "bills", cascade = CascadeType.ALL) //Quen hệ 1-n với đối tượng Bills. 1 người có thể mua nhiều hóa đơn
    // MapopedBy trỏ tới tên user ở trong Bills.
    private Collection<BillDetail> billDetails;

    public Bills() {
    }


    public Bills(Long id, String mahd, Date date, User user, String note, String phone, String address1, Integer status, Integer userid, Collection<BillDetail> billDetails) {
        this.id = id;
        this.mahd = mahd;
        this.date = date;
        this.user = user;
        this.note = note;
        this.phone = phone;
        this.address1 = address1;
        this.status = status;
        this.userid = userid;
        this.billDetails = billDetails;
    }

    public Collection<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(Collection<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Bills{" +
                "id=" + id +
                ", mahd='" + mahd + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", note='" + note + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
