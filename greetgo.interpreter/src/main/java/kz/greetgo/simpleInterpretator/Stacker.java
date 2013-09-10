package kz.greetgo.simpleInterpretator;

import java.util.ArrayList;

public class Stacker implements AtomHandler {
  private final ArrayList<HolderSequence> stack = new ArrayList<HolderSequence>();
  
  @Override
  public String toString() {
    return peekLast().toString();
  }
  
  public HolderSequence sequence() {
    return peekLast();
  }
  
  public Stacker() {
    stack.add(new HolderSequence());
  }
  
  @Override
  public void append(Atom atom) {
    if (atom.isOpenExpression()) {
      push();
      return;
    }
    if (atom.isCloseExpression()) {
      pop();
      return;
    }
    addAtom(atom);
  }
  
  private void pop() {
    if (pollLast() == null) {
      throw new InterpreterError(ErrorCode.TOO_MANY_CLOSE_SKOB, "Слишком много закрывающих скобок");
    }
  }
  
  private void push() {
    HolderSequence holder = new HolderSequence();
    peekLast().getHolders().add(holder);
    stack.add(holder);
  }
  
  private void addAtom(Atom atom) {
    peekLast().getHolders().add(new HolderAtom(atom));
  }
  
  @Override
  public void complete() {
    if (stack.size() > 1) {
      throw new InterpreterError(ErrorCode.TOO_MANY_OPEN_SKOB, "Слишком много открывающих скобок");
    }
    if (stack.size() == 0) {
      throw new InterpreterError(ErrorCode.TOO_MANY_CLOSE_SKOB, "Слишком много закрывающих скобок");
    }
  }
  
  private HolderSequence peekLast() {
    if (stack.size() == 0) return null;
    return stack.get(stack.size() - 1);
  }
  
  private HolderSequence pollLast() {
    HolderSequence last = peekLast();
    stack.remove(stack.size() - 1);
    return last;
  }
}
