package kz.pompei.dbmodel.asd.asd;

import kz.pompei.dbmodelstru.asd.asd.FieldsSmsTemplate;
import java.util.Objects;
import java.util.Arrays;

public class SmsTemplate extends FieldsSmsTemplate {
  public long smsTemplate;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { smsTemplate, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SmsTemplate other = (SmsTemplate)obj;
    return Objects.equals(smsTemplate, other.smsTemplate);
  }
  
  public SmsTemplate() {}
  
  public SmsTemplate(long smsTemplate) {
    this.smsTemplate = smsTemplate;
  }
}
