package com.infinnity.kafka.controller;

import com.github.javafaker.Faker;
import com.infinnity.kafka.domain.BookResponse;
import com.infinnity.kafka.events.BookAsyncEvent;
import com.infinnity.kafka.events.BookEvent;
import com.infinnity.kafka.mapper.BookMapper;
import com.infinnity.kafka.repository.SpringDataBookRepository;
import com.infinnity.kafka.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final SpringDataBookRepository springDataBookRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final Faker faker;
    private final BookMapper bookMapper;

    @PostMapping(value = "/books")
    public BookResponse createBook() {
        var book = bookService.createBook();
        return bookMapper.toResponse(book);
    }

    @PostMapping(value = "/books/republish")
    @Transactional
    public List<BookResponse> republish(@Param("limit") long limit) {
        var limitValue = limit > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) limit;
        var bookResponses = new ArrayList<BookResponse>(limitValue);
        springDataBookRepository.findByOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .forEach(book -> {
                    log.debug("Republish {}", book.getId());
                    book.updateTitle(faker.book().title() + " " + (book.getVersion() + 1));
                    bookResponses.add(bookMapper.toResponse(book));
                    applicationEventPublisher.publishEvent(new BookEvent(book));
                });
        return bookResponses;
    }

    @PostMapping(value = "/async/books")
    public BookResponse asyncCreateBook() {
        var book = bookService.createBookAsync();
        return bookMapper.toResponse(book);
    }

    @PostMapping(value = "/async/books/republish")
    @Transactional
    public List<BookResponse> asyncRepublish(@Param("limit") long limit) {
        var limitValue = limit > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) limit;
        var bookResponses = new ArrayList<BookResponse>(limitValue);
        springDataBookRepository.findByOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .forEach(book -> {
                    log.debug("Republish {}", book.getId());
                    book.updateTitle(faker.book().title() + " " + (book.getVersion() + 1));
                    bookResponses.add(bookMapper.toResponse(book));
                    applicationEventPublisher.publishEvent(new BookAsyncEvent(book));
                });
        return bookResponses;
    }

}
