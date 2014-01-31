package kz.greetgo.homecreditdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class KaruselActivity extends Activity {
  ImageView image1, image2, image3;
  Animation animationSlideInLeft, animationSlideOutRight;
  ImageView curSlidingImage;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_karusel);
    
    image1 = (ImageView)findViewById(R.id.karusel_image1);
    image2 = (ImageView)findViewById(R.id.karusel_image2);
    image3 = (ImageView)findViewById(R.id.karusel_image3);
    
    image2.setVisibility(View.INVISIBLE);
    image3.setVisibility(View.INVISIBLE);
    
    animationSlideInLeft = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
    animationSlideOutRight = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
    animationSlideInLeft.setDuration(1000);
    animationSlideOutRight.setDuration(1000);
    animationSlideInLeft.setAnimationListener(animationSlideInLeftListener);
    animationSlideOutRight.setAnimationListener(animationSlideOutRightListener);
    
    curSlidingImage = image1;
    image1.startAnimation(animationSlideInLeft);
    image1.setVisibility(View.VISIBLE);
  }
  
  @Override
  protected void onPause() {
    super.onPause();
    image1.clearAnimation();
    image2.clearAnimation();
    image3.clearAnimation();
  }
  
  AnimationListener animationSlideInLeftListener = new AnimationListener() {
    
    @Override
    public void onAnimationEnd(Animation animation) {
      if (curSlidingImage == image1) {
        image1.startAnimation(animationSlideOutRight);
      } else if (curSlidingImage == image2) {
        image2.startAnimation(animationSlideOutRight);
      } else if (curSlidingImage == image3) {
        image3.startAnimation(animationSlideOutRight);
      }
    }
    
    @Override
    public void onAnimationRepeat(Animation animation) {}
    
    @Override
    public void onAnimationStart(Animation animation) {}
  };
  
  AnimationListener animationSlideOutRightListener = new AnimationListener() {
    @Override
    public void onAnimationEnd(Animation animation) {
      if (curSlidingImage == image1) {
        curSlidingImage = image2;
        image2.startAnimation(animationSlideInLeft);
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.VISIBLE);
        image3.setVisibility(View.INVISIBLE);
      } else if (curSlidingImage == image2) {
        curSlidingImage = image3;
        image3.startAnimation(animationSlideInLeft);
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.VISIBLE);
      } else if (curSlidingImage == image3) {
        curSlidingImage = image1;
        image1.startAnimation(animationSlideInLeft);
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);
      }
    }
    
    @Override
    public void onAnimationRepeat(Animation animation) {}
    
    @Override
    public void onAnimationStart(Animation animation) {}
  };
}
