package edu.kpi.iasa.mmsa.messageservice.api;

import edu.kpi.iasa.mmsa.messageservice.api.dto.ChatDto;
import edu.kpi.iasa.mmsa.messageservice.api.dto.MessageDtoRequest;
import edu.kpi.iasa.mmsa.messageservice.api.dto.MessageDtoResponse;
import edu.kpi.iasa.mmsa.messageservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping(value="/message")
public class MessageController {

    @Autowired
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<MessageDtoResponse> getMessage(@PathVariable Long id){
        try{
            MessageDtoResponse messageDtoResponse = messageService.getMessageById(id);
            return ResponseEntity.ok(messageDtoResponse);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/chat")
    public ResponseEntity<ArrayList<MessageDtoResponse>> getChatHistory(@RequestBody ChatDto chatDto){
        try{
            ArrayList<MessageDtoResponse> messageDtoResponseList = messageService.getAllMessagesByUsersId(chatDto);
            return ResponseEntity.ok(messageDtoResponseList);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody MessageDtoRequest messageDtoRequest){
        try {
            final Long id = messageService.createMessage(messageDtoRequest);
            final String location = String.format("/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value="/{id}")
    public ResponseEntity<Void> updateMessage(@PathVariable Long id, @RequestBody MessageDtoRequest messageDtoRequest){
        try{
            messageService.updateMessageById(id, messageDtoRequest);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id){
        try {
            messageService.deleteMessageById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
