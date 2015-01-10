package com.fowlcorp.homebank4android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class DropBoxFileActivity extends Activity {

	static final int REQUEST_LINK_TO_DBX = 0;


	private ArrayList<String> pathList;
	private RadioGroup radioGroup;

	private String currentPath;
	private ArrayAdapter<String> adapter;

	private ListView listView;

	private SharedPreferences sharedPreferences; //preferences of the app


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drop_box_file);


		listView = (ListView) findViewById(R.id.dropPathList);

		final Activity thisActivity=this;


		pathList = new ArrayList<String>();
		currentPath = "/";
		File file = new File(currentPath);
		File[] listFile = file.listFiles();
		for(int i=0;i<listFile.length;i++){
			pathList.add(listFile[i].getAbsolutePath());
			System.out.println(pathList.get(i));
		}
		adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout, android.R.id.text1,pathList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position !=0){
					File newFile = new File(pathList.get(position));
					currentPath = newFile.getAbsolutePath();
					if((newFile.isFile())){
						Intent resultData = new Intent();
						resultData.putExtra("pathResult", currentPath);
						setResult(Activity.RESULT_OK, resultData);
						finish();
					} else {
						//currentPath = newCurrent;
						File[] newListFile = newFile.listFiles();
						pathList.clear();
						pathList.add("...");
						for(int i=0;i<newListFile.length;i++){
							pathList.add(newListFile[i].getAbsolutePath());
						}
						adapter.notifyDataSetChanged();
					}
				} else {
					try {
						File newFile = new File(currentPath).getParentFile();
						currentPath = newFile.getAbsolutePath();
						File[] newListFile = newFile.listFiles();
						pathList.clear();
						pathList.add("...");
						for(int i=0;i<newListFile.length;i++){
							pathList.add(newListFile[i].getName());
						}
						adapter.notifyDataSetChanged();
					} catch (Exception e) {
					}
				}
			} 
		});




	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { //called when an activity whith result ends
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) { //if the dropbox link activity end correctly
			} else {
			}
		} 

		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drop_box_file, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
