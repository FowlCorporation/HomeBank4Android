package com.fowlcorp.homebank4android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFileSystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

	private DbxAccountManager dropBoxAccountMgr;
	private DbxFileSystem dbxFs;
	private ArrayList<String> pathList;
	private RadioGroup radioGroup;
	
	private String currentPath;
	private ArrayAdapter<String> adapter;

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drop_box_file);

		
		listView = (ListView) findViewById(R.id.dropPathList);
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.radio_drop){
					try {
						dropBoxAccountMgr = DbxAccountManager.getInstance(getApplicationContext(), "40u2ttil28t3g8e", 	
								"sjt7o80sdtdjsxi");
						final DbxFileSystem dbxFs = DbxFileSystem.forAccount(dropBoxAccountMgr.getLinkedAccount());
						final DbxPath current = DbxPath.ROOT;
						List<DbxFileInfo> infos = dbxFs.listFolder(current);
						pathList = new ArrayList<String>();
						for(int i=0;i<infos.size();i++){
							pathList.add(infos.get(i).path.toString());
						}
						adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout, android.R.id.text1,pathList);
						listView.setAdapter(adapter);
						listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								DbxPath newCurrent = new DbxPath(current.toString()+pathList.get(position));
								try {
									if(dbxFs.isFile(newCurrent)){
										Intent resultData = new Intent();
										resultData.putExtra("pathResult", current.toString()+pathList.get(position));
										resultData.putExtra("isDropPath", true);
										setResult(Activity.RESULT_OK, resultData);
										dbxFs.shutDown();
										finish();
									} else {
										List<DbxFileInfo> newInfos = dbxFs.listFolder(newCurrent);
										pathList.clear();
										for(int i=0;i<newInfos.size();i++){
											pathList.add(newInfos.get(i).path.toString());
										}
										adapter.notifyDataSetChanged();
									}
								} catch (DbxException e1) {
								}
							}
						});
					} catch (Exception e) {
					}
				} else {
					pathList = new ArrayList<String>();
					currentPath = "/";
					File file = new File(currentPath);
					String[] listFile = file.list();
					for(int i=0;i<listFile.length;i++){
						pathList.add(listFile[i]);
						System.out.println(pathList.get(i));
					}
					adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.listview_layout, android.R.id.text1,pathList);
					listView.setAdapter(adapter);
					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							File newFile = new File(currentPath, pathList.get(position));
							String newCurrent = newFile.getAbsolutePath();
								if((newFile.isFile())){
									Intent resultData = new Intent();
									resultData.putExtra("pathResult", newCurrent);
									resultData.putExtra("isDropPath", false);
									setResult(Activity.RESULT_OK, resultData);
									finish();
								} else {
									//currentPath = newCurrent;
									String[] newListFile = newFile.list();
									System.out.println("number of file : "+newListFile.length);
									pathList.clear();
									for(int i=0;i<newListFile.length;i++){
										pathList.add(newListFile[i]);
									}
									adapter.notifyDataSetChanged();
								}
						}
					});

					
				}
			}
		});



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
