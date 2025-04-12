package com.naveend3v.readerhub.dto.bookslist;

public class BookslistDto {
    private int id;
    private String bookName;
    private String bookCoverImagePath;

    public BookslistDto(int id, String bookName, String bookCoverImagePath) {
        this.id = id;
        this.bookName = bookName;
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
}
