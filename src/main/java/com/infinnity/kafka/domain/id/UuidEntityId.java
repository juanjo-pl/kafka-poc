package com.infinnity.kafka.domain.id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode
@JsonSerialize(using = ToStringSerializer.class)
public abstract class UuidEntityId implements Serializable {

    @Column(name = "id", nullable = false, updatable = false)
    private final UUID value;

    public UuidEntityId() {
        this.value = UUID.randomUUID();
    }

    public UuidEntityId(UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}

