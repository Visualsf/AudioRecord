package com.i5suoi.recorder;

import java.io.File;

import com.i5suoi.recorder.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecordAdapter extends BaseAdapter {
	
	private Context mContext;
	private File[] mData;
	private LayoutInflater mInflater;
	private MediaPlayer mPlayer;
	
	public RecordAdapter(Context context, File[] data) {
		mContext = context;
		mData = data;
		mPlayer  =   new MediaPlayer();
		mInflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		return mData.length;
	}

	@Override
	public Object getItem(int arg0) {
		return mData[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.list_item, null);
		TextView fileName = (TextView) convertView.findViewById(R.id.fileName);
		TextView createtime = (TextView) convertView.findViewById(R.id.createtime);
		
		final File file = mData[position];
		fileName.setText(file.getName());
		createtime.setText(U.millis2CalendarString(file.lastModified(), "yyyy年MM月dd日 HH:mm:ss"));
		
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				play(file.getAbsolutePath());
			}
		});
		
		return convertView;
	}
	
	private void play(String path) {
        try {
        	mPlayer.reset();
			mPlayer.setDataSource(path);
			mPlayer.prepare();
	        mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
