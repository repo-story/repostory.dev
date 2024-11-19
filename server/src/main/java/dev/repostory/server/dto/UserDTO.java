package dev.repostory.server.dto;

import lombok.Getter;

@Getter
public class UserDTO {

    private String email;
    private String name;
    private String slug;
    private String profileImg;
    private String bio;
}
