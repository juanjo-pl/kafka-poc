package com.infinnity.kafka.domain;

import com.infinnity.kafka.domain.id.UuidEntityId;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class BookId extends UuidEntityId {

    public BookId() {
    }

    public BookId(String uuid) {
        super(UUID.fromString(uuid));
    }

    private BookId(UUID id) {
        super(id);
    }

    public static BookId fromUUID(UUID uuid) {
        return new BookId(uuid);
    }

}
