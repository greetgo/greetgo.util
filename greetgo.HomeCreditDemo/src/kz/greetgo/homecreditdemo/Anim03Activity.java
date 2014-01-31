package kz.greetgo.homecreditdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Anim03Activity extends Activity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.anim03_activity);
    
    View view = findViewById(R.id.wall03);
    
    view.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return touch(event);
      }
    });
    
    viewCenter = (ImageView)findViewById(R.id.act03_image1);
    viewLeft = (ImageView)findViewById(R.id.act03_image2);
    viewRight = (ImageView)findViewById(R.id.act03_image3);
    viewBack = (ImageView)findViewById(R.id.act03_image4);
  }
  
  private View viewCenter;
  @SuppressWarnings("unused")
  private View viewLeft;
  @SuppressWarnings("unused")
  private View viewRight;
  @SuppressWarnings("unused")
  private View viewBack;
  
  private boolean touch(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      down(event);
      return true;
    }
    if (event.getAction() == MotionEvent.ACTION_MOVE) {
      move(event);
      return true;
    }
    if (event.getAction() == MotionEvent.ACTION_UP) {
      up(event);
      return true;
    }
    return true;
  }
  
  float x, y;
  boolean down = false;
  
  private void down(MotionEvent event) {
    x = event.getX();
    y = event.getY();
    down = true;
  }
  
  private void move(MotionEvent event) {
    if (!down) return;
    float dx = event.getX() - x;
    float dy = event.getY() - y;
    
    viewCenter.setTranslationX(dx);
    viewCenter.setTranslationY(dy);
  }
  
  private void up(MotionEvent event) {
    down = false;
    
    viewCenter.animate().translationX(0).translationY(0).withLayer();
  }
}
