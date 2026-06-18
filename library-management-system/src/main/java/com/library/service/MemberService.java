package com.library.service;

import com.library.dto.request.MemberRequest;
import com.library.dto.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberResponse createMember(MemberRequest request);
    MemberResponse getMemberById(String memberId);
    Page<MemberResponse> getAllMembers(Pageable pageable);
    MemberResponse updateMember(String memberId, MemberRequest request);
    void deleteMember(String memberId);
}
