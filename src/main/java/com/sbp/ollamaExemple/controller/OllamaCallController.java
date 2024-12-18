package com.sbp.ollamaExemple.controller;

import com.sbp.ollamaExemple.dto.ConversationDto;
import com.sbp.ollamaExemple.service.ConversationService;
import com.sbp.ollamaExemple.service.FileReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/model")
@RequiredArgsConstructor
public class OllamaCallController {

    private final FileReadingService fileReadingService;
    private final OllamaChatModel chatModel;
    private final ConversationService conversationService;

    @PostMapping(path = "/ia")
    public String askQuestion(@RequestParam String question) {
        String prompt = fileReadingService.readInternalFileAsString("prompts/promptIa.txt");

        List<ConversationDto> history = conversationService.getLastConversationsAsDto(10);

        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(prompt));

        for (ConversationDto conv : history) {
            messages.add(new UserMessage(conv.getUserMessage()));
            messages.add(new SystemMessage(conv.getAiResponse()));
        }

        messages.add(new UserMessage(question));

        Prompt promptToSend = new Prompt(messages);
        Flux<ChatResponse> chatResponses = chatModel.stream(promptToSend);
        String response = Objects.requireNonNull(chatResponses.collectList().block())
                .stream()
                .map(chatResponse -> chatResponse.getResult().getOutput().getContent())
                .collect(Collectors.joining(""));

        conversationService.saveConversation(question, response);

        return response;
    }

    @GetMapping("/conversations")
    public List<ConversationDto> getConversations(@RequestParam(defaultValue = "10") int limit) {
        return conversationService.getLastConversationsAsDto(limit);
    }
}
