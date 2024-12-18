package com.sbp.ollamaExemple.service;

import com.sbp.ollamaExemple.dto.ConversationDto;
import com.sbp.ollamaExemple.entity.ConversationEntity;
import com.sbp.ollamaExemple.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public void saveConversation(String userMessage, String aiResponse) {
        ConversationEntity conversation = new ConversationEntity();
        conversation.setUserMessage(userMessage);
        conversation.setAiResponse(aiResponse);
        conversation.setTimestamp(java.time.LocalDateTime.now());
        conversationRepository.save(conversation);
    }

    public List<ConversationEntity> getLastConversations(int limit) {
        return conversationRepository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "timestamp"))
        ).getContent();
    }

    public List<ConversationDto> getLastConversationsAsDto(int limit) {
        return getLastConversations(limit).stream()
                .map(ConversationDto::fromEntity)
                .collect(Collectors.toList());
    }
}
