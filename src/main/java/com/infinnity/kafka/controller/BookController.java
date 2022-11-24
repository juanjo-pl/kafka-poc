package com.infinnity.kafka.controller;

import com.github.javafaker.Faker;
import com.infinnity.kafka.events.BookEvent;
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

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final SpringDataBookRepository springDataBookRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final Faker faker;

    @PostMapping(value = "/create")
    public void sendMessageToKafkaTopic() {
        bookService.createBook();
    }

    @PostMapping(value = "/republish")
    @Transactional
    public void republish(@Param("limit") long limit) {
        springDataBookRepository.findAll().stream().limit(limit)
                .forEach(book -> {
                    log.debug("Republish {}", book.getId());
                    book.updateTitle(faker.book().title() + " " + (book.getVersion() + 1));
                    applicationEventPublisher.publishEvent(new BookEvent(book));
                });
    }

}
