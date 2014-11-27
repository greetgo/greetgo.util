package kz.greetgo.teamcity.soundir.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import kz.greetgo.teamcity.soundir.configs.BuildTypeEmployeeMessage;
import kz.greetgo.teamcity.soundir.configs.FromDb;
import kz.greetgo.teamcity.soundir.email.BuildInfoToEmail;
import kz.greetgo.teamcity.soundir.email.Email;
import kz.greetgo.teamcity.soundir.email.EmailSender;
import kz.greetgo.teamcity.soundir.storage.Storage;
import kz.greetgo.teamcity.soundir.teamcity.Api;
import kz.greetgo.teamcity.soundir.teamcity.Teamcity;
import kz.greetgo.teamcity.soundir.teamcity.model.BuildInfo;
import kz.greetgo.teamcity.soundir.teamcity.model.BuildStatus;
import kz.greetgo.teamcity.soundir.teamcity.model.ProjectInfo;
import kz.greetgo.teamcity.soundir.teamcity.rhs.BuildStatusList;
import kz.greetgo.teamcity.soundir.teamcity.rhs.GetBuildInfo;
import kz.greetgo.teamcity.soundir.teamcity.rhs.GetProjectInfo;

public class SendLettersController {
  
  public Storage storage;
  
  public Getter<Map<String, List<BuildTypeEmployeeMessage>>> messageMap;
  
  public void checkSendLetters(String buildType) {
    List<BuildStatus> list = Api.get(new BuildStatusList(buildType));
    
    BuildStatus firstLastFailed = Teamcity.getFirstLastFailed(list);
    
    if (firstLastFailed == null) return;
    
    {
      Calendar cal = new GregorianCalendar();
      cal.add(Calendar.DAY_OF_YEAR, -2);
      
      BuildInfo buildInfo = Api.get(new GetBuildInfo(firstLastFailed.id));
      
      if (cal.after(buildInfo.finishDate)) return;
    }
    
    BuildInfo buildInfo = Api.get(new GetBuildInfo(list.get(0).id));
    
    if (buildInfo.projectId != null) {
      buildInfo.projectInfo = Api.get(new GetProjectInfo(buildInfo.projectId));
      String parentId = buildInfo.projectInfo.parentId;
      if (parentId != null) {
        ProjectInfo parentPI = Api.get(new GetProjectInfo(parentId));
        buildInfo.projectInfo.parent = parentPI;
      }
    }
    
    Date lastSendLetter = storage.forBuildType(buildType).getLastSendLetter();
    
    if (lastSendLetter != null) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(lastSendLetter);
      cal.add(Calendar.DAY_OF_YEAR, +1);
      if (cal.getTime().after(new Date())) return;
    }
    
    Email email = BuildInfoToEmail.convert(buildInfo);
    
    List<String> emailList = new ArrayList<>();
    emailList.add("ekolpakov@greet-go.com");
    emailList.add("obersenev@greet-go.com");
    
    emailList.addAll(FromDb.emailsForBuildType(buildType));
    
    for (String to : emailList) {
      email.to = to;
      //System.out.println("send to " + to);
      if ("a".equals("a")) EmailSender.send(email);
    }
    
    {
      storage.forBuildType(buildType).setLastSendLetter(new Date());
    }
    
  }
}
