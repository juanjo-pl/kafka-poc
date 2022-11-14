package com.infinnity.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Book implements KeyEntity {

    @EmbeddedId
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private BookId id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    @Column(name = "version")
    private long version;
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;



    @Override
    public String getKey() {
        return id.toString();
    }

}
