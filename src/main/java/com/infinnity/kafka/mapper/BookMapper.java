package com.infinnity.kafka.mapper;

import com.infinnity.kafka.domain.Book;
import com.infinnity.kafka.domain.BookResponse;
import com.infinnity.kafka.domain.BookTableMessage;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookTableMessage toMessage(Book book) {
        return BookTableMessage.builder()
                .id(book.getId())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .version(book.getVersion())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .build();
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .version(book.getVersion())
                .build();
    }
}
