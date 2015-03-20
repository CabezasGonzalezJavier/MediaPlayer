package com.thedeveloperworldisyours.mediaplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.thedeveloperworldisyours.mediaplayer.utils.Constants;
import com.thedeveloperworldisyours.mediaplayer.utils.Utils;

public class VideoActivity extends Activity implements SurfaceHolder.Callback,
		OnPreparedListener, OnClickListener {

	private MediaPlayer mMediaPlayer;
	private SurfaceHolder mVidHolder;
	private SurfaceView mVidSurface;
	private boolean mPlayBoolean = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		ImageButton playButton = (ImageButton) findViewById(R.id.activity_video_imageButton_play);

		playButton.setOnClickListener(this);

		mVidSurface = (SurfaceView) findViewById(R.id.activity_video_surfView);
		mVidHolder = mVidSurface.getHolder();
		mVidHolder.addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_song) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDisplay(mVidHolder);
			mMediaPlayer.setDataSource(Constants.VID_ADDRESS);
			mMediaPlayer.prepare();
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mMediaPlayer.stop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_video_imageButton_play:
			if (Utils.isOnline(VideoActivity.this)) {
				if (mPlayBoolean) {

					mMediaPlayer.start();
					mPlayBoolean = false;
				} else {
					mMediaPlayer.pause();
					mPlayBoolean = true;
				}
			} else {
				Toast.makeText(VideoActivity.this, R.string.check_connection,
						Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}
	}
}
