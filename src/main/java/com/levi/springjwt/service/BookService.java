package com.levi.springjwt.service;

import com.levi.springjwt.model.dto.request.BookRequest;
import com.levi.springjwt.model.dto.response.BookDto;
import com.levi.springjwt.model.entity.Book;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(Long id);
    BookDto saveBook(BookRequest bookRequest);
    BookDto updateBookById(Long id, BookRequest bookRequest);
    void deleteBookById(Long id);
}
