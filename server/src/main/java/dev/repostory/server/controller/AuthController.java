package dev.repostory.server.controller;

import dev.repostory.server.repository.UserRepository;
import dev.repostory.server.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MailService mailService;
    private final UserRepository userRepository;

    @PostMapping("/email/{email}")
    public void local(@PathVariable("email") String email) {
        //유효한 이메일인가? - 프론트에서 검증

        //존재하는 이메일인가?
        if(userRepository.findByEmail(email).isPresent()){
            //존재하는 이메일이다 - 로그인 로직 진행
            mailService.sendLoginMail(email);
        }else {
            //존재하지 않는 이메일이다 - 회원가입 경로로 이동
            mailService.sendJoinMail(email);
        }

    }

}
