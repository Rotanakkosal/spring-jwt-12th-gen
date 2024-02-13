package com.levi.springjwt.repository;

import com.levi.springjwt.model.dto.request.BookRequest;
import com.levi.springjwt.model.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookRepository {

    @Select("""
    SELECT * FROM books
    """)
    List<Book> findAll();

    @Select("""
    SELECT * FROM books
    WHERE id = #{id}
    """)
    Book fineOne(Long id);

    @Select("""
    INSERT INTO books (title, year)
    VALUES (#{book.title}, #{book.year})
    RETURNING *
    """)
    Book save(@Param("book") BookRequest bookRequest);

    @Update("""
    UPDATE books
    SET title = #{book.title}, year = #{book.year}
    WHERE id = #{id}
    """)
    void update(Long id,@Param("book") BookRequest bookRequest);

    @Delete("""
    DELETE FROM books
    WHERE id = #{id}
    """)
    void delete(Long id);

}
