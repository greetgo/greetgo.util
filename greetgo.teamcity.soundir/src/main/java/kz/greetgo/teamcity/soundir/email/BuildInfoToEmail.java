package kz.greetgo.teamcity.soundir.email;

import java.text.SimpleDateFormat;

import kz.greetgo.teamcity.soundir.teamcity.model.BuildInfo;
import kz.greetgo.teamcity.soundir.teamcity.model.Change;

public class BuildInfoToEmail {
  public static Email convert(BuildInfo bi) {
    Email ret = new Email();
    
    String projectName = bi.getProjectName("&lt;Не указано&gt;");
    
    ret.subject = "Не исправленные тесты более двух дней. " + projectName
        + ", последний коммит от: " + bi.getLastUsername("&lt;Не указано&gt;");
    
    StringBuilder html = new StringBuilder();
    
    html.append("<h2 style='color:red'>Не исправленные тесты более двух дней. ")
        .append(projectName).append("</h2>\n");
    
    html.append("<p>\n");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    html.append("Дата падения тестов: ").append(sdf.format(bi.finishDate)).append("\n");
    
    html.append("</p><p>\n");
    
    html.append("Сборка: ").append(bi.buildTypeName + " (" + bi.buildType + ")").append("<br />\n");
    html.append("Номер сборки: ").append(bi.number).append("\n");
    
    html.append("</p><p>\n");
    
    html.append("Статус: <a href='").append(bi.webLink).append("'>")//
        .append(bi.statusText).append("</a>\n");
    
    html.append("</p><p>Список последних изменений<ul>\n");
    
    for (Change c : bi.lastChanges) {
      html.append("<li><a href='").append(c.webLink).append("'>");
      html.append(c.version).append(' ').append(c.username).append(' ')
          .append(sdf.format(c.happenedAt));
      html.append("</a></li>\n");
    }
    
    html.append("</ul></p>\n");
    
    ret.body = html.toString();
    
    return ret;
  }
}
