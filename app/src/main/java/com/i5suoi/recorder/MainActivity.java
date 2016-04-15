package com.i5suoi.recorder;

import java.io.File;

import com.i5suoi.recorder.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button record;
	private ListView listView;
	private TextView empty;
	private RecordAdapter mAdapter;
	MusicEnergy energy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		record = (Button) this.findViewById(R.id.record);
		empty = (TextView) this.findViewById(R.id.empty);
		listView = (ListView) this.findViewById(R.id.listview);
		energy = (MusicEnergy) findViewById(R.id.music_energy);
		listView.setEmptyView(empty);
		record.setOnClickListener(this);
		
		U.createDirectory();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateData();
		energy.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.record:
			Intent it = new Intent(this,RecordActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
	}
	
	private void updateData() {
		File[] files = null;
		
		if(U.sdCardExists()) {
			File file = new File(U.DATA_DIRECTORY); 
			WavFileNameFilter filenameFilter = new WavFileNameFilter(".wav");
			files = file.listFiles(filenameFilter);
		}
		
		mAdapter = new RecordAdapter(this, files);
		listView.setAdapter(mAdapter);
	}

	@Override
	protected void onPause() {
		energy.onClose();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
}
