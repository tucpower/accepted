package gr.accepted.stoiximan.service;

import gr.accepted.stoiximan.model.dto.CreateUserDTO;
import gr.accepted.stoiximan.model.dto.UpdateUserDTO;
import gr.accepted.stoiximan.model.entity.User;
import gr.accepted.stoiximan.model.exception.ResourceNotFoundException;
import gr.accepted.stoiximan.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String FIRST_NAME = "Nikos";
    private static final String LAST_NAME = "Giannopoulos";
    private static final String EMAIL = "nikos@test.com";
    private static final String PASSWORD = "password";

    private List<User> users;
    private User user1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        User user2 = User.builder()
                .id(UUID.randomUUID())
                .firstName("George")
                .lastName("Giakoumakis")
                .email("george@test.com")
                .password("password")
                .build();
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    void findAll_Success() {
        when(userRepository.findAll()).thenReturn(users);

        List<User> users = userService.findAll();

        assertThat(users).isNotNull();
        assertEquals(2, users.size());
    }

    @Test
    void findById_Success() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user1));

        User user = userService.findById(USER_ID);

        assertThat(user).isNotNull();
        assertEquals(USER_ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    void findById_NotFound() {
        ResourceNotFoundException thrown =
                Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.findById(USER_ID));
        assertNull(thrown.getMessage());
    }

    @Test
    void createUser_Success() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setFirstName(FIRST_NAME);
        createUserDTO.setLastName(LAST_NAME);
        createUserDTO.setEmail(EMAIL);
        createUserDTO.setPassword(PASSWORD);

        User createUser = new User();
        createUser.setFirstName(createUserDTO.getFirstName());
        createUser.setLastName(createUserDTO.getLastName());
        createUser.setEmail(createUserDTO.getEmail());
        createUser.setPassword(createUserDTO.getPassword());

        when(userRepository.save(createUser)).thenReturn(user1);

        User createdUser = userService.createUser(createUserDTO);

        assertEquals(FIRST_NAME, createdUser.getFirstName());
        assertEquals(LAST_NAME, createdUser.getLastName());
        assertEquals(EMAIL, createdUser.getEmail());
        assertEquals(PASSWORD, createdUser.getPassword());
    }

    @Test
    void updateById() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(FIRST_NAME);
        updateUserDTO.setLastName(LAST_NAME);
        updateUserDTO.setEmail(EMAIL);
        updateUserDTO.setPassword(PASSWORD);

        User updateUser = new User();
        updateUser.setId(USER_ID);
        updateUser.setFirstName(updateUserDTO.getFirstName());
        updateUser.setLastName(updateUserDTO.getLastName());
        updateUser.setEmail(updateUserDTO.getEmail());
        updateUser.setPassword(updateUserDTO.getPassword());

        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(updateUser)).thenReturn(user1);

        User updatedUser = userService.updateById(USER_ID, updateUserDTO);

        assertEquals(FIRST_NAME, updatedUser.getFirstName());
        assertEquals(LAST_NAME, updatedUser.getLastName());
        assertEquals(EMAIL, updatedUser.getEmail());
        assertEquals(PASSWORD, updatedUser.getPassword());
    }

    @Test
    void deleteById() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        doNothing().when(userRepository).deleteById(USER_ID);

        userService.deleteById(USER_ID);

        verify(userRepository, times(1)).existsById(USER_ID);
        verify(userRepository, times(1)).deleteById(USER_ID);
    }
}