package com.infinnity.kafka.events;

import com.infinnity.kafka.domain.Book;
import com.infinnity.kafka.domain.BookId;
import com.infinnity.kafka.domain.BookTableMessage;
import com.infinnity.kafka.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class TableBookMessageProducer {

    private final TopicNameProvider topicNameProvider;
    private final KafkaTemplate<String, BookTableMessage> kafkaTemplate;
    private final BookMapper bookMapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(BookEvent event) {
        publishBookTableMessage(event.book());
    }

    public void publishBookTableMessage(Book book) {
        var bookMessage = bookMapper.toMessage(book);
        log.debug("Before sending bookMessage: {} {}", bookMessage, bookMessage.isDeleted());
        kafkaTemplate.send(topicNameProvider.bookTableTopic(), bookMessage.getKey(), bookMessage);
    }
}
