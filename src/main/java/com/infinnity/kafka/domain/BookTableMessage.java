package com.infinnity.kafka.domain;

import com.infinnity.kafka.events.TableMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookTableMessage implements TableMessage {

    private BookId id;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private long version;
    private boolean deleted;

    private String title;
    private String author;

    @Override
    public String getKey() {
        return id.toString();
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public ZonedDateTime getTimestamp() {
        return updatedAt;
    }
}
