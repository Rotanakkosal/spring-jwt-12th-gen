package com.levi.springjwt.service;

import com.levi.springjwt.model.dto.request.BookRequest;
import com.levi.springjwt.model.dto.response.BookDto;
import com.levi.springjwt.model.entity.Book;
import com.levi.springjwt.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> mapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.fineOne(id);
        return mapper.map(book, BookDto.class);
    }

    @Override
    public BookDto saveBook(BookRequest bookRequest) {
        Book book = bookRepository.save(bookRequest);
        return mapper.map(book, BookDto.class);
    }

    @Override
    public BookDto updateBookById(Long id, BookRequest bookRequest) {
        bookRepository.update(id,bookRequest);
        return getBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.delete(id);
    }
}
