package com.library.repository;

import com.library.model.LibraryCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LibraryCardRepository extends MongoRepository<LibraryCard, String> {
    Optional<LibraryCard> findByMemberId(String memberId);
    boolean existsByMemberId(String memberId);
}
