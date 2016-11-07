package com.example.ndtwo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//////////메인//////////////////
public class MainActivity extends Activity{
	
//	private NdService ndservice;
	
	private ArrayList<Info> Sort;
	public DBAdapter mDb;
	public List<ResolveInfo> app;
	public ApplicationInfo appInfo;
	public PackageManager pm;
	
	private LinearLayout list1,list2,list3;
	private ImageView image1,image2,image3;
	private TextView text1, text2, text3;
	private Drawable dw1,dw2,dw3;
	private int cnt;
	boolean TF;
	
	int max=0;
	int maxid;
	int x=0;
	int temps=0;
	private String nowdelete;

    String packagename="dummy";
    int iddummy;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mDb = new DBAdapter(this);
		pm = this.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		app = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
		cnt=app.size();
		appInfo = new ApplicationInfo();
		Sort = mDb.getAllInfo();
		
		
		
		//제일 처음 DB생성 or 추가된 어플이 있을경우
		if(Sort.size()==0 || Sort.size()<cnt)
		{
		for(int a=0; a<cnt ; a++){
			appInfo = new ApplicationInfo();
			appInfo.setPackageName(app.get(a).activityInfo.applicationInfo.packageName);
			appInfo.setAppName(app.get(a).activityInfo.applicationInfo.loadLabel(pm)+"");
			appInfo.setIcon(app.get(a).activityInfo.applicationInfo.loadIcon(pm));
			
			if(mDb.selectsame(appInfo.getPackageName())==false)
			{
				
				mDb.insertInfo(appInfo.getPackageName(), appInfo.getAppName(), 0);
			}
			
			}
		}
		

	
		
		Log.i("TAGa","static " +String.valueOf(cnt));	
		

		startService(new Intent(this,NdService.class));
		setContentView(R.layout.activity_main);	

		Sort = mDb.Sorting();

		image1 = (ImageView)findViewById(R.id.image0);
		try {
			dw1= getPackageManager().getApplicationIcon((Sort.get(0)).getApplicationname());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		image1.setImageDrawable(dw1);
		
		image2 = (ImageView)findViewById(R.id.image1);
		try {
			dw2= getPackageManager().getApplicationIcon((Sort.get(1)).getApplicationname());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		image2.setImageDrawable(dw2);	
		
		image3 = (ImageView)findViewById(R.id.image2);
		try {
			dw3= getPackageManager().getApplicationIcon((Sort.get(2)).getApplicationname());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		image3.setImageDrawable(dw3);
		
		
		text1 = (TextView)findViewById(R.id.name0);
		text1.setText(Sort.get(0).getName());
		
		text2 = (TextView)findViewById(R.id.name1);
		text2.setText(Sort.get(1).getName());
		
		text3 = (TextView)findViewById(R.id.name2);
		text3.setText(Sort.get(2).getName());
		
		
		list1 = (LinearLayout)findViewById(R.id.list1);
		list1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				packagename=Sort.get(0).getApplicationname();
				iddummy=Sort.get(0).getId();
				Log.i("lname", packagename);
				startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(packagename)));				
			}
		});
		list2 = (LinearLayout)findViewById(R.id.list2);
		list2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				packagename=Sort.get(1).getApplicationname();
				iddummy=Sort.get(1).getId();
				Log.i("lname", packagename);
				startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(packagename)));				
			}
		});		
		list3 = (LinearLayout)findViewById(R.id.list3);
		list3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				packagename=Sort.get(2).getApplicationname();
				iddummy=Sort.get(2).getId();
				Log.i("lname", packagename);
				startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(packagename)));				
			}
		});
		Button btn=(Button)findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent=new Intent(MainActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
}
