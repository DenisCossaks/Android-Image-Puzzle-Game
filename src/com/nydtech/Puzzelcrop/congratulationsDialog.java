package com.nydtech.Puzzelcrop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/** Class Must extends with Dialog */
/** Implement onClickListener to dismiss dialog when OK Button is pressed */
public class congratulationsDialog extends Dialog implements OnClickListener {
        Button okButton;
        Button saveButton;
        Button save;
        PuzzelcropActivity app;
        TextView text;
        TextView title;
        Context mContext;
        Dialog dialog;
        Typeface fontface;
        public congratulationsDialog(Context context,PuzzelcropActivity app1) {
                super(context);
                /** Design the dialog in main.xml file */
                mContext = context;
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.dialog);
                app = app1;
                fontface = Typeface.createFromAsset( context.getAssets(), "fonts/HNHeavy.ttf");
                Display display = ((WindowManager)mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();  
                 
                int width = display.getWidth();  
                int height = display.getHeight();
               
                text= (TextView)findViewById(R.id.message);
                title= (TextView)findViewById(R.id.title);
                if(width == 320 || width == 240 )
                {
                        text.setTextSize(15);
                        title.setTextSize(25);
                }
               
                text.setTypeface(fontface);
                okButton = (Button) findViewById(R.id.OkButton);
                okButton.setOnClickListener(this);
                okButton.setTypeface(fontface);
        }

        public void showDialog()
        {
                   
                   String message="";
                   message+="You have completed the game in ";
                   message+=app.getTimeText();
                   message+=" and ";
                   message+=app.getMovesCount();
                   message+=" moves";
                   text.setText(message);
                   
                   this.show();
        }
       
        public void onClick(View v) {
                /** When OK Button is clicked, dismiss the dialog */
                if (v == okButton){
                	app.resetTimer();
                   // app.updateMovesCount(0);
                    dismiss();
                }
                else if (v == saveButton)
                {
                        dialog = new saveuserDialog(mContext,app);
                        dialog.show();
                        this.dismiss();
                }
               
        }

}


