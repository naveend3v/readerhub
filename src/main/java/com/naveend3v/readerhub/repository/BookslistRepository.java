package com.naveend3v.readerhub.repository;

import com.naveend3v.readerhub.model.Bookslist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookslistRepository extends JpaRepository<Bookslist,Integer> {
    List<Bookslist> findAll();
    Optional<Bookslist> findById(Integer id);
    Bookslist save(Bookslist book);
    String removeById(Integer id);
}
