package com.infinnity.kafka.events;

import com.infinnity.kafka.domain.Book;
import com.infinnity.kafka.domain.BookTableMessage;
import com.infinnity.kafka.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class TableBookAsyncMessageProducer {

    private final TopicNameProvider topicNameProvider;
    private final KafkaTemplate<String, BookTableMessage> kafkaTemplate;
    private final BookMapper bookMapper;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(BookAsyncEvent event) {
        publishBookTableMessage(event.book());
    }

    public void publishBookTableMessage(Book book) {
        var topic = topicNameProvider.bookTableTopic();
        var bookMessage = bookMapper.toMessage(book);
        log.info("[{}] Before sending bookMessage: {} deleted={}", topic, bookMessage, bookMessage.isDeleted());
        try {
            log.info("[{}] Waiting 3 seconds.", topic);
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            log.warn("Interrupted while waiting to send message.", e);
            Thread.currentThread().interrupt();
        }
        kafkaTemplate.send(topic, bookMessage.getKey(), bookMessage);
        log.info("[{}] Sent bookMessage:  bookId={}, deleted={}", topic, bookMessage.getId(), bookMessage.isDeleted());
    }

}
