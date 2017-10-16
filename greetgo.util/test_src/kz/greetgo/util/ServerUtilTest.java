package kz.greetgo.util;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.fest.assertions.api.Assertions.assertThat;

public class ServerUtilTest {

  @Test
  public void extractPackage_1() throws Exception {
    final String aPackage = ServerUtil.extractPackage("asd.dsa.Asd");
    assertThat(aPackage).isEqualTo("asd.dsa");
  }

  @Test
  public void extractPackage_2() throws Exception {
    final String aPackage = ServerUtil.extractPackage("Asd");
    assertThat(aPackage).isNull();
  }

  @Test
  public void resolveFile_1() throws Exception {
    final File file = ServerUtil.resolveFile("some/dir1", "asd1.dsa.Asd", null);
    assertThat(file.getPath()).isEqualTo("some/dir1/asd1/dsa/Asd.java");
  }

  @Test
  public void resolveFile_2() throws Exception {
    final File file = ServerUtil.resolveFile("some/dir", "asd.dsa.Asd", ".java");
    assertThat(file.getPath()).isEqualTo("some/dir/asd/dsa/Asd.java");
  }

  @Test
  public void extractName() throws Exception {
    final String name = ServerUtil.extractName("asd.dsa.Asd");
    assertThat(name).isEqualTo("Asd");
  }

  @Test
  public void extractName_noPackage() throws Exception {
    final String name = ServerUtil.extractName("Asd");
    assertThat(name).isEqualTo("Asd");
  }

  private static class TestClass implements Serializable {
    int intField;
    String strField;
  }

  @Test
  public void java_serialize_deserialize() throws Exception {
    TestClass original = new TestClass();
    original.intField = RND.plusInt(1_000_000);
    original.strField = RND.str(10);

    //
    //
    byte[] bytes = ServerUtil.javaSerialize(original);
    //
    //

    assertThat(bytes).isNotNull();

    //
    //
    TestClass actual = ServerUtil.javaDeserialize(bytes);
    //
    //

    assertThat(actual).isNotNull();
    assertThat(actual.intField).isEqualTo(original.intField);
    assertThat(actual.strField).isEqualTo(original.strField);
  }

  @Test
  public void trimLeft() throws Exception {
    assertThat(ServerUtil.trimLeft("   asd  ")).isEqualTo("asd  ");
  }

  @Test
  public void trimRight() throws Exception {
    assertThat(ServerUtil.trimRight("   asd     ")).isEqualTo("   asd");
  }

  @Test
  public void fnn_001() throws Exception {
    assertThat(ServerUtil.fnn(null, "asd", "dsa")).isEqualTo("asd");
  }

  @Test
  public void fnn_002() throws Exception {
    assertThat(ServerUtil.fnn(null, null, null)).isNull();
  }

  public static class ParentAsd {
    @Test
    public void asd() {
    }
  }

  public static class ChildAsd extends ParentAsd {
    public void asd() {
    }

    @SuppressWarnings("unused")
    public void noIt() {
    }

    @Test
    public void iHave() {
    }
  }

  @Test
  public void getAnnotation() throws Exception {
    {
      Method method = ChildAsd.class.getMethod("asd");

      Test test = ServerUtil.getAnnotation(method, Test.class);

      assertThat(test).isNotNull();
    }
    {
      Method method = ChildAsd.class.getMethod("noIt");

      Test test = ServerUtil.getAnnotation(method, Test.class);

      assertThat(test).isNull();
    }
    {
      Method method = ChildAsd.class.getMethod("iHave");

      Test test = ServerUtil.getAnnotation(method, Test.class);

      assertThat(test).isNotNull();
    }
  }

  @Test
  public void firstUpper() throws Exception {
    assertThat(ServerUtil.firstUpper("asd")).isEqualTo("Asd");
    assertThat(ServerUtil.firstUpper("привет Мир")).isEqualTo("Привет Мир");
  }

  @Test
  public void notNull() throws Exception {
    String str = RND.str(5);
    String str2 = ServerUtil.notNull(str);
    assertThat(str2).isSameAs(str);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void notNull_null() throws Exception {
    ServerUtil.notNull(null);
  }

  @Test
  public void dummyCheck() throws Exception {
    ServerUtil.dummyCheck(true);
    ServerUtil.dummyCheck(false);
  }

  @Test
  public void streamToByteArray() throws Exception {
    byte[] bytes = RND.byteArray(10);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

    //
    //
    byte[] bytes2 = ServerUtil.streamToByteArray(inputStream);
    //
    //

    assertThat(bytes2).isEqualTo(bytes);
  }

  @Test
  public void streamToStr() throws Exception {
    String str = RND.str(10);

    ByteArrayInputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));

    //
    //
    String str2 = ServerUtil.streamToStr(inputStream);
    //
    //

    assertThat(str2).isEqualTo(str);

  }

  @Test
  public void copyStreamsAndCloseIn() throws Exception {

    byte[] bytes = RND.byteArray(1000);

    final boolean inputClosed[] = new boolean[]{false};
    final boolean outputClosed[] = new boolean[]{false};

    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes) {
      @Override
      public void close() throws IOException {
        inputClosed[0] = true;
        super.close();
      }
    };

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream() {
      @Override
      public void close() throws IOException {
        outputClosed[0] = true;
        super.close();
      }
    };


    //
    //
    OutputStream ret = ServerUtil.copyStreamsAndCloseIn(inputStream, outputStream);
    //
    //

    assertThat(ret).isSameAs(outputStream);

    assertThat(inputClosed[0]).isTrue();
    assertThat(outputClosed[0]).isFalse();
    assertThat(outputStream.toByteArray()).isEqualTo(bytes);
  }

  @Test
  public void justOne() throws Exception {
    ServerUtil.justOne(1);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void justOne_no() throws Exception {
    ServerUtil.justOne(2);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void justOne_Zero() throws Exception {
    ServerUtil.justOne(0);
  }

  @Test
  public void deleteRecursively() throws Exception {
    File doNotDelete = new File("build/doNotDelete/wow/hello/f");
    File files[] = new File[]{
      new File("build/deleteRecursively/wow/hello/f1"),
      new File("build/deleteRecursively/wow1/hello/f2"),
      new File("build/deleteRecursively/wow1/hello4/f3"),
      new File("build/deleteRecursively/wow1/hello4/f4"),
      new File("build/deleteRecursively/wow/hello/f5"),
      new File("build/deleteRecursively/wow/hello/f5"),


      new File("build/deleteRecursively2/wow/hello/f1"),
      new File("build/deleteRecursively2/wow1/hello/f2"),
      new File("build/deleteRecursively2/wow1/hello4/f3"),
      new File("build/deleteRecursively2/wow1/hello4/f4"),
      new File("build/deleteRecursively2/wow/hello/f5"),
      new File("build/deleteRecursively2/wow/hello/f5"),
    };


    for (File file : files) {
      file.getParentFile().mkdirs();
      file.createNewFile();
    }

    doNotDelete.getParentFile().mkdirs();
    doNotDelete.createNewFile();

    for (File file : files) {
      assertThat(file).exists();
    }

    //
    //
    ServerUtil.deleteRecursively("build/deleteRecursively");
    ServerUtil.deleteRecursively("build/deleteRecursively2");
    //
    //

    for (File file : files) {
      assertThat(file).doesNotExist();
    }

    assertThat(new File("build/deleteRecursively")).doesNotExist();
    assertThat(doNotDelete).exists();
  }

  @Test
  public void trim() throws Exception {
    assertThat(ServerUtil.trim("   asd   ")).isEqualTo("asd");
    assertThat(ServerUtil.trim(null)).isNull();
  }

  @Test
  public void trimLeft_null() throws Exception {
    assertThat(ServerUtil.trimLeft(null)).isNull();
  }

  @Test
  public void trimRight_null() throws Exception {
    assertThat(ServerUtil.trimRight(null)).isNull();
  }

  @Test
  public void bytesToHex() throws Exception {
    byte[] bytes = new byte[]{0x2E, (byte) -0x71, 0x1E};

    //
    //
    String s = ServerUtil.bytesToHex(bytes);
    //
    //

    assertThat(s).isEqualTo("2E8F1E");
  }
}
