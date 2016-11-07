package com.example.ndtwo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	//패키지 매니저
	public PackageManager ppm;
	public List<ResolveInfo> tempapp;
	private int cnt;
	private ApplicationInfo appInfo;
	public DBAdapter mDb;
    private ArrayList<Info> mInfo;
    private MyAdapter mAdapter;
	int flat=0;
	int iddummy;
	int flat2=0;
	
	
   

    ListView listView;
    Button clear_btn;
    
    String packagename;
    String packagenamedummy="dummy";
    
    String ppackage="package:";
    
    
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
 
	    setContentView(R.layout.activity_setting);
	    
	    //패키지 메니저
	    ppm = this.getPackageManager();

	    listView=(ListView)findViewById(R.id.listView);

	    mDb = new DBAdapter(this);
	    mInfo = mDb.getAllInfo();
	    
	//	Sort.addAll(mDb.Sorting());
	    
	    clear_btn=(Button)findViewById(R.id.clear_btn);
	    clear_btn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//db 횟수 0으로 초기화
				for(int i=0; i<mInfo.size(); i++)
				{
					Info tempinfo = mInfo.get(i);
					mDb.timeclear(tempinfo);
				}
				refreshList();
				
			}
		});		
		
		

        mAdapter = new MyAdapter(this,mInfo,tempapp);
        listView.setAdapter(mAdapter);
        
       //리스트뷰 클릭시 인텐트 호출
        listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> a, View view, int position, long id) {

				Intent intent=new Intent(Intent.ACTION_DELETE);
	
				packagenamedummy=mInfo.get(position).getApplicationname();
				iddummy=mInfo.get(position).getId();
				packagename=ppackage.concat(packagenamedummy);
				intent.setData(Uri.parse(packagename));
			
				
				startActivity(intent);
			
			}
        });
	  //DB연동해서 listView에 뿌릴부분
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Toast.makeText(this, "resume!", 1000).show();
		refreshList();
	}

	private void refreshList() {
		
		Intent intent1 = new Intent(Intent.ACTION_MAIN, null);
		intent1.addCategory(Intent.CATEGORY_LAUNCHER);	
		tempapp = ppm.queryIntentActivities(intent1, PackageManager.GET_META_DATA);
		cnt=tempapp.size();
		
		Log.i("cnt", Integer.toString(cnt));
		Log.i("size", Integer.toString(mInfo.size()));
		
		for(int b=0; b<cnt ; b++){
			appInfo = new ApplicationInfo();
			appInfo.setPackageName(tempapp.get(b).activityInfo.applicationInfo.packageName);
			if(packagenamedummy.equals(appInfo.getPackageName())){
				flat=1;
			}
			
		}
		//같은값이 없는경우  
		if(flat!=1){
			mDb.deleteInfo(iddummy);
			flat=0;		
		}
		

        mInfo.clear();
        mInfo.addAll(mDb.getAllInfo());
        mAdapter.notifyDataSetInvalidated();
    }
}
