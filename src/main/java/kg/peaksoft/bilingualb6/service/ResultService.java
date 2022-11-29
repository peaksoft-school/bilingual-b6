package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.response.ResultResponses;
import kg.peaksoft.bilingualb6.entites.Client;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.ClientRepository;
import kg.peaksoft.bilingualb6.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final ClientRepository clientRepository;
    private final JavaMailSender javaMailSender;
    public List<ResultResponses> getAll(String email) {
        Client client = clientRepository.getClientByAuthInfoEmail(email);
        return resultRepository.findAll(client.getId());
    }

    public String sendMail(String email) throws MessagingException {
        Client client = clientRepository.findClientByAuthInfoEmailOptional(email).
                orElseThrow(() -> new NotFoundException("Почтa не найдена"));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        messageHelper.setSubject("[bilingual-b6] send result");
        messageHelper.setFrom("bilingualbatch6@gmail.com");
        messageHelper.setTo(email);
        messageHelper.setText("Result: ",true);
        javaMailSender.send(mimeMessage);
        return "Email send";
    }
}