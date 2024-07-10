package com.tumafare.com.tumafare.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.*;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendConfirmationEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject) throws MessagingException {
        String templateName;
        if (emailTemplate == null){
            templateName = "confirm-email";
        }else {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        send(to, subject, properties, helper, templateName, mimeMessage);
    }

    public void sendForgotPasswordEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String changePasswordUrl,
            String subject) throws MessagingException {
        String templateName;

        if (emailTemplate == null){
            templateName = "change-password";
        }else {
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("changePasswordUrl", changePasswordUrl);

        send(to, subject, properties, helper, templateName, mimeMessage);
    }

    private void send(String to, String subject, Map<String, Object> properties, MimeMessageHelper helper, String templateName, MimeMessage mimeMessage) throws MessagingException {
        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("info@tumafare.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
    }
}
