package com.sbp.ollamaExemple.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "conversation")
public class ConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String userMessage;

    @Column(length = 5000)
    private String aiResponse;

    @Column()
    private LocalDateTime timestamp = LocalDateTime.now();
}