package com.nydtech.Puzzelcrop;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class scoresListview extends ListActivity{

	PuzzelcropActivity app;
        Typeface fontface;
        Display display;
        int width;
        int height;
       
        public void onCreate(Bundle savedInstanceState) {
             
                super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        app = (PuzzelcropActivity)getApplication();
        for(int i =0;i< app.userList.size();i++)
                System.out.println("name time count "+app.userList.get(i).nameText+":"+app.userList.get(i).timeText);
                fontface = Typeface.createFromAsset( getApplicationContext().getAssets(), "fonts/HNHeavy.ttf");
                display = ((WindowManager)app.getApplicationContext().getSystemService(app.getApplicationContext().WINDOW_SERVICE)).getDefaultDisplay();  
                 
                width = display.getWidth();  
               
                TextView title = (TextView) findViewById(R.id.title);
                if(width == 320 || width == 240 )
                {
                        title.setTextSize(25);
                }
                title.setTypeface(fontface);
        setListAdapter(new EfficientAdapter(this));
       
        }
    private  class EfficientAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Bitmap mIcon1;
        private Bitmap mIcon2;

        public EfficientAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);

        }

        public int getCount() {
            return app.userList.size()+1;
                //return DATA.length;
        }
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                AbsListView.LayoutParams layoutParams;
                if(width == 320 || width == 240 )
                        {
                         layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, 40);
                        }
                else
                {
                        layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, 80);
                }
                convertView = mInflater.inflate(R.layout.scoreslistview, null);
                convertView.setLayoutParams(layoutParams);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.moves = (TextView) convertView.findViewById(R.id.moves);
                if(position == 0 )
                {
                        if(width == 320 || width == 240 )
                        {
                               
                                holder.name.setTextSize(20);
                                holder.time.setTextSize(20);
                                holder.moves.setTextSize(20);
                        }
                        else
                        {
                                holder.name.setTextSize(28);
                                holder.time.setTextSize(28);
                                holder.moves.setTextSize(28);
                               
                        }
                holder.name.setText("Name ");
                holder.name.setTypeface(fontface);
               
                holder.time.setText("Time ");
                holder.time.setTypeface(fontface);
             
                holder.moves.setText("Moves ");
                holder.moves.setTypeface(fontface);
                }
                else
                {
                        if(width == 320 || width == 240 )
                        {
                               
                                holder.name.setTextSize(18);
                                holder.time.setTextSize(18);
                                holder.moves.setTextSize(18);
                        }
                        else
                        {
                                holder.name.setTextSize(28);
                                holder.time.setTextSize(28);
                                holder.moves.setTextSize(28);
                        }
                    holder.name.setText(app.userList.get(position-1).nameText);
                   
                    holder.name.setTypeface(fontface);
                   
                    holder.time.setText(app.userList.get(position-1).timeText);
                    holder.time.setTypeface(fontface);
                   
                    holder.moves.setText(""+app.userList.get(position-1).movesCount);
                    holder.moves.setTypeface(fontface);
                }
               
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
           
            return convertView;
        }
         class ViewHolder {
            TextView name;
            TextView time;
            TextView moves;
        }
    }

}

