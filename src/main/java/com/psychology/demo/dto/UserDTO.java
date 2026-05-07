package com.psychology.demo.dto;

import com.psychology.demo.enumm.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String username;
    private List<String> role;
}
