package com.infinnity.kafka.events;

import com.infinnity.kafka.domain.Book;

public record BookAsyncEvent(Book book) {

}
