package com.library.service;

import com.library.dto.request.IssueRecordRequest;
import com.library.dto.response.IssueRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueRecordService {
    IssueRecordResponse issueBook(IssueRecordRequest request);
    IssueRecordResponse returnBook(String issueId);
    IssueRecordResponse getIssueById(String issueId);
    Page<IssueRecordResponse> getAllIssueHistory(Pageable pageable);
    Page<IssueRecordResponse> getIssuesByMember(String memberId, Pageable pageable);
    Page<IssueRecordResponse> getIssuesByBook(String bookId, Pageable pageable);
}
