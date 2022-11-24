package com.infinnity.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.ZonedDateTime;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@Builder
public class Book implements KeyEntity {
    @EmbeddedId
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private BookId id;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    @Version
    @Column(name = "version")
    private long version;
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "year")
    private Integer year;

    @Override
    public String getKey() {
        return id.toString();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

}
