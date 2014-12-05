package kz.greepto.gpen.editors.gpen.prop;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import kz.greepto.gpen.editors.gpen.action.Action;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;
import kz.greepto.gpen.util.HandlerList;

import org.testng.annotations.Test;

public class PropFactoryTest {
  
  public static class TestSceneWorker implements SceneWorker {
    public final HandlerList handlerList = new HandlerList();
    
    @Override
    public String takeId(Object object) {
      return "asd";
    }
    
    @Override
    public void sendAction(Action action) {
      System.out.println("Sended: " + action);
    }
    
    @Override
    public HandlerKiller addChangeHandler(Handler handler) {
      return handlerList.add(handler);
    }
    
  }
  
  public static class Asd {
    public int nomer = 890;
    
    public String getWorld() {
      return "JI";
    }
  }
  
  @Test
  public void parseObject() throws Exception {
    Asd asd = new Asd();
    
    SceneWorker sceneWorker = new TestSceneWorker();
    
    List<PropAccessor> list = PropFactory.parseObject(asd, sceneWorker);
    
    System.out.println("---------------------------------------------");
    for (PropAccessor pa : list) {
      System.out.println(pa);
    }
    System.out.println("---------------------------------------------");
    
    Object value = list.get(1).getValue();
    System.out.println("value = " + value);
    
    assertThat(1);
  }
}
