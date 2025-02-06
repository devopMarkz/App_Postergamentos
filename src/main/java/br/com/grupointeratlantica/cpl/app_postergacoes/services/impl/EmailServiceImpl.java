package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.EmailService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.RelatorioExcelService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RelatorioExcelService relatorioExcelService;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    @Override
    @Async
    public void enviarEmailComNotas(List<NotaPostergada> notasPostergadas, List<String> emailsCorporativos) {
        String[] destinatarios = new String[emailsCorporativos.size()];

        for (int i = 0; i < destinatarios.length; i++) {
            destinatarios[i] = emailsCorporativos.get(i);
        }

        String assunto = "Relatório de notas postergadas - " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String corpo = "Segue anexo o relatório de notas postergadas.";

        try {
            File relatorio = relatorioExcelService.gerarRelatorioNotas(notasPostergadas);

            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);
            helper.setTo(destinatarios);
            helper.setSubject(assunto);
            helper.setText(corpo);

            helper.addAttachment(LocalDate.now() + " Relatorio_Notas.xlsx", new FileSystemResource(relatorio));

            javaMailSender.send(mensagem);
        } catch (MessagingException e){
            throw new EmailException("Erro no envio de e-mail.");
        }
    }

}
