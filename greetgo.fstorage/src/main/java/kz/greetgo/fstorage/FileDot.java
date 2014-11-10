package kz.greetgo.fstorage;

/**
 * 
 * Хранитель файла - содержит контент файла с именем
 * 
 * @author pompei
 * 
 */
public class FileDot {
  /**
   * Имя файла
   */
  public String filename;
  
  /**
   * Содержимое файла
   */
  public byte[] data;
  
  public FileDot() {}
  
  public FileDot(String filename, byte[] data) {
    this.filename = filename;
    this.data = data;
  }
}
