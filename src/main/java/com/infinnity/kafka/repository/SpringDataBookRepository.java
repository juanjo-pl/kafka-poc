package com.infinnity.kafka.repository;


import com.infinnity.kafka.domain.Book;
import com.infinnity.kafka.domain.BookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataBookRepository extends JpaRepository<Book, BookId>{

    List<Book> findByOrderByCreatedAtDesc();

}
