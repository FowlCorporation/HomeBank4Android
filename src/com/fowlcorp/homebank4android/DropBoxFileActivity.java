package com.fowlcorp.homebank4android;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
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
	
	final static private String APP_KEY = "6a6wdjwu9kuhwzz";
	final static private String APP_SECRET = "ne5ries25tyj83s";
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;
	
	private DropboxAPI<AndroidAuthSession> mDBApi;
	
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
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		
		final Activity thisActivity=this;

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.radio_drop){
					try {
						AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
						AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
						mDBApi = new DropboxAPI<AndroidAuthSession>(session);
						mDBApi.getSession().startOAuth2Authentication(DropBoxFileActivity.this);
						AsyncTask<String, String, ArrayList<String>> listingThread = new AsyncTask<String, String, ArrayList<String>>() {

							@Override
							protected ArrayList<String> doInBackground(
									String... path) {
								ArrayList<String> list = new ArrayList<String>();
								Entry existingEntry = mDBApi.metadata(path, 0, null, true, null);
								existingEntry.
								return list;
							}

						};
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
							File newFile = new File(pathList.get(position));
							String newCurrent = newFile.getAbsolutePath();
							System.out.println("file is : "+newCurrent);
							if((newFile.isFile())){
								Intent resultData = new Intent();
								resultData.putExtra("pathResult", newCurrent);
								resultData.putExtra("isDropPath", false);
								setResult(Activity.RESULT_OK, resultData);
								finish();
							} else {
								//currentPath = newCurrent;
								File[] newListFile = newFile.listFiles();
								System.out.println("number of file : "+newListFile.length);
								pathList.clear();
								for(int i=0;i<newListFile.length;i++){
									pathList.add(newListFile[i].getAbsolutePath());
								}
								adapter.notifyDataSetChanged();
							}
						}
					});


				}
			}
		});



	}
	
	protected void onResume() {
	    super.onResume();

	    if (mDBApi.getSession().authenticationSuccessful()) {
	        try {
	            // Required to complete auth, sets the access token on the session
	            mDBApi.getSession().finishAuthentication();
	            String accessToken = mDBApi.getSession().getOAuth2AccessToken();
	            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	            sharedPreferences.edit().putString("token", accessToken).commit();
	        } catch (IllegalStateException e) {
	        }
	    }
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
