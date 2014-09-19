package kz.greetgo.email;

import java.util.ArrayList;
import java.util.List;

public class Email {
  private String to;
  private String from;
  private String subject;
  private String body;
  private final List<String> copies = new ArrayList<String>();
  
  public String getTo() {
    return to;
  }
  
  public void setTo(String to) {
    this.to = to;
  }
  
  public List<String> getCopies() {
    return copies;
  }
  
  public String getFrom() {
    return from;
  }
  
  public void setFrom(String from) {
    this.from = from;
  }
  
  public String getSubject() {
    return subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String getBody() {
    return body;
  }
  
  public void setBody(String body) {
    this.body = body;
  }
  
  @Override
  public String toString() {
    return "Email [to=" + to + ", from=" + from + ", subject=" + subject + ", body=" + body
        + ", copies=" + copies + "]";
  }
  
}
