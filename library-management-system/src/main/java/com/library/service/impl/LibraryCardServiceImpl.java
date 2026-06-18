package com.library.service.impl;

import com.library.dto.request.LibraryCardRequest;
import com.library.dto.response.LibraryCardResponse;
import com.library.exception.DuplicateResourceException;
import com.library.exception.InvalidOperationException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.LibraryCard;
import com.library.model.Member;
import com.library.repository.LibraryCardRepository;
import com.library.repository.MemberRepository;
import com.library.service.LibraryCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryCardServiceImpl implements LibraryCardService {

    private final LibraryCardRepository cardRepository;
    private final MemberRepository memberRepository;

    @Override
    public LibraryCardResponse createCard(LibraryCardRequest request) {
        log.info("[LibraryCardService] Issuing card for member: {}", request.getMemberId());
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", request.getMemberId()));

        // One-to-One: each member can have only one library card
        if (cardRepository.existsByMemberId(request.getMemberId())) {
            throw new DuplicateResourceException("LibraryCard", "memberId", request.getMemberId());
        }
        if (request.getExpiryDate().isBefore(request.getIssueDate())) {
            throw new InvalidOperationException("Expiry date cannot be before issue date");
        }
        LibraryCard card = LibraryCard.builder()
                .issueDate(request.getIssueDate())
                .expiryDate(request.getExpiryDate())
                .memberId(member.getMemberId())
                .active(true)
                .build();
        LibraryCard saved = cardRepository.save(card);
        log.info("[LibraryCardService] Card created: {}", saved.getCardId());
        return toResponse(saved, member);
    }

    @Override
    public LibraryCardResponse getCardById(String cardId) {
        LibraryCard card = findById(cardId);
        Member member = memberRepository.findById(card.getMemberId()).orElse(null);
        return toResponse(card, member);
    }

    @Override
    public LibraryCardResponse getCardByMemberId(String memberId) {
        LibraryCard card = cardRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("LibraryCard", "memberId", memberId));
        Member member = memberRepository.findById(memberId).orElse(null);
        return toResponse(card, member);
    }

    @Override
    public LibraryCardResponse updateCard(String cardId, LibraryCardRequest request) {
        log.info("[LibraryCardService] Updating card: {}", cardId);
        LibraryCard card = findById(cardId);
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", request.getMemberId()));
        if (request.getExpiryDate().isBefore(request.getIssueDate())) {
            throw new InvalidOperationException("Expiry date cannot be before issue date");
        }
        card.setIssueDate(request.getIssueDate());
        card.setExpiryDate(request.getExpiryDate());
        card.setMemberId(request.getMemberId());
        return toResponse(cardRepository.save(card), member);
    }

    @Override
    public void deleteCard(String cardId) {
        log.info("[LibraryCardService] Deleting card: {}", cardId);
        cardRepository.delete(findById(cardId));
    }

    private LibraryCard findById(String id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LibraryCard", "id", id));
    }

    private LibraryCardResponse toResponse(LibraryCard card, Member member) {
        return LibraryCardResponse.builder()
                .cardId(card.getCardId())
                .issueDate(card.getIssueDate())
                .expiryDate(card.getExpiryDate())
                .memberId(card.getMemberId())
                .memberName(member != null ? member.getMemberName() : null)
                .active(card.isActive())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .build();
    }
}
