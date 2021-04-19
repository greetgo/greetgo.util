package kz.greetgo.util.fui;

import kz.greetgo.util.fui.handler.BoolChangeHandler;
import kz.greetgo.util.fui.handler.BoolChangeHandlerList;
import kz.greetgo.util.fui.handler.ButtonClickHandler;
import kz.greetgo.util.fui.handler.ButtonClickHandlerList;
import kz.greetgo.util.fui.handler.HandlerAttaching;
import kz.greetgo.util.fui.handler.HandlerDetaching;
import kz.greetgo.util.fui.handler.IntChangeHandler;
import kz.greetgo.util.fui.handler.IntChangeHandlerList;
import kz.greetgo.util.fui.handler.StrChangeHandler;
import kz.greetgo.util.fui.handler.StrChangeHandlerList;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * File User Interface
 */
@SuppressWarnings("BusyWait")
public class FUI {
  private final Path baseDir;
  private final Path closeFile;

  private final AtomicInteger pingSleep    = new AtomicInteger(500);
  private final AtomicBoolean appIsWorking = new AtomicBoolean(true);

  public int getPingSleep() {
    return pingSleep.get();
  }

  public void setPingSleep(int pingSleep) {
    this.pingSleep.set(pingSleep);
  }

  public FUI(Path baseDir) {
    this.baseDir = baseDir;
    closeFile    = baseDir.resolve("shutdown-application.btn");
  }

  private final PingableList pingableList = new PingableList();

  @SneakyThrows
  public void go() {
    closeFile.toFile().getParentFile().mkdirs();
    closeFile.toFile().createNewFile();

    while (appIsWorking.get() && closeFile.toFile().exists()) {

      pingableList.list().forEach(Pingable::ping);

      try {
        Thread.sleep(pingSleep.get());
      } catch (InterruptedException e) {
        break;
      }

    }

  }

  public void shutdown() {
    appIsWorking.set(false);
  }

  @SneakyThrows
  public HandlerAttaching<ButtonClickHandler> button(String buttonName, ButtonClickHandler buttonClickHandler) {
    File file = baseDir.resolve(buttonName + ".btn").toFile();
    file.getParentFile().mkdirs();
    file.createNewFile();

    final var handlerList = new ButtonClickHandlerList();
    handlerList.attach(buttonClickHandler);

    //noinspection Convert2Lambda
    pingableList.add(new Pingable() {
      @Override
      @SneakyThrows
      public void ping() {
        if (file.exists()) {
          return;
        }
        file.getParentFile().mkdirs();
        file.createNewFile();
        System.out.println("mvx3J4MZqK :: Button " + buttonName + " clicked...");
        handlerList.fire();
      }
    });

    return handlerList;
  }

  @SneakyThrows
  public HandlerAttaching<ButtonClickHandler> button(String buttonName) {
    return button(buttonName, null);
  }

  public StrAccessor entryStr(String entryName, String defaultValue, StrChangeHandler changeHandler) {
    return entryStr0(entryName, ".entryStr", defaultValue, changeHandler);
  }

  public StrAccessor entryStr(String entryName, String defaultValue) {
    return entryStr0(entryName, ".entryStr", defaultValue, null);
  }

  public IntAccessor entryInt(String entryName, Integer defaultValue, IntChangeHandler changeHandler) {
    var handlerList = new IntChangeHandlerList();
    handlerList.attach(changeHandler);

    var a = entryStr0(entryName, ".entryInt",
                      defaultValue == null ? null : "" + defaultValue,
                      handlerList.strChangeHandler);

    return new IntAccessor() {
      @Override
      public Integer get() {
        String str = a.get();
        return str == null ? null : Integer.valueOf(str.trim());
      }

      @Override
      public int getInt() {
        var x = get();
        return x == null ? 0 : x;
      }

      @Override
      public void set(Integer value) {
        a.set(value == null ? null : "" + value);
      }

      @Override
      public HandlerDetaching attachChangeHandler(IntChangeHandler handler) {
        return handlerList.attach(handler);
      }
    };
  }

  public IntAccessor entryInt(String entryName, Integer defaultValue) {
    return entryInt(entryName, defaultValue, null);
  }

  @SneakyThrows
  private StrAccessor entryStr0(String entryName, String extension, String defaultValue,
                                StrChangeHandler changeHandler) {
    var entryFile = baseDir.resolve(entryName + extension);
    var fileSet   = entryFile.resolve("set").toFile();
    var fileValue = entryFile.resolve("value");

    fileSet.getParentFile().mkdirs();
    fileSet.createNewFile();

    var valueRef = new AtomicReference<String>();

    var handlerList = new StrChangeHandlerList();
    handlerList.attach(changeHandler);

    StrAccessor ret = new StrAccessor() {
      @Override
      @SneakyThrows
      public String get() {
        return valueRef.get();
      }

      @Override
      @SneakyThrows
      public void set(String value) {
        valueRef.set(value);
        if (value == null) {
          Files.delete(fileValue);
          return;
        }
        fileValue.toFile().getParentFile().mkdirs();
        Files.writeString(fileValue, value, UTF_8);
      }

      @Override
      public HandlerDetaching attach(StrChangeHandler strChangeHandler) {
        return handlerList.attach(strChangeHandler);
      }
    };

    if (fileValue.toFile().exists()) {
      valueRef.set(Files.readString(fileValue));
    } else {
      ret.set(defaultValue);
    }

    //noinspection Convert2Lambda
    pingableList.add(new Pingable() {
      @Override
      @SneakyThrows
      public void ping() {
        if (fileSet.exists()) {
          return;
        }
        fileSet.getParentFile().mkdirs();
        fileSet.createNewFile();

        String newValue;

        if (fileValue.toFile().exists()) {
          newValue = Files.readString(fileValue, UTF_8);
        } else {
          newValue = null;
        }

        valueRef.set(newValue);

        System.out.println("rykT6zW363 :: Entry " + entryName + " = " + newValue);

        handlerList.fire(newValue);
      }
    });

    System.out.println("kZka13ZZl7 :: Entry " + entryName + " = " + trim(valueRef.get()));

    return ret;
  }

  private static String trim(String str) {
    return str == null ? null : str.trim();
  }

  public BoolAccessor entryBool(String entryName, boolean defaultValue, BoolChangeHandler changeHandler) {
    var fileTrue  = baseDir.resolve(entryName + "-true");
    var fileFalse = baseDir.resolve(entryName + "-false");

    AtomicBoolean valueRef = new AtomicBoolean();

    //noinspection Convert2Lambda
    Consumer<Boolean> write = new Consumer<>() {
      @Override
      @SneakyThrows
      public void accept(Boolean value) {
        boolean flag = value == null ? false : value;
        File    toDel;
        File    toCreate;
        if (flag) {
          toCreate = fileTrue.toFile();
          toDel    = fileFalse.toFile();
        } else {
          toDel    = fileTrue.toFile();
          toCreate = fileFalse.toFile();
        }

        toDel.delete();
        toCreate.getParentFile().mkdirs();
        toCreate.createNewFile();
      }
    };

    BooleanSupplier read = () -> {
      var existsTrue  = Files.exists(fileTrue);
      var existsFalse = Files.exists(fileFalse);
      if (existsTrue && existsFalse || !existsTrue && !existsFalse) {
        write.accept(defaultValue);
        return defaultValue;
      }
      return existsTrue;
    };

    valueRef.set(read.getAsBoolean());
    System.out.println("uAy2Snl8Ya :: " + entryName + " started with " + valueRef.get());

    Object sync = new Object();

    var handlerList = new BoolChangeHandlerList();
    handlerList.attach(changeHandler);

    pingableList.add(() -> {
      var existsTrue  = Files.exists(fileTrue);
      var existsFalse = Files.exists(fileFalse);
      if (existsTrue || existsFalse) {
        return;
      }

      boolean newValue;

      synchronized (sync) {
        newValue = !valueRef.get();
        valueRef.set(newValue);
        write.accept(newValue);
      }

      System.out.println("uAy2Snl8Ya :: " + entryName + " changed to " + valueRef.get());
      handlerList.fire(newValue);

    });

    return new BoolAccessor() {
      @Override
      public boolean is() {
        return valueRef.get();
      }

      @Override
      @SneakyThrows
      public void set(boolean flag) {
        synchronized (sync) {
          boolean oldFlag = valueRef.getAndSet(flag);
          if (oldFlag == flag) {
            return;
          }
          write.accept(flag);
        }
      }

      @Override
      public HandlerDetaching attachChangeHandler(BoolChangeHandler handler) {
        return handlerList.attach(handler);
      }
    };
  }

}
