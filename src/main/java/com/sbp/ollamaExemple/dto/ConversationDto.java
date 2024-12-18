package com.sbp.ollamaExemple.dto;
import com.sbp.ollamaExemple.entity.ConversationEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ConversationDto {

    private Long id;
    private String userMessage;
    private String aiResponse;
    private LocalDateTime timestamp;

    public ConversationDto(Long id, String userMessage, String aiResponse, LocalDateTime timestamp) {
        this.id = id;
        this.userMessage = userMessage;
        this.aiResponse = aiResponse;
        this.timestamp = timestamp;
    }

    public static ConversationDto fromEntity(ConversationEntity entity) {
        return new ConversationDto(
                entity.getId(),
                entity.getUserMessage(),
                entity.getAiResponse(),
                entity.getTimestamp()
        );
    }
}