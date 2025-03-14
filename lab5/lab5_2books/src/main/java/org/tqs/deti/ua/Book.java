package org.tqs.deti.ua;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book {
    private LocalDate published;
    private String author;
    private String title;

    Book( String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublished() {
        return published;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", author: " + author + ", published: " + published;
    }
}
