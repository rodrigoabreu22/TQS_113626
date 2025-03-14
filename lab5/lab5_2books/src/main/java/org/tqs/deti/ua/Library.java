package org.tqs.deti.ua;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> store;

    public Library() {
        this.store = new ArrayList<>();
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        for(Book book : store){
            if (book.getAuthor().equals(author)) {
                books.add(book);
            }
        }
        return books;
    }

    public void add(Book book) {
        store.add(book);
    }

    public List<Book> findBooks(LocalDate from, LocalDate to) {
        List<Book> books = new ArrayList<>();
        for(Book book : store){
            if (book.getPublished().isAfter(from) && book.getPublished().isBefore(to)) {
                books.add(book);
            }
        }
        return books;
    }
    public List<Book> findBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        for(Book book : store){
            if (book.getTitle().equals(title)) {
                books.add(book);
            }
        }
        return books;
    }
}
