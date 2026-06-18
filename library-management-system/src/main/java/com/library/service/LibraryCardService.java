package com.library.service;

import com.library.dto.request.LibraryCardRequest;
import com.library.dto.response.LibraryCardResponse;

public interface LibraryCardService {
    LibraryCardResponse createCard(LibraryCardRequest request);
    LibraryCardResponse getCardById(String cardId);
    LibraryCardResponse getCardByMemberId(String memberId);
    LibraryCardResponse updateCard(String cardId, LibraryCardRequest request);
    void deleteCard(String cardId);
}
