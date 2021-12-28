package edu.kpi.iasa.mmsa.userservice.service;

import edu.kpi.iasa.mmsa.userservice.api.dto.UserDto;
import edu.kpi.iasa.mmsa.userservice.exceptions.IllegalPhoneNumberException;
import edu.kpi.iasa.mmsa.userservice.exceptions.IllegalSexException;
import edu.kpi.iasa.mmsa.userservice.exceptions.NoUsersException;
import edu.kpi.iasa.mmsa.userservice.repo.UserRepo;
import edu.kpi.iasa.mmsa.userservice.repo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAll() {
        List<User> listUser = userRepo.findAll();
        if (!listUser.isEmpty()){
            return listUser;
        } else throw new NoUsersException();
    }

    public Long postNewUser(UserDto userDto) {
        if (userDto.getSex().equals("F") || userDto.getSex().equals("M")){
            if (userDto.getPhoneNumber().length() == 13 && userDto.getPhoneNumber().startsWith("+380")){
                User newUser = new User(userDto.getUsername(), userDto.getName(), userDto.getLastName(),
                        userDto.getFirstName(), userDto.getAge(), userDto.getHobby(), userDto.getOccupation(),
                        userDto.getCity(), userDto.getCountry(), userDto.getSex(), userDto.getOrientation(),
                        userDto.getReligion(), userDto.getEducation(), userDto.getPhoneNumber(), userDto.getEmail(),
                        userDto.getStatus());
                userRepo.save(newUser);
                return newUser.getId();
            } else throw new IllegalPhoneNumberException();
        } else throw new IllegalSexException();
    }

    public User getUserById(Long id) throws IllegalArgumentException{
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        else throw new IllegalArgumentException("user not found");
    }

    public void updateUserById(Long id, UserDto userDto) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()){
            User oldUser = user.get();
            userUpdate(oldUser, userDto);
            userRepo.save(oldUser);
        }
        else throw new IllegalArgumentException("user not found");
    }

    private void userUpdate(User oldUser, UserDto userDto) {
        if (userDto.getPhoneNumber() != null && userDto.getPhoneNumber().length() == 13
                && userDto.getPhoneNumber().startsWith("+380")){
            oldUser.setPhoneNumber(userDto.getPhoneNumber());
        }
        if(userDto.getAge() != null){ oldUser.setAge(userDto.getAge());}
        if(userDto.getCity() != null){ oldUser.setCity(userDto.getCity());}
        if(userDto.getCountry() != null){ oldUser.setCountry(userDto.getCountry());}
        if(userDto.getEducation() != null){ oldUser.setEducation(userDto.getEducation());}
        if(userDto.getSex() != null && (userDto.getSex().equals("F") || userDto.getSex().equals("M"))){
            oldUser.setSex(userDto.getSex());
        }
        if(userDto.getEmail() != null){ oldUser.setEmail(userDto.getEmail());}
        if(userDto.getFirstName() != null){ oldUser.setFirstName(userDto.getFirstName());}
        if (userDto.getHobby() != null){ oldUser.setHobby(userDto.getHobby());}
        if(userDto.getLastName() != null){ oldUser.setLastName(userDto.getLastName());}
        if(userDto.getName() != null){oldUser.setName(userDto.getName());}
        if(userDto.getOccupation() != null){oldUser.setOccupation(userDto.getOccupation());}
        if(userDto.getReligion() != null){oldUser.setReligion(userDto.getReligion());}
        if(userDto.getStatus() != null) {oldUser.setStatus(userDto.getStatus());}
        if(userDto.getOrientation() != null) {oldUser.setOrientation(userDto.getOrientation());}
        if(userDto.getUsername() != null) {oldUser.setUsername(userDto.getUsername());}
    }

    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }
}
