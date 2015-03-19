package com.thedeveloperworldisyours.mediaplayer;

import java.util.concurrent.TimeUnit;

import com.thedeveloperworldisyours.mediaplayer.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public TextView mSongName, mStartTimeField, mEndTimeField;
	private MediaPlayer mMediaPlayer;
	private double mStartTime = 0;
	private double mFinalTime = 0;
	private Handler mMyHandler = new Handler();;
	private int mForwardTime = 5000;
	private int mBackwardTime = 5000;
	private SeekBar mSeekbar;
	private ImageButton mPlayButton, mPauseButton;
	public static int mOneTimeOnly = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSongName = (TextView) findViewById(R.id.textView4);
		mStartTimeField = (TextView) findViewById(R.id.textView1);
		mEndTimeField = (TextView) findViewById(R.id.textView2);
		mSeekbar = (SeekBar) findViewById(R.id.seekBar1);
		mPlayButton = (ImageButton) findViewById(R.id.imageButton1);
		mPauseButton = (ImageButton) findViewById(R.id.imageButton2);
		mSongName.setText(Constants.NAME_SONG);
		mMediaPlayer = MediaPlayer.create(this, R.raw.song);
		mSeekbar.setClickable(false);
		mPauseButton.setEnabled(false);

	}

	public void play(View view) {
		Toast.makeText(getApplicationContext(),
				getString(R.string.playing_sound), Toast.LENGTH_SHORT).show();
		mMediaPlayer.start();
		mFinalTime = mMediaPlayer.getDuration();
		mStartTime = mMediaPlayer.getCurrentPosition();
		if (mOneTimeOnly == 0) {
			mSeekbar.setMax((int) mFinalTime);
			mOneTimeOnly = 1;
		}

		mEndTimeField.setText(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes((long) mFinalTime),
				TimeUnit.MILLISECONDS.toSeconds((long) mFinalTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes((long) mFinalTime))));
		mStartTimeField.setText(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes((long) mStartTime),
				TimeUnit.MILLISECONDS.toSeconds((long) mStartTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes((long) mStartTime))));
		mSeekbar.setProgress((int) mStartTime);
		mMyHandler.postDelayed(UpdateSongTime, 100);
		mPauseButton.setEnabled(true);
		mPlayButton.setEnabled(false);
	}

	private Runnable UpdateSongTime = new Runnable() {
		public void run() {
			mStartTime = mMediaPlayer.getCurrentPosition();
			mStartTimeField.setText(String.format(
					"%d min, %d sec",
					TimeUnit.MILLISECONDS.toMinutes((long) mStartTime),
					TimeUnit.MILLISECONDS.toSeconds((long) mStartTime)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes((long) mStartTime))));
			mSeekbar.setProgress((int) mStartTime);
			mMyHandler.postDelayed(this, 100);
		}
	};

	public void pause(View view) {
		Toast.makeText(getApplicationContext(),
				getString(R.string.pausing_sound), Toast.LENGTH_SHORT).show();

		mMediaPlayer.pause();
		mPauseButton.setEnabled(false);
		mPlayButton.setEnabled(true);
	}

	public void forward(View view) {
		int temp = (int) mStartTime;
		if ((temp + mForwardTime) <= mFinalTime) {
			mStartTime = mStartTime + mForwardTime;
			mMediaPlayer.seekTo((int) mStartTime);
		} else {
			Toast.makeText(getApplicationContext(),
					"Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
		}

	}

	public void rewind(View view) {
		int temp = (int) mStartTime;
		if ((temp - mBackwardTime) > 0) {
			mStartTime = mStartTime - mBackwardTime;
			mMediaPlayer.seekTo((int) mStartTime);
		} else {
			Toast.makeText(getApplicationContext(),
					"Cannot jump backward 5 seconds", Toast.LENGTH_SHORT)
					.show();
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_video) {
			Intent intent = new Intent(this, VideoActivity.class);
	        startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}