package kz.greetgo.homecreditdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Anim03Activity extends Activity {
  
  final static float SCALE = 0.8f;
  final static float ALFA = 0.65f;
  final static float DIST = -10f;
  
  private final List<View> views = new ArrayList<View>();
  
  private void addView(int viewId) {
    views.add(findViewById(viewId));
  }
  
  private View view(int index) {
    int i = current + index;
    while (i < 0) {
      i += views.size();
    }
    return views.get(i % views.size());
  }
  
  private RelativeLayout wall;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.anim03_activity);
    
    wall = (RelativeLayout)findViewById(R.id.wall03);
    
    wall.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return touch(event);
      }
    });
    
    wall.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
          int oldTop, int oldRight, int oldBottom) {
        dx = 0;
        resetPosition(true);
      }
    });
    
    addView(R.id.act03_lay1);
    addView(R.id.act03_lay2);
    addView(R.id.act03_lay3);
    addView(R.id.act03_lay4);
    addView(R.id.act03_lay5);
    
    setFontTo(R.id.button_change_password, R.id.buttonFinish, R.id.wallText);
    setBoldFontTo(R.id.act03_text1, R.id.act03_text2, R.id.act03_text3, R.id.act03_text4,
        R.id.act03_text5);
    
    findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      }
    });
    
    findViewById(R.id.button_change_password).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        changePassword();
      }
    });
    
    current = 0;
    dx = 0;
  }
  
  private void setFontTo(int... ids) {
    Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular-K_0.ttf");
    for (int id : ids) {
      View view = findViewById(id);
      if (view instanceof TextView) {
        TextView txt = (TextView)view;
        txt.setTypeface(tf);
      }
    }
  }
  
  private void setBoldFontTo(int... ids) {
    Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Bold.otf");
    for (int id : ids) {
      View view = findViewById(id);
      if (view instanceof TextView) {
        TextView txt = (TextView)view;
        txt.setTypeface(tf);
      }
    }
  }
  
  private void setCurrent(int current) {
    this.current = current;
    
    resetPosition(true);
  }
  
  private void updateVisibility() {
    for (View view : views) {
      view.setVisibility(View.INVISIBLE);
    }
    
    view(-2).setVisibility(View.VISIBLE);
    view(-1).setVisibility(View.VISIBLE);
    view(0).setVisibility(View.VISIBLE);
    view(+1).setVisibility(View.VISIBLE);
    view(+2).setVisibility(View.VISIBLE);
  }
  
  private int current;
  
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
  
  float x, dx;
  boolean down = false;
  
  private void down(MotionEvent event) {
    x = event.getX();
    down = true;
  }
  
  private void move(MotionEvent event) {
    if (!down) return;
    dx = event.getX() - x;
    
    resetPosition(false);
    
    view(0).getWidth();
    wall.getWidth();
  }
  
  private void resetPosition(boolean anim) {
    updateVisibility();
    
    float w = view(0).getWidth();
    float W = wall.getWidth();
    
    float xc[] = new float[5];
    
    xc[0] = -W / 2f - w / 2f;
    {
      float xx = -w - 2f * DIST - w;
      if (xc[0] > xx) xc[0] = xx;
    }
    xc[1] = -w - DIST;
    xc[2] = 0;
    xc[3] = +w + DIST;
    xc[4] = +W / 2f + w / 2f;
    {
      float xx = +w + 2f * DIST + w;
      if (xc[4] < xx) xc[4] = xx;
    }
    
    for (int i = -2; i <= +2; i++) {
      
      float scale;
      float alfa;
      if (i == 0) {
        scale = alfa = 1f;
      } else if (i == 1 || i == -1) {
        scale = SCALE + Math.abs(dx) / w * (1f - SCALE);
        alfa = ALFA + Math.abs(dx) / w * (1f - ALFA);
      } else {
        scale = SCALE;
        alfa = ALFA;
      }
      
      if (scale > 1f) scale = 1f;
      if (alfa > 1f) alfa = 1f;
      
      if (anim) {
        float oldTR = view(i).getTranslationX();
        float newTR = xc[i + 2] + dx;
        if (outOfScreen(newTR, W, w) && outOfScreen(oldTR, W, w)) {
          view(i).setVisibility(View.INVISIBLE);
        }
        {
          ViewPropertyAnimator animator = view(i).animate();
          animator.setInterpolator(new DecelerateInterpolator());
          animator.translationX(newTR).scaleX(scale).scaleY(scale).alpha(alfa).withLayer();
        }
      } else {
        view(i).setTranslationX(xc[i + 2] + dx);
        view(i).setScaleX(scale);
        view(i).setScaleY(scale);
        view(i).setAlpha(alfa);
      }
    }
    
  }
  
  private boolean outOfScreen(float translationX, float screenWidth, float itemWidth) {
    if (translationX > 0) {
      return translationX + 1 > screenWidth / 2f + itemWidth / 2f;
    }
    return translationX - 1 < -screenWidth / 2f - itemWidth / 2f;
  }
  
  private void up(MotionEvent event) {
    down = false;
    float dx2 = dx;
    dx = 0;
    
    if (Math.abs(dx2) < 5) {
      int i = current;
      while (i < 0) {
        i += views.size();
      }
      click(i % views.size());
      return;
    }
    
    setCurrent(current + (dx2 < 0 ? +1 :-1));
  }
  
  private void click(int index) {
    startActivity(new Intent(this, getFormClass(index)));
  }
  
  private Class<?> getFormClass(int index) {
    switch (index) {
    case 0:
      return Activity001.class;
    case 1:
      return Activity002.class;
    case 2:
      return Activity003.class;
    case 3:
      return Activity004.class;
    case 4:
      return Activity005.class;
    }
    throw new IllegalArgumentException("index = " + index);
  }
  
  private void changePassword() {
    Intent intent = new Intent(this, ChangePasswordActivity.class);
    startActivity(intent);
  }
}
