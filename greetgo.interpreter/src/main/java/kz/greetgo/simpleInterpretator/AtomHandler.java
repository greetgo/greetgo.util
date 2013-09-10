package kz.greetgo.simpleInterpretator;

public interface AtomHandler {
  void append(Atom atom);
  
  void complete();
}
