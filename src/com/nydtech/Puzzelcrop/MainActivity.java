 package com.nydtech.Puzzelcrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends Activity  {
	private Uri mImageCaptureUri;
	public static ImageView mImageView;
	private SlidingDrawer splitslide;
	private Bitmap oneimage;
	PuzzelcropActivity pussle;// = new PuzzelcropActivity();
	  private ImageView camerabutton;
	  private ImageView galarybutton;
	  private ImageView imagebutton;
	  private ImageView cancelbutton;
//	private	Button Camera;
//	private	Button Galarry;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE   = 3;
	public static ArrayList<Bitmap> chunkedImages ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Display display = getWindowManager().getDefaultDisplay();
		Levelseet.SCREEN_HEIGHT = display.getHeight();
		Levelseet.SCREEN_WIDTH = display.getWidth();
        setContentView(R.layout.crop);
        
        oneimage = BitmapFactory.decodeResource(this.getResources(),R.drawable.idea_picture);
        
        Levelseet.row = 4;
        Levelseet.totalcount = 16;
        Levelseet.normal = true;
        Levelseet.easy   = false;
        Levelseet.hard   = false;

		
		camerabutton      	= (ImageView) findViewById(R.id.Camera);
		galarybutton      	= (ImageView) findViewById(R.id.Gallery);
		imagebutton         = (ImageView) findViewById(R.id.Images);
		cancelbutton      	= (ImageView) findViewById(R.id.Cancel);
		mImageView		    = (ImageView) findViewById(R.id.iv_photo);
		//RelativeLayout layout = (RelativeLayout) findViewById(R.id.linearLayoutm);
//		AdView adView = new AdView(this, AdSize.BANNER, "a15000063ee93cd");
//		layout.addView(adView);
//		AdRequest request = new AdRequest();
//		request.setTesting(true);
//		adView.loadAd(request);
		
		camerabutton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				//dialog.show();
				Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
								   "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

				intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

				try {
					intent.putExtra("return-data", true);
					
					startActivityForResult(intent, PICK_FROM_CAMERA);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		galarybutton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
			}
		});
		
		cancelbutton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						PuzzleActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		imagebutton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				mImageView.setImageBitmap(oneimage);
				Intent intent = new Intent(MainActivity.this,
						dragimage.class);
				startActivity(intent);
				finish();
			}
		});
		
		
    }

  private  Bitmap photo;
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode != RESULT_OK) return;
	   
	    switch (requestCode) {
		    case PICK_FROM_CAMERA:
		    	doCrop();
		    	
		    	break;
		    	
		    case PICK_FROM_FILE: 
		    	mImageCaptureUri = data.getData();
		    	
		    	doCrop();
	    
		    	break;	    	
	    
		    case CROP_FROM_CAMERA:	    	
		        Bundle extras = data.getExtras();
	
		        if (extras != null) {	     
		        	
		            Bitmap photo = extras.getParcelable("data");
		            mImageView.setImageBitmap(photo);
		        	Intent intent = new Intent(MainActivity.this, dragimage.class);
		        	//	Intent intent = new Intent(MainActivity.this, dragimage.class);
		        		intent.putParcelableArrayListExtra("image chunks", chunkedImages);
		        		startActivity(intent);
		        	//	finish();
		         //   splitImage(16);
		        }
	
		        File f = new File(mImageCaptureUri.getPath());            
		        
		        if (f.exists()) f.delete();
	
		        break;

	    }
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	//	 mplayer.release();
//		if(mImageView!= null){
//			mImageView = null;
//		}
		
		this.finish();
		System.gc();
	}
    
    private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
    	
    	Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        
        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
        
        int size = list.size();
        
        if (size == 0) {	        
        	Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
        	
            return;
        } else {
        	intent.setData(mImageCaptureUri);
        	intent.putExtra("crop", "true");
        	intent.putExtra("inputX", 300);
        	intent.putExtra("inputY", 300);
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("aspectX", 300);
            intent.putExtra("aspectY", 300);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            ///////////////////////////////////
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null)
//            .setType("image/*")
//            .putExtra("crop", "true")
//            .putExtra("aspectX", width)
//            .putExtra("aspectY", height)
//            .putExtra("outputX", width)
//            .putExtra("outputY", height)
//            .putExtra("scale", true)
//            .putExtra("scaleUpIfNeeded", true)
//            .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
//            .putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

//////////////////////////////////////////////////////
        	if (size == 1) {
        		Intent i 		= new Intent(intent);
	        	ResolveInfo res	= list.get(0);
	        	
	        	i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
	        	
	        	startActivityForResult(i, CROP_FROM_CAMERA);
        	} else {
		        for (ResolveInfo res : list) {
		        	final CropOption co = new CropOption();
		        	
		        	co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
		        	co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
		        	co.appIntent= new Intent(intent);
		        	
		        	co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
		        	
		            cropOptions.add(co);
		        }
	        
		        CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
		        
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Choose Crop App");
		        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
		            public void onClick( DialogInterface dialog, int item ) {
		                startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
		            }
		        });
	        
		        builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
		            @Override
		            public void onCancel( DialogInterface dialog ) {
		               
		                if (mImageCaptureUri != null ) {
		                    getContentResolver().delete(mImageCaptureUri, null, null );
		                    mImageCaptureUri = null;
		                }
		            }
		        } );
		        
		        AlertDialog alert = builder.create();
		        
		        alert.show();
        	}
        }
	}

}