package kz.greetgo.util.fui;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
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

  public FUI(Path baseDir) {
    this.baseDir = baseDir;
    closeFile    = baseDir.resolve("shutdown-application.btn");
  }

  private final PingableList pingableList = new PingableList();

  @SneakyThrows
  public void go() {
    closeFile.toFile().getParentFile().mkdirs();
    closeFile.toFile().createNewFile();

    while (closeFile.toFile().exists()) {

      pingableList.list().forEach(Pingable::ping);

      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        break;
      }

    }

  }

  @SneakyThrows
  public void button(String buttonName, ButtonClickHandler buttonClickHandler) {
    File file = baseDir.resolve(buttonName + ".btn").toFile();
    file.getParentFile().mkdirs();
    file.createNewFile();

    //noinspection Convert2Lambda
    pingableList.add(new Pingable() {
      @Override
      @SneakyThrows
      public void ping() {
        if (file.exists()) {
          return;
        }
        file.createNewFile();
        System.out.println("mvx3J4MZqK :: Button " + buttonName + " clicked...");
        buttonClickHandler.clicked();
      }
    });
  }

  @SneakyThrows
  public SliceButton sliceButton(String buttonName) {
    File file = baseDir.resolve(buttonName + ".btn").toFile();
    file.getParentFile().mkdirs();
    file.createNewFile();

    return new SliceButton() {
      final AtomicReference<Long> prevCheckExists = new AtomicReference<>(null);

      @Override
      @SneakyThrows
      public boolean isClicked() {
        Long prevCheck = prevCheckExists.get();
        long now       = System.currentTimeMillis();

        if (prevCheck != null) {
          long delta = now - prevCheck;
          if (delta <= 300) {
            return false;
          }
        }

        try {

          if (file.exists()) {
            return false;
          }
          file.getParentFile().mkdirs();
          file.createNewFile();
          return true;

        } finally {
          prevCheckExists.set(now);
        }
      }
    };
  }

  public StrAccessor entryStr(String entryName, String defaultValue, ChangeHandler changeHandler) {
    return entryStr0(entryName, ".entryStr", defaultValue, changeHandler);
  }

  public IntAccessor entryInt(String entryName, Integer defaultValue, ChangeHandler changeHandler) {
    var a = entryStr0(entryName, ".entryInt", defaultValue == null ? null : "" + defaultValue, changeHandler);
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
    };
  }

  public IntAccessor entryInt(String entryName, Integer defaultValue) {
    return entryInt(entryName, defaultValue, null);
  }

  @SneakyThrows
  private StrAccessor entryStr0(String entryName, String extension, String defaultValue, ChangeHandler changeHandler) {
    var entryFile = baseDir.resolve(entryName + extension);
    var fileSet   = entryFile.resolve("set").toFile();
    var fileValue = entryFile.resolve("value");

    fileSet.getParentFile().mkdirs();
    fileSet.createNewFile();

    var valueRef = new AtomicReference<String>();

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
        if (fileValue.toFile().exists()) {
          valueRef.set(Files.readString(fileValue, UTF_8));
        } else {
          valueRef.set(null);
        }

        System.out.println("rykT6zW363 :: Entry " + entryName + " = " + valueRef.get());

        if (changeHandler != null) {
          changeHandler.changed();
        }
      }
    });

    System.out.println("kZka13ZZl7 :: Entry " + entryName + " = " + trim(valueRef.get()));

    return ret;
  }

  private static String trim(String str) {
    return str == null ? null : str.trim();
  }

  public BoolAccessor entryBool(String entryName, boolean defaultValue, ChangeHandler changeHandler) {
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

    pingableList.add(() -> {
      var existsTrue  = Files.exists(fileTrue);
      var existsFalse = Files.exists(fileFalse);
      if (existsTrue || existsFalse) {
        return;
      }

      synchronized (sync) {
        boolean newValue = !valueRef.get();
        valueRef.set(newValue);
        write.accept(newValue);
      }

      System.out.println("uAy2Snl8Ya :: " + entryName + " changed to " + valueRef.get());
      changeHandler.changed();

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
        if (changeHandler != null) {
          changeHandler.changed();
        }
      }
    };
  }

}
