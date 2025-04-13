package com.naveend3v.readerhub.service;

import com.naveend3v.readerhub.dto.bookslist.BookslistDto;
import com.naveend3v.readerhub.model.Bookslist;
import com.naveend3v.readerhub.repository.BookslistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookslistService {
    @Autowired
    private BookslistRepository bookslistRepository;

    public BookslistService(BookslistRepository bookslistRepository) {
        this.bookslistRepository = bookslistRepository;
    }

    public List<BookslistDto> findAllBooks(){
        List<BookslistDto> bookslistDtos = bookslistRepository.findAll()
                .stream()
                .map(books -> new BookslistDto(
                        books.getId(),
                        books.getBookName(),
                        books.getPrice(),
                        books.getBookCoverImagePath()
                        ))
                .toList();
        return bookslistDtos;
    }

    public Optional<Bookslist> findByBook(Integer id) {
        Optional<Bookslist> getBook = bookslistRepository.findById(id);
        if(getBook.isPresent()) {
            return getBook;
        }
        return Optional.empty();
    }

    public Bookslist saveBook(Bookslist book) {
        return bookslistRepository.save(book);
    }

    public String removeBook(Integer id) {
        try{
            Optional<Bookslist> getBook = bookslistRepository.findById(id);
            if(getBook.isPresent()){
                bookslistRepository.delete(getBook.get());
                return "Book removed successfully";
            } else {
                return "Book not found";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Bookslist updateBook(Bookslist book, Integer id) {
        Optional<Bookslist> bookToUpdate = bookslistRepository.findById(id);
        try {
            if (bookToUpdate.isPresent()) {
                bookToUpdate.get().setBookName(book.getBookName());
                bookToUpdate.get().setAuthor(book.getAuthor());
                bookToUpdate.get().setPrice(book.getPrice());
                bookToUpdate.get().setCategory(book.getCategory());
                bookToUpdate.get().setPublishedDate(book.getPublishedDate());
                bookToUpdate.get().setBookCoverImagePath(book.getBookCoverImagePath());
                return bookslistRepository.save(bookToUpdate.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating book!");
        }
        return null;
    }
}
