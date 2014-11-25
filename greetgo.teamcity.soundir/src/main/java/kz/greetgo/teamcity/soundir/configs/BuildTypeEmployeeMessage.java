package kz.greetgo.teamcity.soundir.configs;

public class BuildTypeEmployeeMessage {
  public String buildType, employee, email, message;
  
  @Override
  public String toString() {
    return "BuildTypeEmployeeMessage [buildType=" + buildType + ", employee=" + employee
        + ", email=" + email + ", message=" + message + "]";
  }
  
}
