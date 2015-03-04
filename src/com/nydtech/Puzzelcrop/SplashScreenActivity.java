package com.nydtech.Puzzelcrop;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashScreenActivity extends Activity {
	private Thread splashThresd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.splash);
	    final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
	    splashImageView.setBackgroundResource(R.drawable.flag);
	    final AnimationDrawable frameAnimation = (AnimationDrawable)splashImageView.getBackground();
	    splashImageView.post(new Runnable(){
			@Override
			public void run() {
			frameAnimation.start();				
			}	    	
	    });	  
	    
        final SplashScreenActivity sPlashScreen = this;
        splashThresd = new Thread (){
        	@Override
    		public void run(){
    			try {
    				
    				synchronized(this){
    					wait(2000);
    				}
    			} 
    			catch(InterruptedException ex){    				
    			}
    			finish();
                Levelseet.onetime = false;
    			Intent intent = new Intent();
    			intent.setClass(sPlashScreen, PuzzleActivity.class);
    			startActivity(intent);				
    		}
    	};  	
    	splashThresd.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
    	if(evt.getAction() == MotionEvent.ACTION_DOWN)
    	{
    	}
    	return true;
    }	

}