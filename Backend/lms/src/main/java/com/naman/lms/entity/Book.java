package com.naman.lms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@Table(name = "Books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String name;

    private String author;

    @Column(unique = true)
    private String isbn;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean available;


    
    @JsonIgnore
    private List<Integer> members;

    
    public Book(String name, Genre genre, String author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.available = true;
    }

    public Book(String name, Genre genre, String author, String isbn) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    


}
