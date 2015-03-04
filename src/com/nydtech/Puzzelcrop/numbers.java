package com.nydtech.Puzzelcrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class numbers {
	     //public ImageView img;
         public Bitmap img; // the image of the numbers
         private int coordX = 0; // the x coordinate at the canvas
         private int coordY = 0; // the y coordinate at the canvas
         private int bottom;
         private int right;
         private int id; // gives every number a unique id
         private static int count = 1;
         private boolean goRight = true;
         private boolean goDown = true;
                public numbers(Context context, int drawable) {
                
                        BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;

		switch (drawable) {
		case 0:

			img = MainActivity.chunkedImages.get(0);

			id = drawable;
			break;
		case 1:
			img = MainActivity.chunkedImages.get(1);

			id = drawable;
			break;
		case 2:
			img = MainActivity.chunkedImages.get(2);

			id = drawable;
			break;
		case 3:
			img = MainActivity.chunkedImages.get(3);

			id = drawable;
			break;
		case 4:
			img = MainActivity.chunkedImages.get(4);

			id = drawable;
			break;
		case 5:
			img = MainActivity.chunkedImages.get(5);

			id = drawable;
			break;
		case 6:
			img = MainActivity.chunkedImages.get(6);

			id = drawable;
			break;
		case 7:
			img = MainActivity.chunkedImages.get(7);

			id = drawable;
			break;
		case 8:
			if (Levelseet.totalcount == 9) {
				img = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.square);
			} else {
				img = MainActivity.chunkedImages.get(8);
			}

			id = drawable;
			break;
		case 9:
			img = MainActivity.chunkedImages.get(9);
			id = drawable;
			break;
		case 10:
			img = MainActivity.chunkedImages.get(10);
			id = drawable;
			break;
		case 11:
			img = MainActivity.chunkedImages.get(11);
			id = drawable;
			break;
		case 12:
			img = MainActivity.chunkedImages.get(12);
			id = drawable;
			break;
		case 13:
			img = MainActivity.chunkedImages.get(13);
			id = drawable;
			break;
		case 14:
			img = MainActivity.chunkedImages.get(14);
			id = drawable;
			break;
		case 15:
			img = MainActivity.chunkedImages.get(15);
			id = drawable;
			break;
		case 16:
			if (Levelseet.totalcount == 16) {
				img = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.square);
			} else {
				img = MainActivity.chunkedImages.get(16);
			}

			id = drawable;
			break;
		case 17:
			img = MainActivity.chunkedImages.get(17);
			id = drawable;
			break;
		case 18:
			img = MainActivity.chunkedImages.get(18);
			id = drawable;
			break;
		case 19:
			img = MainActivity.chunkedImages.get(19);
			id = drawable;
		case 20:
			img = MainActivity.chunkedImages.get(20);
			id = drawable;
			break;
		case 21:
			img = MainActivity.chunkedImages.get(21);
			id = drawable;
			break;
		case 22:
			img = MainActivity.chunkedImages.get(22);
			id = drawable;
			break;
		case 23:
			img = MainActivity.chunkedImages.get(23);
			id = drawable;
			break;
		case 24:
			if (Levelseet.totalcount == 25) {
				img = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.square);
			} else {
				img = MainActivity.chunkedImages.get(24);
			}

			id = drawable;
			break;

		}
                }
                
                public static int getCount() {
                        return count;
                }
                void setX(int newValue) {
                coordX = newValue;
            }
                
                public int getX() {
                        return coordX;
                }

                void setY(int newValue) {
                coordY = newValue;
           }
                
                public int getY() {
                        return coordY;
                }
                
                public int getID() {
                        return id;
                }
                void setRight(int w)
                {
                        right = w;
                }
                int getRight()
                {
                        return right;
                }
                void setBottom(int h)
                {
                        bottom = h;
                }
                int getBottom()
                {
                        return bottom;
                }
                public Bitmap getBitmap() {
                        return img;
                }
                
                public void moveBall(int goX, int goY) {
                        // check the borders, and set the direction if a border has reached
                        if (coordX > 270){
                                goRight = false;
                        }
                        if (coordX < 0){
                                goRight = true;
                        }
                        if (coordY > 400){
                                goDown = false;
                        }
                        if (coordY < 0){
                                goDown = true;
                        }
                        // move the x and y 
                        if (goRight){
                                coordX += goX;
                        }else
                        {
                                coordX -= goX;
                        }
                        if (goDown){
                                coordY += goY;
                        }else
                        {
                                coordY -= goY;
                        }
                        
                }

}

