package com.library.service;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(String bookId);
    Page<BookResponse> getAllBooks(Pageable pageable);
    BookResponse updateBook(String bookId, BookRequest request);
    void deleteBook(String bookId);

    // Search & filter
    Page<BookResponse> searchByTitle(String title, Pageable pageable);
    Page<BookResponse> searchByCategory(String categoryId, Pageable pageable);
    Page<BookResponse> searchByAuthor(String authorId, Pageable pageable);
    Page<BookResponse> getAvailableBooks(Pageable pageable);
}
