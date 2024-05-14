package com.infinnity.kafka.events;

import org.springframework.stereotype.Component;

@Component
public class TopicNameProvider {

    public static final String BOOK_TABLE_TOPIC = "table.book";

    public String bookTableTopic() {
        return BOOK_TABLE_TOPIC;
    }

}
