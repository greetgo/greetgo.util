package kz.greetgo.teamcity.soundir.email;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailSender {
  
  public static void send(final Email email) {
    
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    
    {
      javaMailSender.setHost("smtp.gmail.com");
      javaMailSender.setPort(25);
      javaMailSender.setUsername("greetgoemailtester@gmail.com");
      javaMailSender.setPassword("RosaiPot199");
      Properties pp = new Properties();
      pp.setProperty("mail.transport.protocol", "smtp");
      pp.setProperty("mail.smtp.auth", "true");
      pp.setProperty("mail.smtp.ssl.enable", "true");
      pp.setProperty("mail.smtp.starttls.enable", "true");
      pp.setProperty("mail.debug", "true");
      javaMailSender.setJavaMailProperties(pp);
    }
    
    javaMailSender.send(new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom(email.from != null ? email.from :"greetgoemailtester@gmail.com");
        message.setTo(email.to);
        message.setSubject(email.subject);
        message.setText(email.body, true);
      }
    });
  }
}
