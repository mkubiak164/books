package pl.jstk.repository;


import java.util.List;

import pl.jstk.entity.BookEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("select book from BookEntity book where upper(book.title) like concat(upper(:title), '%')")
    List<BookEntity> findBookByTitle(@Param("title") String title);

    @Query("select book from BookEntity book where upper(book.authors) like concat('%', upper(:author), '%')")
    List<BookEntity> findBookByAuthor(@Param("author") String author);

    @Query("select book from BookEntity book where upper(book.authors) like concat('%', upper(:author), '%') and upper(book.title) like concat(upper(:title), '%')")
    List<BookEntity> findBookByAuthorAndTitle(@Param("author") String author, @Param("title") String title);
    
    @Query("select book from BookEntity book")
    List<BookEntity> findAllBooks();
    
//    @Query("insert into BookEntity book")
//    @Query(value = "insert into BookEntity (title,authors) VALUES (:title,:authors), nativeQuery = true")
//    BookEntity addBook(BookEntity bookEntity);
}


