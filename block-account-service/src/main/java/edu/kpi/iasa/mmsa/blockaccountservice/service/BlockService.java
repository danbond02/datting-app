package edu.kpi.iasa.mmsa.blockaccountservice.service;

import edu.kpi.iasa.mmsa.blockaccountservice.api.dto.BlockDto;
import edu.kpi.iasa.mmsa.blockaccountservice.api.dto.BlockedUserDto;
import edu.kpi.iasa.mmsa.blockaccountservice.repo.BlockRepo;
import edu.kpi.iasa.mmsa.blockaccountservice.repo.model.Block;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockService {

    @Autowired
    private final BlockRepo blockRepo;
    private final String userService = "http://localhost:8080/user";

    public BlockService(BlockRepo blockRepo) { this.blockRepo = blockRepo; }

    public ArrayList<BlockedUserDto> getListOfBlockedAccountsById(Long id) throws IllegalArgumentException{
        List<Block> ListOfBlockedUserId = blockRepo.findByUserId(id);
        ArrayList<BlockedUserDto> BlockedUsersList = new ArrayList<BlockedUserDto>();
        if (!ListOfBlockedUserId.isEmpty()){
            for (Block block : ListOfBlockedUserId) {
                Long userId = block.getBlockedUserId();
                    JSONObject userJson = getUser(userId);
                    BlockedUserDto newBlockedUser = createBlockedUserDto(userJson);
                    BlockedUsersList.add(newBlockedUser);
            }
            return BlockedUsersList;
        } else throw new IllegalArgumentException("that account have no blocked users");
    }

    private BlockedUserDto createBlockedUserDto(JSONObject userJson) {
        return new BlockedUserDto(Long.parseLong(userJson.get("id").toString()), userJson.get("username").toString(),
                userJson.get("name").toString(), userJson.get("firstName").toString(),
                Integer.parseInt(userJson.get("age").toString()), userJson.get("city").toString(),
                userJson.get("country").toString(), userJson.get("sex").toString(),
                userJson.get("orientation").toString());
    }

    public JSONObject getUser(Long id) throws IllegalArgumentException {
        RestTemplate newRestTemplate = new RestTemplate();
        try {
            ResponseEntity<JSONObject> userResponse = newRestTemplate.exchange(userService + "/" + id, HttpMethod.GET,
                    null, JSONObject.class);
                return userResponse.getBody();
        } catch (HttpClientErrorException e){
            throw new IllegalArgumentException("there is no such account");
        }
    }

    public Long postNewBlock(BlockDto blockDto) throws IllegalArgumentException{
        JSONObject userJson = getUser(blockDto.getUserId());
        JSONObject blockedUserJson = getUser(blockDto.getBlockedUserId());
        Block newBlock = new Block(blockDto.getUserId(), blockDto.getBlockedUserId());
        blockRepo.save(newBlock);
        return newBlock.getBlockedUserId();
    }

    public void deleteBlock(BlockDto blockDto) {
        Block blockToDelete = blockRepo.findByUserIdAndBlockedUserId(blockDto.getUserId(), blockDto.getBlockedUserId());
        if(blockToDelete != null){
            blockRepo.delete(blockToDelete);
        } else throw new IllegalArgumentException();
    }
}
