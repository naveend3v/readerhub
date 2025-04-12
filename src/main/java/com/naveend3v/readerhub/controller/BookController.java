package com.naveend3v.readerhub.controller;

import com.naveend3v.readerhub.service.BookslistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BookController {
    @Autowired
    private BookslistService bookslistService;

    public BookController(BookslistService bookslistService) {
        this.bookslistService = bookslistService;
    }

    @GetMapping("/welcome")
    public ResponseEntity welcome() {
        return ResponseEntity.ok("Welcome to Book Store");
    }

    @GetMapping("/books")
    public ResponseEntity getAllBooks(){
        return ResponseEntity.ok(bookslistService.findAllBooks());
    }

}
