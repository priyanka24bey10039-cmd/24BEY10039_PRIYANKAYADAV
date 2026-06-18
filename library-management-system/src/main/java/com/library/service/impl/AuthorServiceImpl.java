package com.library.service.impl;

import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Author;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponse createAuthor(AuthorRequest request) {
        log.info("[AuthorService] Creating author with email: {}", request.getEmail());
        if (authorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Author", "email", request.getEmail());
        }
        Author author = Author.builder()
                .authorName(request.getAuthorName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        Author saved = authorRepository.save(author);
        log.info("[AuthorService] Author created with ID: {}", saved.getAuthorId());
        return toResponse(saved);
    }

    @Override
    public AuthorResponse getAuthorById(String authorId) {
        log.info("[AuthorService] Fetching author by ID: {}", authorId);
        return toResponse(findById(authorId));
    }

    @Override
    public Page<AuthorResponse> getAllAuthors(Pageable pageable) {
        log.info("[AuthorService] Fetching all authors — page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return authorRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public AuthorResponse updateAuthor(String authorId, AuthorRequest request) {
        log.info("[AuthorService] Updating author ID: {}", authorId);
        Author author = findById(authorId);
        // Allow same author to keep their email; only block if another author has it
        if (!author.getEmail().equalsIgnoreCase(request.getEmail())
                && authorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Author", "email", request.getEmail());
        }
        author.setAuthorName(request.getAuthorName());
        author.setEmail(request.getEmail());
        author.setPhone(request.getPhone());
        Author updated = authorRepository.save(author);
        log.info("[AuthorService] Author updated: {}", authorId);
        return toResponse(updated);
    }

    @Override
    public void deleteAuthor(String authorId) {
        log.info("[AuthorService] Deleting author ID: {}", authorId);
        Author author = findById(authorId);
        authorRepository.delete(author);
        log.info("[AuthorService] Author deleted: {}", authorId);
    }

    private Author findById(String authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
    }

    private AuthorResponse toResponse(Author author) {
        return AuthorResponse.builder()
                .authorId(author.getAuthorId())
                .authorName(author.getAuthorName())
                .email(author.getEmail())
                .phone(author.getPhone())
                .createdAt(author.getCreatedAt())
                .updatedAt(author.getUpdatedAt())
                .build();
    }
}
