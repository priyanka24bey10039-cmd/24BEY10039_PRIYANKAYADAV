package com.library.repository;

import com.library.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByEmail(String email);
    boolean existsByEmail(String email);
}
