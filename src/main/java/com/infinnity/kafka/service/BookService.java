package com.infinnity.kafka.service;

import com.github.javafaker.Faker;
import com.infinnity.kafka.domain.Book;
import com.infinnity.kafka.domain.BookId;
import com.infinnity.kafka.events.BookEvent;
import com.infinnity.kafka.repository.SpringDataBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class BookService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final SpringDataBookRepository springDataBookRepository;
    private final Faker faker;
    private final Random random = new Random();

    private int getRandomNumber(int max) {
        return random.nextInt(max - 1500) + 1500;
    }

    public void createBook() {
        var now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        var generatedBook = faker.book();
        var book = Book.builder()
                .id(new BookId())
                .title(generatedBook.title())
                .author(generatedBook.author())
                .year(getRandomNumber(now.getYear()))
                .build();

        var createdBook = springDataBookRepository.save(book);
        log.debug("Created bookId={}", createdBook.getId());

        applicationEventPublisher.publishEvent(new BookEvent(createdBook));
    }

}
