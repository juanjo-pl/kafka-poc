package com.infinnity.kafka.task;

import com.infinnity.kafka.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceGenerator {

    private final BookService bookService;

    @Scheduled(
            fixedRateString = "${kafka-poc.bookServiceGenerator.fixedRate:2000}",
            initialDelayString = "${kafka-poc.bookServiceGenerator.initialDelay:10000}")
    public void createBook() {
        bookService.createBook();
    }

}
