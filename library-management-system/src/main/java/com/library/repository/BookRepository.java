package com.library.repository;

import com.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    // Search by title (case-insensitive partial match)
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Filter by category
    Page<Book> findByCategoryId(String categoryId, Pageable pageable);

    // Filter by author
    Page<Book> findByAuthorId(String authorId, Pageable pageable);

    // Available books only
    @Query("{ 'available_quantity': { $gt: 0 } }")
    Page<Book> findAvailableBooks(Pageable pageable);

    // Combined search: title + category
    Page<Book> findByTitleContainingIgnoreCaseAndCategoryId(String title, String categoryId, Pageable pageable);

    List<Book> findByCategoryId(String categoryId);
    List<Book> findByAuthorId(String authorId);
}
