package kz.greetgo.homecreditdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Anim02Activity extends Activity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.anim02_activity);
    
    View view = findViewById(R.id.wall);
    
    view.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return touch(event);
      }
    });
    
    image = (ImageView)findViewById(R.id.image);
  }
  
  private ImageView image;
  
  private boolean touch(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      down();
      return true;
    }
    return true;
  }
  
  private void down() {
    
    image.animate().translationX(100).withLayer();
    
  }
}
