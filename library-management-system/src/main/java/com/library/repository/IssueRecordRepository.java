package com.library.repository;

import com.library.model.IssueRecord;
import com.library.model.IssueRecord.IssueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRecordRepository extends MongoRepository<IssueRecord, String> {

    Page<IssueRecord> findByMemberId(String memberId, Pageable pageable);
    Page<IssueRecord> findByBookId(String bookId, Pageable pageable);
    List<IssueRecord> findByMemberIdAndStatus(String memberId, IssueStatus status);

    // Check if a member has already issued a specific book and not returned it
    Optional<IssueRecord> findByMemberIdAndBookIdAndStatus(String memberId, String bookId, IssueStatus status);

    Page<IssueRecord> findAll(Pageable pageable);
}
