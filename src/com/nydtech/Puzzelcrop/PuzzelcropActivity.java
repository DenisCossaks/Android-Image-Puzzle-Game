package com.nydtech.Puzzelcrop;

import java.util.ArrayList;
import java.util.Random;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.preference.PreferenceManager;


public class PuzzelcropActivity extends Application {
	dragimage activity;
	private Handler mHandler = new Handler();
	long mStartTime = 0L;
	public String stopWatchTime = "00:00:00";
	public ArrayList<Integer> numberPositions;
	public ArrayList<User> userList;
	private int movesCount = 0;
	public boolean isTimerStarted = false;
	boolean isSounON = true;
	Random randomizer;
	int totSeconds = 0;
	int seconds = 0;
	int minutes = 0;
	int hours = 0;

	private static String APPLICATION_ID = "500005b0d3d4e000c7000304";
	congratulationsDialog customizeDialog;

	class User {
		public String nameText;
		public String timeText;
		public int movesCount;

		User(String name, String time, int count) {
			nameText = name;
			timeText = time;
			movesCount = count;
		}
	}

	@Override
	public void onCreate() {

		super.onCreate();
		numberPositions = new ArrayList<Integer>();
		userList = new ArrayList<User>();
		randomizer = new Random();
		// generateRandomNos();
		loadSettings();
	}

	public void onTerminate() {
		// numberPositions.clear();
		// generateRandomNos();
		super.onTerminate();

	}

	public void generateRandomNos(int a) {

		int number = Math.abs(randomizer.nextInt(a - 1));
		if (numberPositions.size() > 0)
			numberPositions.clear();
		for (int i = 0; i < a; i++) {
			while (isalreadyAdded(number) == false) {
				number = Math.abs(randomizer.nextInt(a));
			}
			numberPositions.add(number);
		}
	}

	public boolean isalreadyAdded(int number) {

		boolean numberFound = true;

		if (numberPositions.size() == 0) {
			return numberFound;
		} else {
			for (int i = 0; i < numberPositions.size(); i++) {
				if (numberPositions.get(i) == number) {
					numberFound = false;
					break;
				}
			}

		}
		return numberFound;
	}

	public void showSucessDialog() {

//		RevMobDevs.showFullscreenAd(activity, APPLICATION_ID);
//		activity.showSucessDialog();
	}

	public void setActivity(dragimage act) {
		activity = act;
	}

	public void updateNumberPositions(int index, int id) {
		numberPositions.set(index, id);
	}

	public void getNumberPositions(ArrayList<Integer> numPosList) {
		for (int i = 0; i < numberPositions.size(); i++)
			numPosList.set(i, numberPositions.get(i));
	}

	public void updateMovesCount(int count) {
		movesCount = count;
	}

	public void updateSoundOnOFF(boolean sound) {
		isSounON = sound;
	}

	public void updateTime(String time) {
		stopWatchTime = time;
	}

	public String getTimeText() {
		return stopWatchTime;
	}

	public boolean getSoundOnOFF() {
		return isSounON;
	}

	public int getMovesCount() {
		return movesCount;
	}

	public void startTimer() {
		if (mStartTime == 0L) {
			isTimerStarted = true;
			mStartTime = System.currentTimeMillis();
			if (stopWatchTime.contains("00:00:00") == false) {
				String time[] = stopWatchTime.trim().split(":");
				minutes = Integer.parseInt(time[1]);
				hours = Integer.parseInt(time[0]);
				totSeconds = (hours * 60 * 60) + (minutes * 60)
						+ Integer.parseInt(time[2]);
			}
			mHandler.removeCallbacks(mUpdateTimeTask);
			mHandler.postDelayed(mUpdateTimeTask, 100);
		}
	}

	String time = "";
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {

			final long start = mStartTime;
			long millis = System.currentTimeMillis() - start;
			seconds = totSeconds + (int) (millis / 1000);

			minutes = seconds / 60;
			hours = minutes / 60;
			seconds = seconds % 60;

			if (hours < 10) {
				time = " 0" + hours;
			} else
				time = time + hours;

			if (minutes < 10) {
				time = time + ":" + "0" + minutes;
			} else
				time = time + ":" + minutes;

			if (seconds < 10) {
				time = time + ":" + "0" + seconds;
			} else
				time = time + ":" + seconds;

			stopWatchTime = time;
			activity.setTimeText(stopWatchTime);
			mHandler.postDelayed(this, 1000);
		}
	};

	public void resetTimer() {
		mStartTime = 0L;
		isTimerStarted = false;
		stopWatchTime = "00:00:00";
		totSeconds = 0;
		activity.setTimeText(stopWatchTime);
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	public void stopTimer() {
		mStartTime = 0L;
		isTimerStarted = false;
		stopWatchTime = "00:00:00";
		activity.setTimeText(stopWatchTime);
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	public void pauseTimer() {
		mStartTime = 0L;
		isTimerStarted = false;
		stopWatchTime = time;
		activity.setTimeText(stopWatchTime);
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	public void updateUserList(String name) {

		boolean isWithinRange = true;
		if (userList.size() == 20) {
			isWithinRange = false;
		}

		User user = new User(name, stopWatchTime, movesCount);
		int maxPosition = -1;

		if (userList.size() == 0)
			userList.add(user);
		else {
			for (int i = 0; i < userList.size(); i++) {
				if (movesCount < userList.get(i).movesCount) {
					maxPosition = i;
					break;
				}
			}
			if (maxPosition == -1)
				maxPosition = userList.size();

			String namee;
			String time;
			int count;

			if (isWithinRange == false) {
				User swapUser;

				// Swap the position and remove the last element.
				for (int i = maxPosition; i < userList.size(); i++) {
					swapUser = userList.get(i);
					userList.set(i, user);
					user = swapUser;
				}
			} else if (maxPosition < userList.size()) {
				namee = userList.get(maxPosition).nameText;
				time = userList.get(maxPosition).timeText;
				count = userList.get(maxPosition).movesCount;
				userList.add(maxPosition, user);
			} else {
				userList.add(user);
			}
		}

	}

	public void saveSettings() {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = null;
		;
		editor = prefs.edit();
		int count = userList.size();
		editor.putInt("Count", count);

		for (int i = 0; i < count; i++) {
			User data = userList.get(i);

			if (count != 0) {
				// TODO:
				editor.putString("Name" + i, data.nameText);
				editor.putString("Time" + i, data.timeText);
				editor.putInt("Movescount" + i, data.movesCount);
			}
		}

		editor.commit();
		userList.clear();
	}

	public boolean loadSettings() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = null;
		;
		int count = prefs.getInt("Count", 0);
		if (count == 0) {

			return false;
		}

		for (int i = 0; i < count; i++) {
			String name = prefs.getString("Name" + i, "");
			String time = prefs.getString("Time" + i, "");
			int movescount = prefs.getInt("Movescount" + i, 0);

			User user = new User(name, time, movescount);
			userList.add(user);

		}
		return true;
	}

}
