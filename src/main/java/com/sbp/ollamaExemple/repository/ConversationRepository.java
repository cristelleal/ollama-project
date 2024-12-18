package com.sbp.ollamaExemple.repository;

import com.sbp.ollamaExemple.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
}