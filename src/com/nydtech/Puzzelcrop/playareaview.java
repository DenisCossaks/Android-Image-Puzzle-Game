package com.nydtech.Puzzelcrop;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class playareaview extends View {
	private static String APPLICATION_ID = "500005b0d3d4e000c7000304";
	MainActivity mainactivity = new MainActivity();
	int positionX = 0;
	int positionY = 0;
	public boolean soundONOFF = true;
	int cellWidth = 0;
	int cellHeight = 0;
	int numId = 0;
	Boolean imagemove = false;
	Boolean successfulMove = true;
	Boolean isRandom = false;
	Context mContext;
	Paint mPaint;
	Background mBackground;
	private numbers[] numbers;// = new numbers[Levelseet.totalcount]; // array
								// that holds the numbers

	private String timeText = "00:00:00";
	private String movesText = "0";
	private PuzzelcropActivity mParent;
	public int movesCount = 0;
	int mTouchMode;
	static final int TOUCH_MODE_TAP = 1;
	static final int TOUCH_MODE_DOWN = 2;
	static final int TOUCH_MODE_REST = 3;

	private int mTouchSlop;
	private boolean isForceSwap = false;
	// MediaPlayer mpNumbers;
	MediaPlayer mpButtons;
	MediaPlayer mpGameFinished;
	MediaPlayer mbutton;
	public boolean isSoundON = true;
	int prevX, prevY, prevR, prevB;
	Rect prevRect;
	Typeface fontface;
	int position = 0;
	dragimage activity;
	private AtomicReference<Vibrator> vibratorRef = new AtomicReference<Vibrator>();

	playareaview(Context context, PuzzelcropActivity app) {
		super(context);
		mContext = context;

		soundONOFF = app.getSoundOnOFF();
		mPaint = new Paint();
		splitImage(Levelseet.totalcount);
		// set Color- blue
		// /// Log.e("level"+Levelseet.totalcount, "level"+Levelseet.row);
		mPaint.setColor(0xFFFFFFFF);
		mPaint.setTextSize(30);
		fontface = Typeface.createFromAsset(context.getAssets(),
				"fonts/HNHeavy.ttf");
		mPaint.setTypeface(fontface);
		mParent = app;
		// mpNumbers = MediaPlayer.create(context, R.raw.buttonclick);
		mpButtons = MediaPlayer.create(context, R.raw.right);
		mpGameFinished = MediaPlayer.create(context, R.raw.gameover);
		mbutton = MediaPlayer.create(context, R.raw.move);
		if (Levelseet.row == 3) {
			mParent.generateRandomNos(9);
		}
		if (Levelseet.row == 4) {
			mParent.generateRandomNos(16);
		}
		if (Levelseet.row == 5) {
			mParent.generateRandomNos(25);
		}
		numbers = new numbers[Levelseet.totalcount];
		for (int i = 0; i < Levelseet.totalcount; i++)
			numbers[i] = new numbers(context, i);

		mBackground = new Background(context, mPaint, this, numbers);
		mBackground.generateRandomNos();

		prevRect = new Rect();
		// ///////// mParent.generateRandomNos();
		// Log.e("scree"+Levelseet.SCREEN_WIDTH,
		// "dddd"+Levelseet.SCREEN_HEIGHT);
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop = configuration.getScaledTouchSlop();

		mBackground.getBackgroundImage(false);
		isSoundON = mParent.getSoundOnOFF();
		timeText = mParent.getTimeText();
		movesCount = mParent.getMovesCount();
		mParent.getNumberPositions(mBackground.randomNos);
	}

	public void splitImage(int chunkNumbers) {

		// For the number of rows and columns of the grid to be displayed
		int rows, cols;

		// For height and width of the small image chunks
		int chunkHeight, chunkWidth;

		// To store all the small image chunks in bitmap format in this list
		MainActivity.chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

		// Getting the scaled bitmap of the source image
		BitmapDrawable drawable = (BitmapDrawable) MainActivity.mImageView
				.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
				bitmap.getWidth(), bitmap.getHeight(), true);

		rows = cols = Levelseet.row;// (int) Math.sqrt(chunkNumbers);

		chunkHeight = bitmap.getHeight() / rows;
		chunkWidth = bitmap.getWidth() / cols;

		// xCoord and yCoord are the pixel positions of the image chunks
		int yCoord = 0;
		for (int x = 0; x < rows; x++) {
			int xCoord = 0;
			for (int y = 0; y < cols; y++) {
				MainActivity.chunkedImages.add(Bitmap.createBitmap(
						scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
				xCoord += chunkWidth;
			}
			yCoord += chunkHeight;
		}

	}

	public boolean isSoundEffectsEnabled() {
		return true;
	}

	public void updateNumberPositions() {
		int index = 0;
		System.out.println("numeber position size is "
				+ mBackground.numbersPos.size());
		for (int i = 0; i < Levelseet.totalcount; i++) {
			for (int j = 0; j < Levelseet.totalcount; j++) {

				if (mBackground.numbersPos.get(i).x == numbers[j].getX()
						&& mBackground.numbersPos.get(i).y == numbers[j].getY()) {
					mParent.updateNumberPositions(index, numbers[j].getID());
				}

			}
			index++;
		}
	}

	public void getMovesCount() {
		mParent.updateMovesCount(movesCount);
	}

	public void getTime() {
		mParent.updateTime(timeText);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		positionX = (int) event.getX();
		positionY = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			mTouchMode = TOUCH_MODE_DOWN;
			// ////////////////manu/////////////////////////
			if (positionX > mBackground.getStartButtonX()
					&& positionX < mBackground.getStartButtonX()
							+ mBackground.getbuttonmanuWidth()
					&& positionY > mBackground.getStartButtonY()
					&& positionY < mBackground.getStartButtonY()
							+ mBackground.getbuttonmanuHeight()) {
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				mParent.resetTimer();
				Intent intent = new Intent(mContext, PuzzleActivity.class);
				mContext.startActivity(intent);

				// invalidate();
				// mContext.finish();

			}
			// /////////reset/////////////////////////
			else if (positionX > mBackground.getStartresetButtonX()
					&& positionX < mBackground.getStartresetButtonX()
							+ mBackground.getbuttonresetWidth()
					&& positionY > mBackground.getStartresetButtonY()
					&& positionY < mBackground.getStartresetButtonY()
							+ mBackground.getbuttonresetHeight()) {
				mBackground.generateRandomNos();
				isRandom = true;
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				mParent.resetTimer();
				movesCount = 0;
				invalidate();
			}
			// /////////manuback/////////////////////////
			else if (positionX > mBackground.getmanybackButtonX()
					&& positionX < mBackground.getmanybackButtonX()
							+ mBackground.getbuttonmanybackWidth()
					&& positionY > mBackground.getmanybackButtonY()
					&& positionY < mBackground.getmanybackButtonY()
							+ mBackground.getbuttonmanybackHeight()) {
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				Intent intent = new Intent(mContext, MainActivity.class);
				mContext.startActivity(intent);
				mParent.resetTimer();
				movesCount = 0;
				// invalidate();
			}
			// /////////Easy/////////////////////////
			else if (positionX > mBackground.geteasyButtonX()
					&& positionX < mBackground.geteasyButtonX()
							+ mBackground.getbuttoneasyWidth()
					&& positionY > mBackground.geteasyButtonY()
					&& positionY < mBackground.geteasyButtonY()
							+ mBackground.getbuttoneasyHeight()) {
				// mainactivity.chunkedImages.clear();
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				invalidate();
				Levelseet.easy = true;
				Levelseet.normal = false;
				Levelseet.hard = false;
				Levelseet.row = 3;
				Levelseet.totalcount = 9;
				mParent.resetTimer();

				splitImage(9);
				numbers = new numbers[9];
				mParent.generateRandomNos(9);

				for (int i = 0; i < 9; i++)
					numbers[i] = new numbers(mContext, i);

				mBackground = new Background(mContext, mPaint, this, numbers);
				mBackground.generateRandomNos();

			}
			// /////////NORMAL/////////////////////////
			else if (positionX > mBackground.getnormalButtonX()
					&& positionX < mBackground.getnormalButtonX()
							+ mBackground.getbuttonnormalWidth()
					&& positionY > mBackground.getnormalButtonY()
					&& positionY < mBackground.getnormalButtonY()
							+ mBackground.getbuttonnormalHeight()) {
				// mainactivity.chunkedImages.clear();
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				invalidate();
				Levelseet.easy = false;
				Levelseet.normal = true;
				Levelseet.hard = false;
				Levelseet.row = 4;
				Levelseet.totalcount = 16;
				mParent.resetTimer();

				splitImage(16);
				numbers = new numbers[16];
				mParent.generateRandomNos(16);

				for (int i = 0; i < 16; i++)
					numbers[i] = new numbers(mContext, i);

				mBackground = new Background(mContext, mPaint, this, numbers);
				mBackground.generateRandomNos();

			}

			// /////////Hard/////////////////////////
			else if (positionX > mBackground.gethardButtonX()
					&& positionX < mBackground.gethardButtonX()
							+ mBackground.getbuttonhardWidth()
					&& positionY > mBackground.gethardButtonY()
					&& positionY < mBackground.gethardButtonY()
							+ mBackground.getbuttonhardHeight()) {
				// mainactivity.chunkedImages.clear();
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				invalidate();
				Levelseet.easy = false;
				Levelseet.normal = false;
				Levelseet.hard = true;
				Levelseet.row = 5;
				Levelseet.totalcount = 25;
				mParent.resetTimer();
				splitImage(25);
				numbers = new numbers[25];

				mParent.generateRandomNos(25);

				for (int i = 0; i < 25; i++)
					numbers[i] = new numbers(mContext, i);
				mBackground = new Background(mContext, mPaint, this, numbers);
				mBackground.generateRandomNos();

			}
			// ////////sound ///////////////////

			else if (positionX > mBackground.getsoundButtonX()
					&& positionX < mBackground.getsoundButtonX()
							+ mBackground.getbuttonsoundWidth()
					&& positionY > mBackground.getsoundButtonY()
					&& positionY < mBackground.getsoundButtonY()
							+ mBackground.getbuttonsoundHeight()) {
				if (soundONOFF == true) {
					soundONOFF = false;
					mParent.updateSoundOnOFF(soundONOFF);
					Levelseet.sound = false;
				} else {
					soundONOFF = true;
					mParent.updateSoundOnOFF(soundONOFF);
					Levelseet.sound = true;
				}
				isSoundON = soundONOFF;
				invalidate();
			}
			// /////////Icontouch/////////////////////////
			else if (positionX > mBackground.geticonButtonX()
					&& positionX < mBackground.geticonButtonX()
							+ mBackground.getbuttoniconWidth()
					&& positionY > mBackground.geticonButtonY()
					&& positionY < mBackground.geticonButtonY()
							+ mBackground.getbuttoniconHeight()) {
				// mainactivity.chunkedImages.clear();
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				Levelseet.icontouch = true;
				// Levelseet.texttouch = true;
				invalidate();
			}
			// /////////NUmvering/////////////////////////
			else if (positionX > mBackground.getnumberButtonX()
					&& positionX < mBackground.getnumberButtonX()
							+ mBackground.getbuttonnumberWidth()
					&& positionY > mBackground.getnumberButtonY()
					&& positionY < mBackground.getnumberButtonY()
							+ mBackground.getbuttonnumberHeight()) {
				// mainactivity.chunkedImages.clear();
				if (isSoundON) {
					if (mbutton != null)
						mbutton.start();
				}
				Levelseet.texttouch = true;
				invalidate();
			} else {

				if (checkForValidNumbers(positionX, positionY)) {

					setNumId(positionX, positionY);
					prevRect.left = numbers[numId].getX();
					prevRect.top = numbers[numId].getY();
					prevRect.right = numbers[numId].getRight();
					prevRect.bottom = numbers[numId].getBottom();
					prevX = positionX;
					prevY = positionY;
					// cellWidth = 25;
					cellWidth = numbers[Levelseet.totalcount - 1].getRight()
							- numbers[Levelseet.totalcount - 1].getX();
					cellHeight = numbers[Levelseet.totalcount - 1].getBottom()
							- numbers[Levelseet.totalcount - 1].getY();
					imagemove = true;
				} else {
					position = 0;
					imagemove = false;
				}
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {

			if (imagemove == true) {
				// Check if we have moved far enough that it looks more like a
				// scroll than a tap
				final int distY = Math.abs(positionY - prevY);
				final int distX = Math.abs(positionX - prevX);
				// Log.e("distX"+distX, "mTouchSlop"+mTouchSlop);
				if (distX > mTouchSlop || distY > mTouchSlop) {

					if (checkIsWithinBounds(positionX, positionY)) {
						isForceSwap = true;
						invalidate();
					} else {
						// Swap
						numbers[numId].setX(numbers[Levelseet.totalcount - 1]
								.getX());
						numbers[numId].setY(numbers[Levelseet.totalcount - 1]
								.getY());
						numbers[numId]
								.setRight(numbers[Levelseet.totalcount - 1]
										.getRight());
						numbers[numId]
								.setBottom(numbers[Levelseet.totalcount - 1]
										.getBottom());

						swapPositions(prevRect);
						isForceSwap = false;
						invalidate();

						if (mBackground.isGameFinished()) {
							if (mpGameFinished != null)
								mpGameFinished.start();
							mParent.pauseTimer();
							mParent.showSucessDialog();
							// mParent.updateMovesCount(0);
							movesCount = 0;
							mBackground.generateRandomNos();
							invalidate();
						}
						imagemove = false;
					}
				}

			}

		}
			break;
		case MotionEvent.ACTION_UP: {
			if (isForceSwap) {
				// Swap
				numbers[numId].setX(numbers[Levelseet.totalcount - 1].getX());
				numbers[numId].setY(numbers[Levelseet.totalcount - 1].getY());
				numbers[numId].setRight(numbers[Levelseet.totalcount - 1]
						.getRight());
				numbers[numId].setBottom(numbers[Levelseet.totalcount - 1]
						.getBottom());

				swapPositions(prevRect);
				isForceSwap = false;

				invalidate();

				if (mBackground.isGameFinished()) {
					mpGameFinished.start();
					mParent.pauseTimer();
					mParent.showSucessDialog();

					mBackground.generateRandomNos();
					movesCount = 0;
					// RevMobDevs.showFullscreenAd(activity, APPLICATION_ID);
					invalidate();
				}
				// else{
				// Vibrator v = vibratorRef.get();
				// if (v != null) {
				// v.vibrate(1110);
				// }
				// }
				imagemove = false;
				invalidate();
			}
			// if(positionX > 0 && positionX < Levelseet.SCREEN_WIDTH &&
			// positionY >(Levelseet.SCREEN_HEIGHT*15)/100 && positionY <=
			// ((Levelseet.SCREEN_HEIGHT*15)/100 + Levelseet.SCREEN_WIDTH ))
			// {
			// Vibrator v = vibratorRef.get();
			// if (v != null) {
			// v.vibrate(100);
			// }
			// }
			// }
			// // else {
			// // Vibrator v = vibratorRef.get();
			// // if (v != null) {
			// // v.vibrate(100);
			// // }
			// // }
			if (Levelseet.icontouch == true || Levelseet.texttouch == true) {
				Levelseet.icontouch = false;
				// Levelseet.texttouch = false;
				invalidate();
			}

		}
			break;
		}

		return true;
	}

	public void setVibrator(Vibrator v) {
		vibratorRef.set(v);
	}

	public boolean setNumId(int x, int y) {
		boolean validMove = false;

		for (numbers number : numbers) {
			if (x > number.getX() && x < number.getRight() && y > number.getY()
					&& y < number.getBottom()) {
				numId = number.getID();
				validMove = true;
				break;
			}
		}

		return validMove;
	}

	public boolean checkIsWithinBounds(int x, int y) {
		boolean isCanMove = false;
		int deltaX;
		int deltaY;
		switch (position) {
		case 1:

			if ((x - cellWidth - cellWidth / 2) > numbers[Levelseet.totalcount - 1]
					.getX()
					&& (x - cellWidth - cellWidth / 2) < numbers[Levelseet.totalcount - 1]
							.getRight()) {
				deltaX = Math.abs(prevX - positionX);
				numbers[numId].setX(numbers[numId].getX() - deltaX);
				numbers[numId].setY(numbers[Levelseet.totalcount - 1].getY());
				numbers[numId].setRight(numbers[numId].getX() + cellWidth);
				numbers[numId].setBottom((numbers[Levelseet.totalcount - 1]
						.getBottom()));
				prevX = positionX;
				isCanMove = true;
				return isCanMove;
			} else
				isCanMove = false;
			break;
		case 2:

			if ((x + cellWidth + cellWidth / 2) > numbers[Levelseet.totalcount - 1]
					.getX()
					&& (x + cellWidth + cellWidth / 2) < (numbers[Levelseet.totalcount - 1]
							.getRight())) {
				deltaX = Math.abs(prevX - positionX);
				numbers[numId].setX(numbers[numId].getX() + deltaX);
				numbers[numId].setY(numbers[Levelseet.totalcount - 1].getY());
				numbers[numId].setRight(numbers[numId].getX() + cellWidth);
				numbers[numId].setBottom((numbers[Levelseet.totalcount - 1]
						.getBottom()));
				prevX = positionX;
				isCanMove = true;
				return isCanMove;
			} else
				isCanMove = false;
			break;
		case 3:
			if ((y + cellHeight + cellHeight / 2) > numbers[Levelseet.totalcount - 1]
					.getY()
					&& (y + cellHeight + cellHeight / 2) < numbers[Levelseet.totalcount - 1]
							.getBottom()) {

				deltaY = Math.abs(prevY - positionY);
				numbers[numId].setX(numbers[Levelseet.totalcount - 1].getX());
				numbers[numId].setY(numbers[numId].getY() + deltaY);
				numbers[numId].setRight(numbers[Levelseet.totalcount - 1]
						.getRight());
				numbers[numId].setBottom(numbers[numId].getY() + cellHeight);
				prevY = positionY;
				isCanMove = true;
				return isCanMove;
			} else
				isCanMove = false;
			break;
		case 4:
			if ((y - cellHeight - cellHeight / 2) > numbers[Levelseet.totalcount - 1]
					.getY()
					&& (y - cellHeight - cellHeight / 2) < numbers[Levelseet.totalcount - 1]
							.getBottom()) {

				deltaY = Math.abs(prevY - positionY);
				numbers[numId].setX(numbers[Levelseet.totalcount - 1].getX());
				numbers[numId].setY(numbers[numId].getY() - deltaY);
				numbers[numId].setRight(numbers[Levelseet.totalcount - 1]
						.getRight());
				numbers[numId].setBottom(numbers[numId].getY() + cellHeight);
				prevY = positionY;
				isCanMove = true;
				return isCanMove;
			} else
				isCanMove = false;
			break;
		}

		return isCanMove;
	}

	public boolean checkForValidNumbers(int x, int y) {
		boolean validMove = false;
		int numberSquareX = numbers[Levelseet.totalcount - 1].getX();
		int numberSquareY = numbers[Levelseet.totalcount - 1].getY();
		cellWidth = numbers[Levelseet.totalcount - 1].getRight()
				- numbers[Levelseet.totalcount - 1].getX();
		cellHeight = numbers[Levelseet.totalcount - 1].getBottom()
				- numbers[Levelseet.totalcount - 1].getY();
		/* Check for right */
		if (x > numberSquareX + cellWidth + 3
				&& x < numberSquareX + 3 + cellWidth + cellWidth
				&& y > numberSquareY && y < numberSquareY + cellHeight) {
			position = 1;
			validMove = true;
		}
		/* Check for left */
		else if (x > numberSquareX - (3 + cellWidth) && x < numberSquareX - 3
				&& y > numberSquareY && y < numberSquareY + cellHeight) {
			position = 2;
			validMove = true;
		}
		/* Check for top */
		else if (y > numberSquareY - (3 + cellHeight) && y < numberSquareY - 3
				&& x > numberSquareX + 3 && x < numberSquareX + cellWidth) {
			position = 3;
			validMove = true;
		}
		/* Check for bottom */
		else if (y > numberSquareY + cellHeight + 3
				&& y < numberSquareY + 3 + cellHeight + cellHeight
				&& x > numberSquareX + 3 && x < numberSquareX + cellWidth) {
			position = 4;
			validMove = true;
		}
		return validMove;

	}

	public void swapPositions(Rect rect) {
		numbers[Levelseet.totalcount - 1].setX(rect.left);
		numbers[Levelseet.totalcount - 1].setY(rect.top);
		numbers[Levelseet.totalcount - 1].setRight(rect.right);
		numbers[Levelseet.totalcount - 1].setBottom(rect.bottom);
		if (isSoundON) {
			if (mpButtons != null)
				mpButtons.start();
		}
		mParent.startTimer();
		// mParent.showSucessDialog();
		movesCount++;
		mParent.updateMovesCount(movesCount);
		// if(positionX > 0 && positionX < Levelseet.SCREEN_WIDTH && positionY
		// >(Levelseet.SCREEN_HEIGHT*15)/100 && positionY <=
		// ((Levelseet.SCREEN_HEIGHT*15)/100 + Levelseet.SCREEN_WIDTH ))
		// {
		Levelseet.texttouch = false;
		Vibrator v = vibratorRef.get();
		if (v != null) {
			v.vibrate(50);
		}
		// }
	}

	public void setTimeText(String text) {
		timeText = text;
		invalidate();
	}

	public void setActivity(dragimage act) {
		activity = act;
	}

	@Override
	public void onDraw(Canvas canvas) {

		Bitmap bitmap = mBackground.getBackgroundImage(isRandom);
		isRandom = false;

		canvas.drawBitmap(bitmap, 0, 0, mPaint);
		// mBackground.drawbackwhereplayButton(canvas);
		mBackground.drawmenuButtons(canvas);
		mBackground.drawresetButtons(canvas);
		mBackground.drawmanybackButton(canvas);
		mBackground.draweasyButton(canvas);
		mBackground.drawnormalButton(canvas);
		mBackground.drawhardButton(canvas);

		if (Levelseet.icontouch == true) {
			mBackground.drawiconbackgroundimage(canvas);
		}
		mBackground.drawtimeButton(canvas);

		mBackground.drawiconButton(canvas);
		mBackground.drawnumberButton(canvas);

		mBackground.drawsoundButton(canvas);
		mBackground.drawTimeText(timeText, canvas);
		movesText = "" + movesCount;
		// mBackground.drawMovesText(movesText,canvas);
		bitmap.recycle();
	}

}
