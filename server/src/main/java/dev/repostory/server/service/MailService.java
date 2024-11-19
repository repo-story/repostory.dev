package dev.repostory.server.service;

import dev.repostory.server.entity.UserMailCode;
import dev.repostory.server.exception.InternalServerErrorException;
import dev.repostory.server.repository.UserMailCodeRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;
    private final UserMailCodeRepository userMailCodeRepository;

    @Value("${spring.mail.username}")   //lombok.Value가 아닌 스프링 어노테이션 Value를 사용해야 한다
    private String senderMail;      //static 필드와 함께 사용할 수 없다 (null)

    @Value("${front.url}")
    private String frontUrl;

    /**
     * email 계정으로 회원가입 링크가 첨부된 메일을 발송합니다
     *
     * @param email 메일을 수신할 이메일 계정
     */
    public void sendJoinMail(String email) {
        sendMail(email, "classpath:/mail/join.html", "회원가입 안내");
    }

    /**
     * email 계정으로 로그인 링크가 첨부된 메일을 발송합니다
     *
     * @param email 메일을 수신할 이메일 계정
     */
    public void sendLoginMail(String email) {
        sendMail(email, "classpath:/mail/login.html", "로그인 안내");
    }

    /**
     * email 계정으로 로그인 링크가 첨부된 메일을 발송합니다
     *
     * @param targetEmail 메일을 수신할 이메일 계정
     */
    private void sendMail(String targetEmail, String templateLocationPath, String subject) {
        UserMailCode userMailCode = userMailCodeRepository.findByEmail(targetEmail)
                .map(before -> {
                    before.setId(null);
                    before.setExpiredAt(null);

                    return userMailCodeRepository.save(before);
                })
                .orElseGet(() -> userMailCodeRepository.save(new UserMailCode(targetEmail)));

        try {
            //Value 어노테이션으로 읽어온 값을 static 필드에 주입하여 null
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom(new InternetAddress(senderMail, "RepoStory", "UTF-8"));
            mimeMessage.setRecipients(Message.RecipientType.TO, targetEmail);
            mimeMessage.setSubject(subject);

            String body = (new String(resourceLoader
                    .getResource(templateLocationPath)
                    .getInputStream()
                    .readAllBytes()
            ))
                    .replaceAll("%HOME-PAGE%", frontUrl)
                    .replaceAll("%MAIL-CODE%", userMailCode.getId().toString());
            mimeMessage.setText(body, "UTF-8", "html");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("메일 전송에 실패했습니다", e);
        }
    }

}
