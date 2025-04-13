package com.naveend3v.readerhub.dto.cart;

import com.naveend3v.readerhub.model.Bookslist;
import com.naveend3v.readerhub.model.Cart;

public class CartItemsDto {
    private Integer id;
    private Integer quantity;
    private Bookslist bookslist;

    public CartItemsDto() {
    }

    public CartItemsDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setBook(cart.getBookslist());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Bookslist getBook() {
        return bookslist;
    }

    public void setBook(Bookslist bookslist) {
        this.bookslist = bookslist;
    }
}
