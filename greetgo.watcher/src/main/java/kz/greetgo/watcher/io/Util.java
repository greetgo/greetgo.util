package kz.greetgo.watcher.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Util {
  
  public static final Iterable<File> fileSequence(final File dir, final String prefix,
      final String suffix, final int minNum, final int maxNum) {
    final String numFormat = "%0" + Integer.toString(maxNum).length() + "d";
    return new Iterable<File>() {
      @Override
      public Iterator<File> iterator() {
        return new Iterator<File>() {
          private int num = minNum;
          
          @Override
          public boolean hasNext() {
            return num <= maxNum;
          }
          
          @Override
          public File next() {
            if (num > maxNum) {
              throw new NoSuchElementException();
            }
            return new File(dir, prefix + String.format(numFormat, num++) + suffix);
          }
          
          @Override
          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }
  
  public static final void rotate(Iterable<File> it) {
    List<File> list = new ArrayList<>();
    boolean lastExists = true;
    for (File file : it) {
      list.add(file);
      if (!file.exists()) {
        lastExists = false;
        break;
      }
    }
    if (lastExists) {
      list.get(list.size() - 1).delete();
    }
    for (int i = list.size() - 1; i > 0; i--) {
      list.get(i - 1).renameTo(list.get(i));
    }
  }
}
