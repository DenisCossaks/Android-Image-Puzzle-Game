package com.nydtech.Puzzelcrop;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class dragimage extends Activity {
	private boolean isDrawn = false;
   private playareaview playView;
   private Context mContext;
   private PuzzelcropActivity app;
   public boolean soundONOFF = true;
   public ArrayList<Bitmap> imageChunks;
   congratulationsDialog customizeDialog;
   private static String APPLICATION_ID = "4fce050384250b000c0003be";
   @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
   	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       // setContentView(R.layout.drawadd);
        mContext = getApplicationContext();
       // RevMobDevs.showFullscreenAd(this, APPLICATION_ID);
        app = (PuzzelcropActivity)getApplication();
        app.setActivity(this);
    //    playView.setActivity(this);
        customizeDialog = new congratulationsDialog(this,app);
        playView = new playareaview(this,app);
        imageChunks = getIntent().getParcelableArrayListExtra("image chunks");
        setTitleBar();
        soundONOFF = app.getSoundOnOFF();
        ////////////////////////////////////////////////////////////////////////////////////////
//		RelativeLayout layout = (RelativeLayout) findViewById(R.id.linearLayoutm);
//		AdView adView = new AdView(this, AdSize.BANNER, "a14fddab7960133");
//		layout.addView(adView);
//		AdRequest request = new AdRequest();
//		request.setTesting(true);
//		adView.loadAd(request);
//		//	if(!isDrawn) {
//				playView = (playareaview)findViewById(R.id.SurfaceView01);
//			//	mycanvas.startDrawImage();
//			//	isDrawn = true;
//			//}
		Vibrator vibrator = (Vibrator) getSystemService(Activity.VIBRATOR_SERVICE);
		playView.setVibrator(vibrator);
       setContentView(playView);
  ///   setContentView(R.layout.drawadd);

                }
                public boolean onPreparePanel(int id, View v, Menu menu)
                {
                        if(soundONOFF == false)
                                menu.getItem(0).setIcon(R.drawable.sound_off);
                        else
                                menu.getItem(0).setIcon(R.drawable.sound_on);
                       
                        return true;
                }
               
            public void setTitleBar()
            {
                        Display display = ((WindowManager)mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();  
                         
                        int width = display.getWidth();  
                        int height = display.getHeight();
                       
                       
                        if(width > height)
                        {
                                   requestWindowFeature(Window.FEATURE_NO_TITLE);      
                        }
       
            }
       
           public void setTimeText(String text)
           {
                   playView.setTimeText(text);
           }
           public String getTimeText()
           {
                   return app.getTimeText();
           }
           public void getNumberPositions(ArrayList <Integer> numPosList)
           {
                   app.getNumberPositions(numPosList);
           }
           public void updateNumberPositions(int index,int id)
           {
                   app.updateNumberPositions(index,id);
           }
           public void updateMovesCount(int count)
           {
                  app.updateMovesCount(count);
           }
           public int getMovesCount()
           {
                   return app.getMovesCount();
           }
           public void showSucessDialog()
           {
                   customizeDialog.showDialog();
           }
//           
//        public boolean onCreateOptionsMenu(Menu menu){
//
//                MenuInflater inflater = getMenuInflater();
//                inflater.inflate(R.menu.menu, menu);
//                return true;
//           }
//   
//        public boolean onOptionsItemSelected (MenuItem item){
//                switch (item.getItemId()) {
//               
//                case R.id.sound:
//                        if(soundONOFF == true)
//                        {
//                                soundONOFF = false;
//                                app.updateSoundOnOFF(soundONOFF);
//                                item.setIcon(R.drawable.sound_off);
//                        }
//                        else
//                        {
//                                soundONOFF = true;
//                                app.updateSoundOnOFF(soundONOFF);
//                                item.setIcon(R.drawable.sound_on);
//                        }
//                        playView.isSoundON = soundONOFF;
//                        break;
//
//                case R.id.scores:
//                        Intent intent = new Intent();
//                        intent.setClass(getApplicationContext(), scoresListview.class);
//                        startActivity(intent);
//                        break;                          
//               
//                case R.id.exit:
//                        app.saveSettings();
//                        System.exit(0);
//                        break;
//                }
//                return true;
//                }

         public void onBackPressed ()
         {
                 app.stopTimer();
                 this.finish();
         }

         @Override
   public void onDestroy() {
                super.onDestroy();
           //     PuzzelcropActivity.n
               
              //  playView.updateNumberPositions();
             //  playView.getMovesCount();
              //    playView.getTime();
              //  MainActivity.chunkedImages.clear();
             //   ImageAdapter.imageChunks.clear();
                app.updateMovesCount(0);
                app.stopTimer();
              //  playView.movesCount = 0;
                Levelseet.row = 4;
                Levelseet.totalcount = 16;
                app.updateSoundOnOFF(soundONOFF);
                System.gc();
                System.runFinalization();
                System.gc();
                this.finish();
   }
     	@Override
    	public boolean onKeyDown(int keyCode, KeyEvent event) {

    		if (keyCode == KeyEvent.KEYCODE_BACK) {

    			showDialog(1);
    			return true;
    		}

    		return false;
    	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case 1: {
			final AlertDialog alertbuilder = new AlertDialog.Builder(this)
					.create();
			// alertbuilder.setTitle(Splash.title);
			alertbuilder.setMessage("Are you sure you want to exit the app?");
			alertbuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
//								    app.stopTimer();
//					                
//					                Levelseet.row = 4;
//					                Levelseet.totalcount = 16;
//					                app.updateSoundOnOFF(soundONOFF);
//					                System.gc();
//					                System.runFinalization();
//					                System.gc();
				                 finish();
							} catch (final NullPointerException e) {
							}

						}
					});

			alertbuilder.setButton(DialogInterface.BUTTON_NEUTRAL, "No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});

			return alertbuilder;
		}
		}
		return super.onCreateDialog(id);
	}
}

