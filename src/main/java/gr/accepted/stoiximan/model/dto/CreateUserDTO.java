package gr.accepted.stoiximan.model.dto;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
