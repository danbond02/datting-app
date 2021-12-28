package edu.kpi.iasa.mmsa.userservice.api;

import edu.kpi.iasa.mmsa.userservice.api.dto.UserDto;
import edu.kpi.iasa.mmsa.userservice.repo.model.User;
import edu.kpi.iasa.mmsa.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody UserDto userDto){
        final Long id = userService.postNewUser(userDto);
        final String location = String.format("/user/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try{
            userService.updateUserById(id, userDto);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
