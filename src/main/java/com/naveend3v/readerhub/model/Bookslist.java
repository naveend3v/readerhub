package com.naveend3v.readerhub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookslist")
public class Bookslist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "published_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishedDate;

    @Column(name = "category")
    private String category;

    @Column(name = "image_path")
    private String bookCoverImagePath;

    // Non parameterized constructor
    public Bookslist() {
    }

    // Parameterized constructor
    public Bookslist(String bookName, String author, String description, double price, String category, String bookCoverImagePath, LocalDate publishedDate) {
        this.bookName = bookName;
        this.author = author;
        this.description = description;
        this.price = price;
        this.category = category;
        this.bookCoverImagePath = bookCoverImagePath;
        this.publishedDate = publishedDate;
    }

    // Getter and Setter

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookCoverImagePath() {
        return bookCoverImagePath;
    }

    public void setBookCoverImagePath(String bookCoverImagePath) {
        this.bookCoverImagePath = bookCoverImagePath;
    }
}
