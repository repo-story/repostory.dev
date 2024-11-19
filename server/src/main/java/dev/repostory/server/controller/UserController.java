package dev.repostory.server.controller;

import dev.repostory.server.dto.UserDTO;
import dev.repostory.server.entity.User;
import dev.repostory.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String join(UserDTO userDTO) {

        //프론트에서 넘어온 유저정보에서 slug값이 중복인지 확인
        if(userService.isExistingSlug(userDTO.getSlug())){
            return "Slug already exists";
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .slug(userDTO.getSlug())
                .profileImg(userDTO.getProfileImg())
                .bio(userDTO.getBio())
                .build();
        userService.joinUser(user);
        return "회원가입 완료";
    }
}
