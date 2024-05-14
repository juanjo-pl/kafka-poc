package com.infinnity.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@Builder
public class BookResponse {

    private BookId id;
    private String title;
    private String author;
    private Integer year;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private long version;

}
