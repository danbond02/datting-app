package edu.kpi.iasa.mmsa.blockaccountservice.api;

import edu.kpi.iasa.mmsa.blockaccountservice.api.dto.BlockDto;
import edu.kpi.iasa.mmsa.blockaccountservice.api.dto.BlockedUserDto;
import edu.kpi.iasa.mmsa.blockaccountservice.service.BlockService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping(value="/block")
public class BlockController {

    @Autowired
    private final BlockService blockService;

    public BlockController(BlockService blockService) {this.blockService = blockService;}

    @GetMapping(value="/{id}")
    public ResponseEntity<ArrayList<BlockedUserDto>> getListOfBlockedAccountsById(@PathVariable Long id){
        try{
            ArrayList<BlockedUserDto> ListOfBlockedAccounts = blockService.getListOfBlockedAccountsById(id);
            return ResponseEntity.ok(ListOfBlockedAccounts);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/user/{id}")
    public ResponseEntity<JSONObject> getUserById(@PathVariable Long id){
        try{
            JSONObject str = blockService.getUser(id);
            return ResponseEntity.ok(str);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> blockUser(@RequestBody BlockDto blockDto){
        try {
            final Long id = blockService.postNewBlock(blockDto);
            final String location = String.format("/user/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> unblockUser(@RequestBody BlockDto blockDto){
        try {
            blockService.deleteBlock(blockDto);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

}
