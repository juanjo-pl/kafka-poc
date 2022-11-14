package com.infinnity.kafka.service;

import com.infinnity.kafka.domain.Book;
import com.infinnity.kafka.domain.BookId;
import com.infinnity.kafka.events.BookEvent;
import com.infinnity.kafka.repository.SpringDataBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class BookService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final SpringDataBookRepository springDataBookRepository;


    public void createBook() {
        var now = ZonedDateTime.now();
        var book = new Book(new BookId(), now, now, 0, false, "tilte", "author");
        var createdBook = springDataBookRepository.save(book);
        log.info("Created book={}", createdBook);

        applicationEventPublisher.publishEvent(new BookEvent(createdBook));
    }

}
