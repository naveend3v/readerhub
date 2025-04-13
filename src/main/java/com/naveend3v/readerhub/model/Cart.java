package com.naveend3v.readerhub.model;

import jakarta.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Bookslist bookslist;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserInfo userInfo;

    private int quantity;

    public Cart(){}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bookslist getBookslist() {
        return bookslist;
    }

    public void setBookslist(Bookslist bookslist) {
        this.bookslist = bookslist;
    }

    public UserInfo getUserDetails() {
        return userInfo;
    }

    public void setUserDetails(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
