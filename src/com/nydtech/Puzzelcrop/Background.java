package com.nydtech.Puzzelcrop;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Background {
       
         private Bitmap img; // the image of the numbers
         private int width             = 0;
         private int height            = 0;
         private int ROWS     		   = Levelseet.row;
         private int COLUMNS           = Levelseet.row;
         private int xoffsetLandscape  = 0;
         private int xoffsetportrait   = 10;
         private int yoffsetportrait   = 10;
         public int cellSizeWidth      = 0;
         public int cellSizeHeight     = 0;
         private int reqPlaywidth      = 0;
         private int reqPlayhight      = 0;
         public int mainimagewight     = 0;
         public int mainimagehight     = 0;
         private boolean isPortrait;
         private boolean isUpdateNumberCellsPositions;
         private Context mContext;
         private Paint mPaint;
         private playareaview mParent;
         private int lineGap = 3;
         public int iconsize;
         private numbers[] numbers;
         private Display display;
         public boolean soundONOFF = true;
         private PuzzelcropActivity app;
         private int timeTextX=0;
         private int timeTextY=0;
         private int movesTextX=0;
         private int movesTextY=0;
        
         /////////////////manu ///////////////////
         private int startingmanuX;
         private int startingmanuY;
         private int buttonmanuWidth;
         private int buttonmanuHeight;
         ////////////////////////manuback ////////////////////
         private int startingmanybackX;
         private int startingmanybackY;
         private int buttonmanybackWidth;
         private int buttonmanybackHeight;
         ////////////////////////reset ////////////////////
         private int startingresetX;
         private int startingresetY;
         private int buttonresetWidth;
         private int buttonresetHeight;
         ////////////////////////easy ////////////////////
         private int startingeasyX;
         private int startingeasyY;
         private int buttoneasyWidth;
         private int buttoneasyHeight;
         ////////////////////////normal ////////////////////
         private int startingnormalX;
         private int startingnormalY;
         private int buttonnormalWidth;
         private int buttonnormalHeight;
         ////////////////////////Hard ////////////////////
         private int startinghardX;
         private int startinghardY;
         private int buttonhardWidth;
         private int buttonhardHeight;
         ////////////////////////Sound////////////////////
         private int startingsoundX;
         private int startingsoundY;
         private int buttonsoundWidth;
         private int buttonsoundHeight;
         ////////////////////////Sound////////////////////
         private int startingiconX;
         private int startingiconY;
         private int buttoniconWidth;
         private int buttoniconHeight;
         ////////////////////////NUMBER////////////////////
         private int startingnumberX;
         private int startingnumberY;
         private int buttonnumberWidth;
         private int buttonnumberHeight;

         public ArrayList <Integer> randomNos ;
         private Random randomizer;
         Rect destination = new Rect();
         Rect destinationmanu = new Rect();
         Rect destinationreset = new Rect();
         Rect destinationmanuback = new Rect();
         Rect destinationeasy = new Rect();
         Rect destinationnormal = new Rect();
         Rect destinationhard = new Rect();
         Rect destinationsound = new Rect();
         Rect destinationicon = new Rect();
         Rect destinationnumbering = new Rect();
         Rect destinationiconfull = new Rect();
         Rect destinationtime = new Rect();
         Rect destinationhint = new Rect();
         Rect destinationhintR = new Rect();
         Rect destinationbackwhereplay = new Rect();
         

         public class numbersCellPosition
                {
                        public int x;
                        public int y;
                }
         public ArrayList<numbersCellPosition > numbersPos = new ArrayList<numbersCellPosition>();
               
         
         public  Bitmap manu,reset,easy,normal,hard,numbering,backmanu,soundbtn,iconbtn,timebar,Hintbox,backwhereplay;
         public  Bitmap easyin,normalin,hardin,Divider;
         Background(Context context,Paint paint,playareaview parent,numbers[] aNum)
         {
        	  //  app = (PuzzelcropActivity)getApplication();
        ////	 Log.e("number"+aNum.length, "mumber");
                mContext = context;
                mPaint   = paint;
                mParent  = parent;
                numbers  = aNum;
                randomizer = new Random();
                randomNos  = new ArrayList<Integer>();
               
                display = ((WindowManager)mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();  
             /////////    Log.e("ccc"+display.getWidth(), "ddd"+display.getHeight());
                width = display.getWidth();  
                height = display.getHeight();
               
               
                if(width == 320 || width == 240 )
                {
                        mPaint.setTextSize(15);
                       // iconsize        = 52;
                        xoffsetportrait = (height *4)/100;
                        yoffsetportrait = 25;
                        
                }
                else
                {
                        mPaint.setTextSize(25);
                   //     iconsize        = 75;
                        xoffsetportrait = (height *4)/100;
                        yoffsetportrait = 25;
                }
               
                updateBackground();
               
         }

         public void generateRandomNos()
         {
         int number = Math.abs(randomizer.nextInt(Levelseet.totalcount-1));
        	// int number = (16);
         if(randomNos.size() > 0)
                 randomNos.clear();

                for(int i=0;i<Levelseet.totalcount;i++)
                {
                         while(isalreadyAdded(number)==false){
                        	// number = (16);
                                 number = Math.abs(randomizer.nextInt(Levelseet.totalcount));
                         }
                        randomNos.add(number);
                }
         }
         
         public boolean isalreadyAdded(int number)
         {
                 boolean numberFound = true;
         
                 if(randomNos.size() == 0)
         {
                 return numberFound;
         }
         else
         {
                         for(int i =0;i<randomNos.size();i++)
                         {
                                 if(randomNos.get(i)==number)
                                 {
                                        numberFound = false;
                                        break;
                                 }
                         }      
         }
                 return numberFound;
         }
         public void calculateTimeandMovesTextXY(int width,int height)
         {

                                        timeTextY = (height *89)/100;
                                        movesTextY= (height *75)/100;
                                        timeTextX = (width *41)/100;
                                        movesTextX = (width *60)/100;

                 
         }
         public void updateBackground()
         {
                        reqPlaywidth = display.getWidth();
                        reqPlayhight = display.getWidth();
                                 isPortrait = true;;

                        calculateTimeandMovesTextXY(width,height);
                        mParent.invalidate();
                        isUpdateNumberCellsPositions = true;
            }
         public Bitmap getBackgroundImage(boolean isRandom)
         {
                         Bitmap bitmap;
                         Bitmap mbitmap;
                         Bitmap bar;
                         bitmap = Bitmap.createBitmap(width,height, Config.ARGB_8888);
                         Canvas drawcanvas = new Canvas(bitmap);
                        
  //                       if(isPortrait)
                                 mbitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.mainbackground);
                                 bar = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bar);
//                         else
//                                 mbitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.landscape);

                         		Rect srcbackground = new Rect(0, 0, mbitmap.getWidth(), mbitmap.getHeight());
                         		Rect dstbackground = new Rect(0,0, Levelseet.SCREEN_WIDTH,Levelseet.SCREEN_HEIGHT);
                         	//	canvas.drawBitmap(questionbarbutton, srcQbar, dstQbar, null);
                 drawcanvas.drawBitmap(mbitmap,srcbackground,dstbackground,mPaint);
                 
                 
                 ////////////////////////////////bar///////////////////////
         		Rect srcbar = new Rect(0, 0, bar.getWidth(), bar.getHeight());
         		Rect dstbar = new Rect(0,Levelseet.SCREEN_HEIGHT-bar.getHeight(), Levelseet.SCREEN_WIDTH,Levelseet.SCREEN_HEIGHT);
         	//	canvas.drawBitmap(questionbarbutton, srcQbar, dstQbar, null);
                drawcanvas.drawBitmap(bar,srcbar,dstbar,mPaint);
                 //////////////////////////////////////////////////////////////////////////
                 int yOffset;
                 int xOffset ;
                                     cellSizeWidth = (reqPlaywidth-((reqPlaywidth*7)/100) - ((3*lineGap)+xoffsetportrait ))/ Levelseet.row;
                              
                                     cellSizeHeight = (reqPlaywidth-((reqPlaywidth*7)/100) - ((3*lineGap)+xoffsetportrait ))/ Levelseet.row;
                                     mainimagewight = (reqPlaywidth-((reqPlaywidth*7)/100) - ((3*lineGap)+xoffsetportrait ));
                                     mainimagehight = (reqPlaywidth-((reqPlaywidth*7)/100) - ((3*lineGap)+xoffsetportrait ));
                                     Log.e("requreW"+reqPlaywidth, "requireH");
                                     yOffset = (height *15)/100;
                                     xOffset = xoffsetportrait;
                 Log.e("reqPlaywidth"+reqPlaywidth, "reqPlaywidth"+reqPlaywidth);
                 //////////////////////////////////////////////////////////////////////////
                 
             ////////////////////////////backwhere imageback set ////////////////
             
        	 backwhereplay = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.imagebackbox);
             int xOffsettt;
             int yOffsettt;

                     xOffsettt                         = 0;
                     yOffsettt                         = (height *11)/100;
                     destinationbackwhereplay.top      = yOffsettt;
                     destinationbackwhereplay.left     = xOffsettt;
                     destinationbackwhereplay.right    = xOffsettt +reqPlaywidth;
                     destinationbackwhereplay.bottom   = yOffsettt +reqPlaywidth;
                     drawcanvas.drawBitmap(backwhereplay,null,destinationbackwhereplay,mPaint);
             
             
             /////////////////////////////////////////////////////////////////////////////
            
             mbitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.square);
            
                      
       
                              /////   Log.e("reqPlaywidth"+cellSizeWidth, "reqPlaywidth");
                         int rightOffset=xOffset+cellSizeWidth;
                         int bottomOffset=yOffset+cellSizeHeight;
                
                         for( int i=0;i<Levelseet.row;i++)
                         {
                                 for(int j=0;j<Levelseet.row;j++)
                                 {
                                                 destination.top    = yOffset;
                                                 destination.left   = xOffset;
                                                 destination.right  = rightOffset;
                                                 destination.bottom = bottomOffset;
                                  
                                                 if(isUpdateNumberCellsPositions)
                                                 {
                                                         
                                                         numbersCellPosition obj = new numbersCellPosition();
                                                         obj.x = destination.left;
                                                         obj.y = destination.top;
                                                         numbersPos.add(obj);
                                                 }
                                                
                                                 drawcanvas.drawBitmap(mbitmap,null,destination,mPaint);
                                                 xOffset = xOffset + cellSizeWidth+lineGap;
                                                 rightOffset = xOffset+cellSizeWidth;
                                 } // End of inner for loop

                                                  xOffset = xoffsetportrait;

                                                        
                                         rightOffset  =xOffset+ cellSizeWidth;
                                         yOffset      = yOffset + cellSizeHeight+lineGap;
                                         bottomOffset = bottomOffset + cellSizeHeight+lineGap;

                    }
                         isUpdateNumberCellsPositions = false;

                         yOffset = (height *15)/100;
                     ///////    Log.e("hight"+buttonmanuHeight, "fff");
                         xOffset = xoffsetportrait;
                         
                          rightOffset=xOffset+cellSizeWidth;
                          bottomOffset=yOffset+cellSizeHeight;
                          int numbersCount=0;

                         for( int i=0;i<Levelseet.row;i++)
                                 {
                                  for(int j=0;j<Levelseet.row;j++)
                                         {
                                                 if(numbersCount < Levelseet.totalcount)
                                                 {
                                                 ///////	Log.e("level4"+Levelseet.totalcount, "level4"+Levelseet.row);
                                                         if(isRandom || numbers[randomNos.get(numbersCount)].getX() == 0)
                                                         {
                                                                 destination.top = yOffset;
                                                                 destination.left = xOffset;
                                                                 destination.right = rightOffset;
                                                                 destination.bottom = bottomOffset;
                                                                 numbers[randomNos.get(numbersCount)].setX(xOffset);
                                                                 numbers[randomNos.get(numbersCount)].setY(yOffset);
                                                                 numbers[randomNos.get(numbersCount)].setRight(rightOffset );
                                                                 numbers[randomNos.get(numbersCount)].setBottom(bottomOffset );
                                                         }
                                                         else
                                                         {
                                                                 destination.top    = numbers[randomNos.get(numbersCount)].getY();
                                                                 destination.left   = numbers[randomNos.get(numbersCount)].getX();
                                                                 destination.right  = numbers[randomNos.get(numbersCount)].getRight();
                                                                 destination.bottom = numbers[randomNos.get(numbersCount)].getBottom();
                                                         }
                                                         if(randomNos.get(numbersCount) !=(Levelseet.totalcount-1)){
                                                        	if(Levelseet.icontouch == false){
                                               drawcanvas.drawBitmap(numbers[randomNos.get(numbersCount)].getBitmap(),null,destination,mPaint);
                                                        	}
                                                         if(Levelseet.texttouch == true){
                                               drawcanvas.drawText(""+numbers[randomNos.get(numbersCount)].getID(),(destination.left+destination.right)/2,(destination.top+destination.bottom)/2,mPaint);
                                                         }
                                                         }
                                                         numbersCount++;
                                                 }
                                                 xOffset = xOffset + cellSizeWidth+lineGap;
                                                 rightOffset = xOffset+cellSizeWidth;
                                         }
 //
//                                  if(isPortrait)
//                                  {
                                          xOffset = xoffsetportrait;
//                                  }
//                                  else
//                                  {
//                                          xOffset=xoffsetLandscape;
//                                  }

                                 rightOffset = xOffset+cellSizeWidth;
                                 yOffset = yOffset + cellSizeHeight+lineGap;
                                 bottomOffset = bottomOffset + cellSizeHeight+lineGap;
                         }
                
                         return bitmap;
                 }



        public void drawTimeText(String text,Canvas canvas)
        {
        	//timebar = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.timebar);
                    //    canvas.drawBitmap(timebar, timeTextX, timeTextY, mPaint);
                        canvas.drawText(""+text, timeTextX, timeTextY, mPaint);
            }
       
        
//        public void drawMovesText(String text,Canvas canvas)
//        {
//                canvas.drawText("Moves : "+text, movesTextX, movesTextY, mPaint);
//        }      
        public int getStartButtonX()
        {
                return  startingmanuX;
               
        }
        public int getStartButtonY()
        {
                return  startingmanuY;
        }

        public int getbuttonmanuWidth()
        {
                return buttonmanuWidth;
        }
        public int getbuttonmanuHeight()
        {
                return buttonmanuHeight;
        }
        ////////////////back Icon //////////////////
       public void drawiconbackgroundimage(Canvas canvas){
         //  if(Levelseet.icontouch == true){
        	   int yOffset;
        	   int xOffset;
        	   int rightOffset;
               int bottomOffset;
                yOffset                              =(height *15)/100;
                xOffset                              = xoffsetportrait;
                rightOffset                          = xOffset+mainimagewight+5;
                bottomOffset                         = yOffset+mainimagehight+5;
                destinationiconfull.top              = yOffset;
                destinationiconfull.left             = xOffset;
                destinationiconfull.right            = rightOffset;
                destinationiconfull.bottom 			 = bottomOffset;
                BitmapDrawable drawable              = (BitmapDrawable) MainActivity.mImageView.getDrawable();
       		    iconbtn                              = drawable.getBitmap();
                canvas.drawBitmap(iconbtn,null,destinationiconfull,mPaint);
                
           //    }
       }
        public void  drawmenuButtons(Canvas canvas)
        {         
        	if(Levelseet.manu == true)
                manu = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.manu);
//        	else
//        		manu = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.manu);
//                timeTextY = (height *89)/100;
//                movesTextY= (height *75)/100;
//                timeTextX = (width *41)/100;
//                movesTextX = (width *60)/100;
                int xOffset;
                int yOffset;

                        xOffset                    = (width *30)/100;
                        yOffset                    = (height *76)/100;
                        startingmanuX              = xOffset;
                        startingmanuY              = yOffset;
                        buttonmanuWidth            = manu.getWidth();
                        buttonmanuHeight           = manu.getHeight();
                        destinationmanu.top        = yOffset;
                        destinationmanu.left       = xOffset;
                        destinationmanu.right      = xOffset + manu.getWidth();
                        destinationmanu.bottom     = yOffset+manu.getHeight();
                        canvas.drawBitmap(manu,null,destinationmanu,mPaint);
                        
              //  canvas.drawBitmap(bitmap, startingmanuX,startingmanuY, mPaint);
        }
        
        //////////////////////////////reset/////////////////////////
        public int getStartresetButtonX()
        {
                return  startingresetX;
               
        }
        public int getStartresetButtonY()
        {
                return  startingresetY;
        }

        public int getbuttonresetWidth()
        {
                return buttonresetWidth;
        }
        public int getbuttonresetHeight()
        {
                return buttonresetHeight;
        }

        public void  drawresetButtons(Canvas canvas)
        {         
        	reset = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.reset);

                int xOffset;
                int yOffset;

                        xOffset                  = (width *30)/100+manu.getWidth();
                        yOffset                  = (height *76)/100;
                        startingresetX           = xOffset;
                        startingresetY           = yOffset;
                        buttonresetWidth         = reset.getWidth();
                        buttonresetHeight        = reset.getHeight();
                        destinationreset.top     = yOffset;
                        destinationreset.left    = xOffset;
                        destinationreset.right   = xOffset + reset.getWidth();
                        destinationreset.bottom  = yOffset+reset.getHeight();
                        canvas.drawBitmap(reset,null,destinationreset,mPaint);
                        
              //  canvas.drawBitmap(bitmap, startingmanuX,startingmanuY, mPaint);
        }
   
     ////////////////////////////manuback //////////////////
     public int getmanybackButtonX()
     {
             return  startingmanybackX;
            
     }
     public int getmanybackButtonY()
     {
             return  startingmanybackY;
     }

     public int getbuttonmanybackWidth()
     {
             return buttonmanybackWidth;
     }
     public int getbuttonmanybackHeight()
     {
             return buttonmanybackHeight;
     }
     
     ////////////////////////////manuback //////////////////////////
     public void  drawmanybackButton(Canvas canvas)
     {
         //  Bitmap mbitmap;

    	     backmanu = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.photobtn);
    	     Divider  = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.divider);
             int xOffset;
             int yOffset;

                     xOffset                    = 0;
                     yOffset                    = height-backmanu.getHeight();
                     startingmanybackX 		    = xOffset;
                     startingmanybackY 		    = yOffset;
                     buttonmanybackWidth 	    = backmanu.getWidth();
                     buttonmanybackHeight 	    = backmanu.getHeight();
                     destinationmanuback.top    = yOffset;
                     destinationmanuback.left   = xOffset;
                     destinationmanuback.right  = xOffset + backmanu.getWidth();
                     destinationmanuback.bottom = yOffset+ backmanu.getHeight();
                     canvas.drawBitmap(backmanu,null,destinationmanuback,mPaint);
                     canvas.drawBitmap(Divider, backmanu.getWidth(), height-backmanu.getHeight(), null);
     }
     //////////////////////////////////////////
     public int geteasyButtonX()
     {
             return  startingeasyX;
            
     }
     public int geteasyButtonY()
     {
             return  startingeasyY;
     }

     public int getbuttoneasyWidth()
     {
             return buttoneasyWidth;
     }
     public int getbuttoneasyHeight()
     {
             return buttoneasyHeight;
     }
     
     ////////////////////////////easy //////////////////////////
     public void  draweasyButton(Canvas canvas)
     {
         //  Bitmap mbitmap;
           if(Levelseet.easy == false){
        	   easy = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.easyin);
           }
           else{
               easy = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.easy);
           }
          
             int xOffset;
             int yOffset;

                     xOffset                  = backmanu.getWidth()+Divider.getWidth()+1;
                     yOffset                  = height-easy.getHeight();
                     startingeasyX            = xOffset;
                     startingeasyY            = yOffset;
                     buttoneasyWidth          =  easy.getWidth();
                     buttoneasyHeight         = easy.getHeight();
                     destinationeasy.top      = yOffset;
                     destinationeasy.left     = xOffset;
                     destinationeasy.right    = xOffset + easy.getWidth();
                     destinationeasy.bottom   = yOffset+easy.getHeight();
                     canvas.drawBitmap(easy,null,destinationeasy,mPaint);
                     canvas.drawBitmap(Divider, xOffset + easy.getWidth(), height-backmanu.getHeight(), null);
     }
     
     /////////////////////////normal ///////////////////////
     public int getnormalButtonX()
     {
             return  startingnormalX;
            
     }
     public int getnormalButtonY()
     {
             return  startingnormalY;
     }

     public int getbuttonnormalWidth()
     {
             return buttonnormalWidth;
     }
     public int getbuttonnormalHeight()
     {
             return buttonnormalHeight;
     }
     
     ////////////////////////////Normal //////////////////////////
     public void  drawnormalButton(Canvas canvas)
     {
         //  Bitmap mbitmap;
           if(Levelseet.normal == false){
               normal = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.normalin);
           }
           else {
        	   normal = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.normal);
		}
             int xOffset;
             int yOffset;

                     xOffset                     = backmanu.getWidth()+easy.getWidth()+Divider.getWidth()+2;
                     yOffset                     = height-normal.getHeight();
                     startingnormalX 		     = xOffset;
                     startingnormalY 		     = yOffset;
                     buttonnormalWidth 		     =  normal.getWidth();
                     buttonnormalHeight 	     = normal.getHeight();
                     destinationnormal.top       = yOffset;
                     destinationnormal.left      = xOffset;
                     destinationnormal.right     = xOffset + normal.getWidth();
                     destinationnormal.bottom    = yOffset+normal.getHeight();
                     canvas.drawBitmap(normal,null,destinationnormal,mPaint);
                     canvas.drawBitmap(Divider, xOffset + normal.getWidth(), height-backmanu.getHeight(), null);
     }
     
     /////////////////////////hard ///////////////////////
     public int gethardButtonX()
     {
             return  startinghardX;
            
     }
     public int gethardButtonY()
     {
             return  startinghardY;
     }

     public int getbuttonhardWidth()
     {
             return buttonhardWidth;
     }
     public int getbuttonhardHeight()
     {
             return buttonhardHeight;
     }
     
     ////////////////////////////Hard //////////////////////////
     public void  drawhardButton(Canvas canvas)
     {
         //  Bitmap mbitmap;
           if(Levelseet.hard  == false){
        	   hard = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.hardin);
           }
           else {
        	   hard = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.hard);
		}
           
                     int xOffset;
                     int yOffset;

                     xOffset                     = backmanu.getWidth()+easy.getWidth()+normal.getWidth()+Divider.getWidth()+3;
                     yOffset                     = height-hard.getHeight();
                     startinghardX 			     = xOffset;
                     startinghardY 			     = yOffset;
                     buttonhardWidth		     = hard.getWidth();
                     buttonhardHeight 	       	 = hard.getHeight();
                     destinationhard.top         = yOffset;
                     destinationhard.left        = xOffset;
                     destinationhard.right       = xOffset + hard.getWidth();
                     destinationhard.bottom      = yOffset+hard.getHeight();
                     canvas.drawBitmap(hard,null,destinationhard,mPaint);
                     canvas.drawBitmap(Divider, xOffset + hard.getWidth(), height-backmanu.getHeight(), null);
     }

     
     /////////////////////////sound ///////////////////////
     public int getsoundButtonX()
     {
             return  startingsoundX;
            
     }
     public int getsoundButtonY()
     {
             return  startingsoundY;
     }

     public int getbuttonsoundWidth()
     {
             return buttonsoundWidth;
     }
     public int getbuttonsoundHeight()
     {
             return buttonsoundHeight;
     }
     
     ////////////////////////////sound //////////////////////////
     public void  drawsoundButton(Canvas canvas)
     {
         //  Bitmap mbitmap;
            if(Levelseet.sound == true){
    	     soundbtn = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.soundmainpage);
            }
            else {
            	soundbtn = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.soundmainpagenai);
			}
             int xOffset;
             int yOffset;

                     xOffset                      = Levelseet.SCREEN_WIDTH-2*soundbtn.getWidth();
                     yOffset                      = height-soundbtn.getHeight();
                     startingsoundX 			  = xOffset;
                     startingsoundY 			  = yOffset;
                     buttonsoundWidth		      = soundbtn.getWidth();
                     buttonsoundHeight 		      = soundbtn.getHeight();
                     destinationsound.top         = yOffset;
                     destinationsound.left        = xOffset;
                     destinationsound.right       = xOffset + soundbtn.getWidth();
                     destinationsound.bottom      = yOffset+soundbtn.getHeight();
                     canvas.drawBitmap(soundbtn,null,destinationsound,mPaint);
     }
     
     /////////////////////////ICON ///////////////////////
     
     public int geticonButtonX()
     {
             return  startingiconX;
            
     }
     public int geticonButtonY()
     {
             return  startingiconY;
     }

     public int getbuttoniconWidth()
     {
             return buttoniconWidth;
     }
     public int getbuttoniconHeight()
     {
             return buttoniconHeight;
     }
     
     ////////////////////////////ICON //////////////////////////
     public void  drawiconButton(Canvas canvas)
     {
//       timeTextY = (height *89)/100;
//       movesTextY= (height *75)/100;
//       timeTextX = (width *41)/100;
//       movesTextX = (width *60)/100;
    	 ///////////////////image to bitmap//////////////////////////
    	 //  xhintOffset             = 5;
        //   yhintOffset             = height-1*easy.getHeight()-Hintbox.getHeight()-5;
 		BitmapDrawable drawable    = (BitmapDrawable) MainActivity.mImageView.getDrawable();
		 iconbtn                   = drawable.getBitmap();
		 iconsize                  = Hintbox.getWidth()-14;

             int xOffset;
             int yOffset;

                     xOffset                    = 12;
                     yOffset                    = height-1*easy.getHeight()-Hintbox.getHeight()+2;//(height *78)/100;
                     startingiconX 			    = xOffset;
                     startingiconY 			    = yOffset;
                     buttoniconWidth		    = xOffset +iconsize;
                     buttoniconHeight 		    = yOffset+ iconsize;
                     destinationicon.top        = yOffset;
                     destinationicon.left       = xOffset;
                     destinationicon.right      = xOffset +iconsize;
                     destinationicon.bottom     = yOffset+ iconsize;
                     canvas.drawBitmap(iconbtn,null,destinationicon,mPaint);
     }
     
     ////////////////////////NUMBERING ///////////////////////
     public int getnumberButtonX()
     {
             return  startingnumberX;
            
     }
     public int getnumberButtonY()
     {
             return  startingnumberY;
     }

     public int getbuttonnumberWidth()
     {
             return buttonnumberWidth;
     }
     public int getbuttonnumberHeight()
     {
             return buttonnumberHeight;
     }
     
     ////////////////////////////NUmber //////////////////////////
     public void  drawnumberButton(Canvas canvas)
     {
    	 // xOffset                    = 12;
       //   yOffset                    = height-1*easy.getHeight()-Hintbox.getHeight()+2;//(height *78)/100;
       int numbersize =  Hintbox.getWidth()-14;
    	 numbering = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.numbering);
             int xOffset;
             int yOffset;

                     xOffset                            = width-5-Hintbox.getWidth()+7;
                     yOffset                            = height-1*easy.getHeight()-Hintbox.getHeight()+2;//(height *78)/100;//height-2*manu.getHeight()-numbering.getHeight();
                     startingnumberX 			        = xOffset;
                     startingnumberY 			        = yOffset;
                     buttonnumberWidth		            = numbersize;
                     buttonnumberHeight 		        = numbersize;
                     destinationnumbering.top           = yOffset;
                     destinationnumbering.left          = xOffset;
                     destinationnumbering.right         = xOffset +numbersize;
                     destinationnumbering.bottom        = yOffset +numbersize;
                     canvas.drawBitmap(numbering,null,destinationnumbering,mPaint);
     }
     

    
     ////////////////////////////timebar //////////////////////////
     public void  drawtimeButton(Canvas canvas)
     {
         //  Bitmap mbitmap;

           timebar = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.timebar);
             int xOffset;
             int yOffset;

                     xOffset                  = width/2-timebar.getWidth()/2;
                     yOffset                  = height-2*easy.getHeight()-height/35;

                     destinationtime.top      = yOffset;
                     destinationtime.left     = xOffset;
                     destinationtime.right    = xOffset +timebar.getWidth();
                     destinationtime.bottom   = yOffset +timebar.getHeight()+height/35;
                     canvas.drawBitmap(timebar,null,destinationtime,mPaint);
                     ///////////////////////////////HINTleft//////////////////////
                     Hintbox = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.hintbox);
                     int xhintOffset;
                     int yhintOffset;

                              xhintOffset             = 5;
                              yhintOffset             = height-1*easy.getHeight()-Hintbox.getHeight()-5;

                             destinationhint.top      = yhintOffset;
                             destinationhint.left     = xhintOffset;
                             destinationhint.right    = xhintOffset +Hintbox.getWidth();
                             destinationhint.bottom   = yhintOffset +Hintbox.getHeight();
                             canvas.drawBitmap(Hintbox,null,destinationhint,mPaint);
                             /////////////////////////HINTRITE///////////////////////
                             int xhintROffset;
                             int yhintROffset;

                                     xhintROffset               = width-5-Hintbox.getWidth();
                                     yhintROffset               = height-1*easy.getHeight()-Hintbox.getHeight()-5;

                                     destinationhintR.top      = yhintROffset;
                                     destinationhintR.left     = xhintROffset;
                                     destinationhintR.right    = xhintROffset +Hintbox.getWidth();
                                     destinationhintR.bottom   = yhintROffset +Hintbox.getHeight();
                                     canvas.drawBitmap(Hintbox,null,destinationhintR,mPaint);
                             
     }
     
     
       
    public boolean isGameFinished()
        {
                        boolean isGameFinish = true;
                        for(int i =0;i<numbers.length;i++)
                        {
                        ////	Log.e("level4"+numbers.length, "level4"+numbers.length);
                                if(numbers[i].getX() == numbersPos.get(i).x && numbers[i].getY() == numbersPos.get(i).y)
                                {
                                        //System.out.println("Numbers dont match");
                                        //  return isGameFinish;
                                       //	isGameFinish = true;
                                }
                                else
                                {
                                        isGameFinish = false;
                                        break;
                                }
                        }
                        return isGameFinish;
                }
}

