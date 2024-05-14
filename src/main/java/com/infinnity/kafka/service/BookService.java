package com.infinnity.kafka.service;

import com.github.javafaker.Faker;
import com.infinnity.kafka.domain.Book;
import com.infinnity.kafka.domain.BookId;
import com.infinnity.kafka.events.BookAsyncEvent;
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

    public Book createBook() {
        var createdBook = createBookInDB();
        applicationEventPublisher.publishEvent(new BookEvent(createdBook));
        return createdBook;
    }

    public Book createBookAsync() {
        var createdBook = createBookInDB();
        applicationEventPublisher.publishEvent(new BookAsyncEvent(createdBook));
        return createdBook;
    }

    private Book createBookInDB() {
        var now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        var fakerBook = faker.book();
        var book = Book.builder()
                .id(new BookId())
                .title(fakerBook.title())
                .author(fakerBook.author())
                .year(getRandomNumber(now.getYear()))
                .build();
        return springDataBookRepository.save(book);
    }

}
