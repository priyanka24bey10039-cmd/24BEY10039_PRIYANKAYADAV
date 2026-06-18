package com.library.service;

import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorRequest request);
    AuthorResponse getAuthorById(String authorId);
    Page<AuthorResponse> getAllAuthors(Pageable pageable);
    AuthorResponse updateAuthor(String authorId, AuthorRequest request);
    void deleteAuthor(String authorId);
}
