package com.library.service.impl;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Category;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BookResponse createBook(BookRequest request) {
        log.info("[BookService] Creating book with ISBN: {}", request.getIsbn());
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Book", "ISBN", request.getIsbn());
        }
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", request.getAuthorId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Book book = Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .publicationYear(request.getPublicationYear())
                .quantity(request.getQuantity())
                .availableQuantity(request.getQuantity()) // initially all copies are available
                .authorId(author.getAuthorId())
                .categoryId(category.getCategoryId())
                .build();
        Book saved = bookRepository.save(book);
        log.info("[BookService] Book created: {}", saved.getBookId());
        return toResponse(saved, author, category);
    }

    @Override
    public BookResponse getBookById(String bookId) {
        log.info("[BookService] Fetching book: {}", bookId);
        Book book = findById(bookId);
        Author author = authorRepository.findById(book.getAuthorId()).orElse(null);
        Category category = categoryRepository.findById(book.getCategoryId()).orElse(null);
        return toResponse(book, author, category);
    }

    @Override
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        log.info("[BookService] Fetching all books — page: {}", pageable.getPageNumber());
        return bookRepository.findAll(pageable).map(this::toResponseWithLookup);
    }

    @Override
    public BookResponse updateBook(String bookId, BookRequest request) {
        log.info("[BookService] Updating book: {}", bookId);
        Book book = findById(bookId);
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Book", "ISBN", request.getIsbn());
        }
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", request.getAuthorId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        // Adjust available quantity proportionally when total quantity changes
        int diff = request.getQuantity() - book.getQuantity();
        int newAvailable = Math.max(0, book.getAvailableQuantity() + diff);

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setQuantity(request.getQuantity());
        book.setAvailableQuantity(newAvailable);
        book.setAuthorId(author.getAuthorId());
        book.setCategoryId(category.getCategoryId());

        return toResponse(bookRepository.save(book), author, category);
    }

    @Override
    public void deleteBook(String bookId) {
        log.info("[BookService] Deleting book: {}", bookId);
        bookRepository.delete(findById(bookId));
        log.info("[BookService] Book deleted: {}", bookId);
    }

    @Override
    public Page<BookResponse> searchByTitle(String title, Pageable pageable) {
        log.info("[BookService] Searching books by title: '{}'", title);
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable).map(this::toResponseWithLookup);
    }

    @Override
    public Page<BookResponse> searchByCategory(String categoryId, Pageable pageable) {
        log.info("[BookService] Searching books by category: {}", categoryId);
        // Verify category exists
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return bookRepository.findByCategoryId(categoryId, pageable).map(this::toResponseWithLookup);
    }

    @Override
    public Page<BookResponse> searchByAuthor(String authorId, Pageable pageable) {
        log.info("[BookService] Searching books by author: {}", authorId);
        authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        return bookRepository.findByAuthorId(authorId, pageable).map(this::toResponseWithLookup);
    }

    @Override
    public Page<BookResponse> getAvailableBooks(Pageable pageable) {
        log.info("[BookService] Fetching available books");
        return bookRepository.findAvailableBooks(pageable).map(this::toResponseWithLookup);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    private BookResponse toResponseWithLookup(Book book) {
        Author author = authorRepository.findById(book.getAuthorId()).orElse(null);
        Category category = categoryRepository.findById(book.getCategoryId()).orElse(null);
        return toResponse(book, author, category);
    }

    private BookResponse toResponse(Book book, Author author, Category category) {
        return BookResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .quantity(book.getQuantity())
                .availableQuantity(book.getAvailableQuantity())
                .authorId(book.getAuthorId())
                .authorName(author != null ? author.getAuthorName() : null)
                .categoryId(book.getCategoryId())
                .categoryName(category != null ? category.getCategoryName() : null)
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}
