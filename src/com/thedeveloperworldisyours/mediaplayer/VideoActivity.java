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

import com.thedeveloperworldisyours.mediaplayer.utils.Constants;

public class VideoActivity extends Activity implements SurfaceHolder.Callback,
		OnPreparedListener {

	private MediaPlayer mMediaPlayer;
	private SurfaceHolder mVidHolder;
	private SurfaceView mVidSurface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		mVidSurface = (SurfaceView) findViewById(R.id.surfView);
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
			Intent intent = new Intent(this, MainActivity.class);
	        startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		mMediaPlayer.start();
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
}
