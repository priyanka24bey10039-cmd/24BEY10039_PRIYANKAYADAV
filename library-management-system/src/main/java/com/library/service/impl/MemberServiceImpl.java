package com.library.service.impl;

import com.library.dto.request.MemberRequest;
import com.library.dto.response.MemberResponse;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Member;
import com.library.repository.MemberRepository;
import com.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponse createMember(MemberRequest request) {
        log.info("[MemberService] Creating member: {}", request.getEmail());
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Member", "email", request.getEmail());
        }
        Member member = Member.builder()
                .memberName(request.getMemberName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        Member saved = memberRepository.save(member);
        log.info("[MemberService] Member created: {}", saved.getMemberId());
        return toResponse(saved);
    }

    @Override
    public MemberResponse getMemberById(String memberId) {
        log.info("[MemberService] Fetching member: {}", memberId);
        return toResponse(findById(memberId));
    }

    @Override
    public Page<MemberResponse> getAllMembers(Pageable pageable) {
        log.info("[MemberService] Fetching all members");
        return memberRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public MemberResponse updateMember(String memberId, MemberRequest request) {
        log.info("[MemberService] Updating member: {}", memberId);
        Member member = findById(memberId);
        if (!member.getEmail().equalsIgnoreCase(request.getEmail())
                && memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Member", "email", request.getEmail());
        }
        member.setMemberName(request.getMemberName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        return toResponse(memberRepository.save(member));
    }

    @Override
    public void deleteMember(String memberId) {
        log.info("[MemberService] Deleting member: {}", memberId);
        memberRepository.delete(findById(memberId));
        log.info("[MemberService] Member deleted: {}", memberId);
    }

    private Member findById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }

    private MemberResponse toResponse(Member m) {
        return MemberResponse.builder()
                .memberId(m.getMemberId())
                .memberName(m.getMemberName())
                .email(m.getEmail())
                .phone(m.getPhone())
                .address(m.getAddress())
                .createdAt(m.getCreatedAt())
                .updatedAt(m.getUpdatedAt())
                .build();
    }
}
