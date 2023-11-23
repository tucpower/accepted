package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateUserDTO;
import gr.accepted.stoiximan.model.dto.UpdateUserDTO;
import gr.accepted.stoiximan.model.entity.User;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public List<User> findAll();
    User findById(UUID id) throws ResourceNotFoundException;
    User createUser(CreateUserDTO createUserDTO);
    User updateById(UUID id, UpdateUserDTO updateUserDTO) throws ResourceNotFoundException;
    void deleteById(UUID id) throws ResourceNotFoundException;
}
