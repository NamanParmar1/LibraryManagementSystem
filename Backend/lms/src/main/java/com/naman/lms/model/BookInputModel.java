package com.naman.lms.model;

import com.naman.lms.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInputModel {

    private String name;
    private String author;
    private String isbn;
    private Genre genre;
    
}
