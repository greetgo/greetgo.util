package kz.greetgo.sgwt.component;

import kz.greetgo.sgwt.base.ServiceFake;
import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;

public class Stand extends Snippet {
  public final VLayout root = vl();
  protected final HLayout mainMenu = hl();
  protected final HTMLFlow logFlow = new HTMLFlow();
  
  public Stand(Canvas content) {
    TextItem delays = new TextItem("DELAY", "Задержка");
    DynamicForm form = form(delays);
    delays.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        ServiceFake.setDelays((String)event.getValue());
      }
    });
    mainMenu.setAutoHeight();
    members(mainMenu, form);
    members(root, mainMenu, content, logFlow);
  }
  
  protected final Menu menu(String title, int width) {
    Menu menu = new Menu();
    IMenuButton button = new IMenuButton(title, menu);
    button.setWidth(width);
    mainMenu.addMember(button);
    return menu;
  }
  
  protected final void log(String message) {
    logFlow.setContents(logFlow.getContents() + "<br />" + message);
  }
  
  protected final void clearLog(String message) {
    logFlow.setContents("");
  }
}