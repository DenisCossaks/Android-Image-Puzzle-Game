package com.nydtech.Puzzelcrop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
public class Forwebbrouse extends Activity {
	
	WebView browser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.webbrous);
		browser=(WebView)findViewById(R.id.webkit);
		if(Levelseet.link1 == true){
		browser.loadUrl("https://play.google.com/store/apps/details?id="+Levelseet.packagelist);
		}
		if(Levelseet.link2 == true){
		browser.loadUrl("http://www.nydtech.com");
		}
		if(Levelseet.moreclick == true){
		browser.loadUrl("https://play.google.com/store/apps/developer?id=NYDT");
		}
	}
    public static void callMe(Context c)
    {
        c.startActivity(new Intent(c, Forwebbrouse.class));
    }
}
//
//public class Forwebbrouse extends Activity {
//	WebView browser;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		setContentView(R.layout.webbrous);
//		browser=(WebView)findViewById(R.id.webkit);
//	
//	
//		browser.loadUrl("http://www.nydtech.com");
//	
//	}
//    public static void callMe(Context c)
//    {
//        c.startActivity(new Intent(c, Forwebbrouse.class));
//    }
//}