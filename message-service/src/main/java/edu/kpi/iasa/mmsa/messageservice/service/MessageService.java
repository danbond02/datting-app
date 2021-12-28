package edu.kpi.iasa.mmsa.messageservice.service;

import edu.kpi.iasa.mmsa.messageservice.api.dto.ChatDto;
import edu.kpi.iasa.mmsa.messageservice.api.dto.MessageDtoRequest;
import edu.kpi.iasa.mmsa.messageservice.api.dto.MessageDtoResponse;
import edu.kpi.iasa.mmsa.messageservice.repo.MessageRepo;
import edu.kpi.iasa.mmsa.messageservice.repo.model.Message;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) { this.messageRepo = messageRepo; }

    public Long createMessage(MessageDtoRequest messageDtoRequest) throws IllegalArgumentException{
        JSONObject userSenderJson = getUser(messageDtoRequest.getSenderId());
        JSONObject userReceiverJson = getUser(messageDtoRequest.getReceiverId());
            JSONObject[] listOfBlockedUsers = getListOfBlockedUsers(messageDtoRequest.getReceiverId());
            for (JSONObject listOfBlockedUser : listOfBlockedUsers) {
                if (Long.parseLong(listOfBlockedUser.get("id").toString()) == messageDtoRequest.getSenderId()) {
                    throw new IllegalArgumentException("user was blocked");
                }
            }
            Message newMessage = new Message(messageDtoRequest.getDate(), messageDtoRequest.getTime(),
                    messageDtoRequest.getSenderId(), messageDtoRequest.getReceiverId(), messageDtoRequest.getText());
            messageRepo.save(newMessage);
            return newMessage.getId();
    }

    public JSONObject getUser(Long id) throws IllegalArgumentException {
        RestTemplate newRestTemplate = new RestTemplate();
        try {
            String userService = "http://localhost:8080/user";
            ResponseEntity<JSONObject> userResponse = newRestTemplate.exchange(userService + "/" + id, HttpMethod.GET,
                    null, JSONObject.class);
            return userResponse.getBody();
        } catch (HttpClientErrorException e){
            throw new IllegalArgumentException("there is no such account");
        }
    }

    public JSONObject[] getListOfBlockedUsers(Long id) throws IllegalArgumentException{
        RestTemplate newRestTemplate = new RestTemplate();
        try {
            String blockedAccountService = "http://localhost:8081/block";
            ResponseEntity<JSONObject[]> blockedUsersResponse = newRestTemplate.exchange(blockedAccountService + "/" + id,
                    HttpMethod.GET, null, JSONObject[].class);
            return blockedUsersResponse.getBody();
        } catch (HttpClientErrorException e){
            throw new IllegalArgumentException();
    }
    }

    public MessageDtoResponse getMessageById(Long id) throws IllegalArgumentException{
        Optional<Message> message = messageRepo.findById(id);
        if (message.isPresent()){
            Message messageGet = message.get();
            JSONObject sender = getUser(messageGet.getSenderId());
            JSONObject receiver = getUser(messageGet.getReceiverId());
            return new MessageDtoResponse(messageGet.getDate(), messageGet.getTime(), sender.get("username").toString(),
                    receiver.get("username").toString(), messageGet.getText());
        } else throw new IllegalArgumentException("message not found");
    }

    public ArrayList<MessageDtoResponse> getAllMessagesByUsersId(ChatDto chatDto) {
        List<Message> messagesList = messageRepo.findBySenderIdAndReceiverIdOrderByDate(chatDto.getFirstParticipantId(),
                chatDto.getSecondParticipantId());
        if (!messagesList.isEmpty()){
            Message message = messagesList.get(0);
            JSONObject sender = getUser(message.getSenderId());
            JSONObject receiver = getUser(message.getReceiverId());
            ArrayList<MessageDtoResponse> chatHistory = new ArrayList<>();
            for (Message value : messagesList) {
                MessageDtoResponse messageDtoResponse = new MessageDtoResponse(value.getDate(),
                        value.getTime(), sender.get("username").toString(), receiver.get("username").toString(),
                        value.getText());
                chatHistory.add(messageDtoResponse);
            }
            return chatHistory;
        } else throw new IllegalArgumentException("there is no chat between this users");
    }

    public void updateMessageById(Long id, MessageDtoRequest messageDtoRequest) {
        Optional<Message> message = messageRepo.findById(id);
        if(message.isPresent()){
            Message oldMessage = message.get();
            updateMessage(oldMessage, messageDtoRequest);
            messageRepo.save(oldMessage);
        } else throw new IllegalArgumentException("message was not found");
    }

    private void updateMessage(Message oldMessage, MessageDtoRequest messageDtoRequest) {
        if(messageDtoRequest.getDate() != null){ oldMessage.setDate(messageDtoRequest.getDate()); }
        if(messageDtoRequest.getTime() != null){ oldMessage.setTime(messageDtoRequest.getTime()); }
        if(messageDtoRequest.getSenderId()!=null){
            JSONObject senderJson = getUser(messageDtoRequest.getSenderId());
            JSONObject[] listOfBlockedUsers = getListOfBlockedUsers(oldMessage.getReceiverId());
            for (JSONObject listOfBlockedUser : listOfBlockedUsers) {
                if (Long.parseLong(listOfBlockedUser.get("id").toString()) == messageDtoRequest.getSenderId()) {
                    throw new IllegalArgumentException("user was blocked");
                }
            }
            oldMessage.setSenderId((messageDtoRequest.getSenderId()));
        }
        if(messageDtoRequest.getReceiverId() != null) {
            JSONObject receiverJson = getUser(messageDtoRequest.getReceiverId());
            JSONObject[] listOfBlockedUsers = getListOfBlockedUsers(messageDtoRequest.getReceiverId());
            for (JSONObject listOfBlockedUser : listOfBlockedUsers) {
                if (Long.parseLong(listOfBlockedUser.get("id").toString()) == oldMessage.getSenderId()) {
                    throw new IllegalArgumentException("user was blocked");
                }
            }
            oldMessage.setReceiverId(messageDtoRequest.getReceiverId());
        }
        if(messageDtoRequest.getText() != null) {oldMessage.setText(messageDtoRequest.getText());}
    }

    public void deleteMessageById(Long id) { messageRepo.deleteById(id); }
}
