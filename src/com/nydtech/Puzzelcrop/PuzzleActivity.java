package com.nydtech.Puzzelcrop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class PuzzleActivity extends Activity {
	private View startButton;
	RelativeLayout relativeLayoutaa;
	private View LogoButton;
	private ImageView Optionbutton;

	TransitionDrawable transition;
	private MediaPlayer mplayer;
	private Context apps;
	private ImageView startimage;
	private TextView text;
	private ImageView icon;
	private boolean netconnect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.manupage);
		Levelseet.playcount = Levelseet.playcount + 1;

		startButton = findViewById(R.id.playbutton);
		startButton.setOnClickListener(playButtonListener);
		LogoButton = findViewById(R.id.logobutton);
		LogoButton.setOnClickListener(logobuttonlistener);
		Optionbutton = (ImageView) findViewById(R.id.optionbutton);
		startimage = (ImageView) findViewById(R.id.startimage);
		text = (TextView) findViewById(R.id.text);
		icon = (ImageView) findViewById(R.id.icon);
		startimage.setOnClickListener(removeListener);
		icon.setOnClickListener(anotherlistenner);
		// mplayer = MediaPlayer.create(this, R.raw.button);

		Optionbutton.setAlpha(200);
		Optionbutton.setAdjustViewBounds(true);
		Optionbutton.setOnClickListener(optionbuttonlistener);
		netconnect = true;
		apps = this.getBaseContext();
		// if(isNetworkAvailable()){
		// if(Levelseet.onetime == false){
		// /////// Log.e("network", "network");
		// downloadTwitterIcon();
		 Levelseet.onetime = true;
		// }
		// else{
		// netconnect = true;
		// }
		// }
		// else{
		// netconnect = true;
		// }

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return (activeNetworkInfo != null);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.gc();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// mplayer.release();
		this.finish();
		System.gc();
	}

	private View.OnClickListener playButtonListener = new View.OnClickListener() {

		public void onClick(View v) {

			if (netconnect == true) {
				Intent intent = new Intent(PuzzleActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	};

	private View.OnClickListener optionbuttonlistener = new View.OnClickListener() {

		public void onClick(View v) {

			if (netconnect == true) {
				Levelseet.link1 = false;
				Levelseet.link2 = false;
				Levelseet.moreclick = true;
				Forwebbrouse.callMe(PuzzleActivity.this);
			}
		}
	};
	private View.OnClickListener removeListener = new View.OnClickListener() {

		public void onClick(View v) {

			// if (Levelseet.sound == true) {
			// mplayer.start();
			// }
			startButton.setVisibility(ImageView.VISIBLE);
			LogoButton.setVisibility(ImageView.VISIBLE);
			Optionbutton.setVisibility(ImageView.VISIBLE);
			relativeLayoutaa.setVisibility(RelativeLayout.INVISIBLE);
			startimage.setVisibility(ImageView.INVISIBLE);
			icon.setVisibility(ImageView.INVISIBLE);
			text.setVisibility(TextView.INVISIBLE);
			// advertisement();
		}
	};

	private View.OnClickListener anotherlistenner = new View.OnClickListener() {

		public void onClick(View v) {
			Levelseet.link2 = false;
			Levelseet.moreclick = false;
			Levelseet.link1 = true;
			// if (Levelseet.sound == true) {
			// mplayer.start();
			// }
			startButton.setVisibility(ImageView.VISIBLE);
			LogoButton.setVisibility(ImageView.VISIBLE);
			Optionbutton.setVisibility(ImageView.VISIBLE);
			relativeLayoutaa.setVisibility(RelativeLayout.INVISIBLE);
			startimage.setVisibility(ImageView.INVISIBLE);
			icon.setVisibility(ImageView.INVISIBLE);
			text.setVisibility(TextView.INVISIBLE);

			Forwebbrouse.callMe(PuzzleActivity.this);

		}
	};

	private View.OnClickListener logobuttonlistener = new View.OnClickListener() {
		public void onClick(View v) {
			if (netconnect == true) {
				Levelseet.link1 = false;
				Levelseet.moreclick = false;
				Levelseet.link2 = true;
				Forwebbrouse.callMe(PuzzleActivity.this);
			}
		}
	};

	public void downloadTwitterIcon() {
		Handler handler = new Handler() {
			public void handleMessage(Message message) {
				switch (message.what) {
				case HttpConnection.DID_START: {

					// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					Resources res = getResources();
					Drawable drawable = res
							.getDrawable(R.drawable.addbackground);
					drawable.setAlpha(150);
					relativeLayoutaa = (RelativeLayout) findViewById(R.id.adddview);
					relativeLayoutaa.setBackgroundDrawable(drawable);
					text.setText("Please Wait ....");

					break;
				}
				case HttpConnection.DID_SUCCEED: {

					startimage.setImageResource(R.drawable.cr);
					Bitmap response = (Bitmap) message.obj;
					icon.setImageBitmap(response);

					netconnect = true;

					// start.setVisibility(TextView.VISIBLE);
					startButton.setVisibility(ImageView.INVISIBLE);
					LogoButton.setVisibility(ImageView.INVISIBLE);
					Optionbutton.setVisibility(ImageView.INVISIBLE);
					text.setVisibility(TextView.INVISIBLE);
					relativeLayoutaa.setVisibility(RelativeLayout.VISIBLE);
					startimage.setVisibility(ImageView.VISIBLE);
					icon.setVisibility(ImageView.VISIBLE);
					// text.setVisibility(TextView.VISIBLE);
					// ///Log.e("Thissss id the icommm", "dddddd Connection");
					break;
				}
				case HttpConnection.DID_ERROR: {
					netconnect = true;
					// startButton.setVisibility(ImageView.VISIBLE);
					// LogoButton.setVisibility(ImageView.VISIBLE);
					// Optionbutton.setVisibility(ImageView.VISIBLE);
					// relativeLayoutaa.setVisibility(RelativeLayout.INVISIBLE);
					// startimage.setVisibility(ImageView.INVISIBLE);
					// icon.setVisibility(ImageView.INVISIBLE);
					text.setVisibility(TextView.INVISIBLE);
					Exception e = (Exception) message.obj;
					e.printStackTrace();
					break;
				}
				}
			}
		};
		try {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager
					.getCellLocation();
			Levelseet.cid = cellLocation.getCid();
			Levelseet.lac = cellLocation.getLac();
			Levelseet.locale = this.getBaseContext().getResources()
					.getConfiguration().locale.getCountry();
		} catch (Exception e) {
			// TODO: handle exception
		}
		new HttpConnection(handler)
				.post("http://www.nydtech.com/user_info/submit_form.php");
	}

	private void advertisement() {
		// //
		// //////////////////////////////////////Endconnect///////////////////////////////////////////////////////////////////////
		// //
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		AdView adView = new AdView(this, AdSize.BANNER, "a15000063ee93cd");
		layout.addView(adView);
		AdRequest request = new AdRequest();
		request.setTesting(true);
		adView.loadAd(request);
	}

}