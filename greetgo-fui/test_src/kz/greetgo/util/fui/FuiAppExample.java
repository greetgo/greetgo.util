package kz.greetgo.util.fui;

import kz.greetgo.util.fui.handler.IntChangeHandler;
import kz.greetgo.util.fui.handler.StrChangeHandler;

import java.nio.file.Paths;

public class FuiAppExample {
  public static void main(String[] args) {
    new FuiAppExample().execute();
  }

  private void execute() {
    var fui = new FUI(Paths.get("build/FuiAppExample"));

    fui.button("exit-2", fui::shutdown);

    var b1 = fui.button("hello-world/button-1");
    var b2 = fui.button("hello-world/button-2");
    var b3 = fui.button("hello-world/button-3");

    b1.attach(() -> System.out.println("tf0rEl5c6i :: b1 clicked"));
    b2.attach(() -> System.out.println("tf0rEl5c6i :: b2 clicked"));
    b3.attach(() -> System.out.println("tf0rEl5c6i :: b3 clicked"));

    {
      var i1   = fui.entryInt("int-fields/int-a", 10);
      var i2   = fui.entryInt("int-fields/int-b", 11);
      var i3   = fui.entryInt("int-fields/int-s", 12);
      var iSum = fui.entryInt("int-fields/int-sum", 13);

      IntChangeHandler handler = ignore -> iSum.set(i1.getInt() + i2.getInt() + i3.getInt());
      i1.attachChangeHandler(handler);
      i2.attachChangeHandler(handler);
      i3.attachChangeHandler(handler);
    }

    var appName = fui.entryStr("app-name", "Fui Application Example");

    fui.button("make-app-name-left", () -> appName.set("Left name"));

    fui.entryInt("ping-sleep", fui.getPingSleep(), fui::setPingSleep);

    System.out.println("t0tc2P6fpD :: Application started");

    var strValue = fui.entryStr("str-value", "Hi WORLD", str -> System.out.println("uuF00WsGl4 :: str = " + str));
    fui.entryBool("bool-value", true, bool -> System.out.println("s43Eon0W7d :: bool = " + bool));

    strValue.attach(str -> System.out.println("C5ayOOHyLc :: strValue = " + str));

    fui.go();

    System.out.println("6L0wtIgK7T :: Application down " + appName.get());
  }
}
