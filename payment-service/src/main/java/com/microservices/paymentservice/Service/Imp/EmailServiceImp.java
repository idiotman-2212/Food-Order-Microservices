package com.microservices.paymentservice.Service.Imp;

import com.microservices.paymentservice.Payload.Response.EmailDetails;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface EmailServiceImp {
    Mono<String> sendSimpleMail(EmailDetails details);
    Mono<String> sendMailWithAttachment(EmailDetails details);
    Mono<String> sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);
}
