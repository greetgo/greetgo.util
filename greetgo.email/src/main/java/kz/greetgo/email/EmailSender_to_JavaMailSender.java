package kz.greetgo.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailSender_to_JavaMailSender implements EmailSender {
  
  private JavaMailSender javaMailSender;
  
  public EmailSender_to_JavaMailSender(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }
  
  @Override
  public void send(final Email email) {
    javaMailSender.send(new MimeMessagePreparator() {
      @Override
      public void prepare(javax.mail.internet.MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom(email.getFrom());
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody(), true);
      }
    });
  }
  
}
