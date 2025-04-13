package com.naveend3v.readerhub.dto.bookslist;

public class BookslistDto {
    private int id;
    private String bookName;
    private String bookCoverImagePath;
    private double price;

    public BookslistDto(int id, String bookName, double price, String bookCoverImagePath) {
        this.id = id;
        this.bookName = bookName;
        this.price = price;
        this.bookCoverImagePath = bookCoverImagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCoverImagePath() {
        return bookCoverImagePath;
    }

    public void setBookCoverImagePath(String bookCoverImagePath) {
        this.bookCoverImagePath = bookCoverImagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
