package com.fowlcorp.homebank4android;

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
import android.widget.Toast;

public class DropBoxFileActivity extends Activity {
	
	private DbxAccountManager dropBoxAccountMgr;
	private DbxFileSystem dbxFs;
	private ArrayList<String> pathList;

	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drop_box_file);
		
		dropBoxAccountMgr = DbxAccountManager.getInstance(getApplicationContext(), "40u2ttil28t3g8e", 	
				"sjt7o80sdtdjsxi");
		listView = (ListView) findViewById(R.id.dropPathList);
		
		try {
			final DbxFileSystem dbxFs = DbxFileSystem.forAccount(dropBoxAccountMgr.getLinkedAccount());
			final DbxPath current = DbxPath.ROOT;
		    List<DbxFileInfo> infos = dbxFs.listFolder(current);
			pathList = new ArrayList<String>();
			for(int i=0;i<infos.size();i++){
				pathList.add(infos.get(i).path.toString());
			}
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,pathList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					DbxPath newCurrent = new DbxPath(current.toString()+pathList.get(position));
					try {
						DbxFile file = dbxFs.open(newCurrent);
						System.out.println("file");
						SharedPreferences sharePreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						sharePreferences.edit().putString("dropPath", current.toString()+pathList.get(position));
						finish();
					} catch (DbxException e1) {
						try {
							List<DbxFileInfo> newInfos = dbxFs.listFolder(newCurrent);
							pathList.clear();
							for(int i=0;i<newInfos.size();i++){
								pathList.add(newInfos.get(i).path.toString());
							}
							adapter.notifyDataSetChanged();
						} catch (DbxException e) {
						}
					}
					
				}
			});
		} catch (Exception e) {
			//TODO
		}
		
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
