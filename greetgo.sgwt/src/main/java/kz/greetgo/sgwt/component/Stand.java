package kz.greetgo.sgwt.component;

import kz.greetgo.sgwt.base.ServiceFake;
import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;

public class Stand extends Snippet {
  public final VLayout root;
  protected final HLayout header;
  protected final HTMLFlow footer = new HTMLFlow();
  
  public Stand(Canvas content) {
    TextItem delays = new TextItem("DELAY", "Задержка");
    delays.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        ServiceFake.setDelays((String)event.getValue());
      }
    });
    header = hl(form(delays));
    header.setAutoHeight();
    root = vl(header, content, footer);
  }
  
  protected final Menu menu(String title, int width) {
    Menu menu = new Menu();
    IMenuButton button = new IMenuButton(title, menu);
    button.setWidth(width);
    header.addMember(button);
    return menu;
  }
  
  protected final void log(String message) {
    footer.setContents(footer.getContents() + "<br />" + message);
  }
  
  protected final void clearLog() {
    footer.setContents("");
  }
}