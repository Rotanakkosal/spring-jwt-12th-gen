package com.levi.springjwt.controller;

import com.levi.springjwt.model.dto.request.BookRequest;
import com.levi.springjwt.model.dto.response.BookDto;
import com.levi.springjwt.service.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getAllBook(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDto saveBook(@RequestBody BookRequest bookRequest){
        return bookService.saveBook(bookRequest);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest){
        return bookService.updateBookById(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.deleteBookById(id);
        return "Successfully Deleted book id" + id;
    }
}
