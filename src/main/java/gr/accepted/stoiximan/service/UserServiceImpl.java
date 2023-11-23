package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateUserDTO;
import gr.accepted.stoiximan.model.dto.UpdateUserDTO;
import gr.accepted.stoiximan.model.entity.User;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;
import gr.accepted.stoiximan.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public User createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        BeanUtils.copyProperties(createUserDTO, user);

        return userRepository.save(user);
    }

    public User updateById(UUID id, UpdateUserDTO updateUserDTO) throws ResourceNotFoundException {
        User existingUser = findById(id);
        if(updateUserDTO.getFirstName() != null) {
            existingUser.setFirstName(updateUserDTO.getFirstName());
        }
        if(updateUserDTO.getLastName() != null) {
            existingUser.setLastName(updateUserDTO.getLastName());
        }
        if(updateUserDTO.getEmail() != null) {
            existingUser.setEmail(updateUserDTO.getEmail());
        }
        if(updateUserDTO.getPassword() != null) {
            existingUser.setPassword(updateUserDTO.getPassword());
        }
        return userRepository.save(existingUser);
    }

    public void deleteById(UUID id) throws ResourceNotFoundException {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
