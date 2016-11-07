package com.example.ndtwo;

import java.util.ArrayList;
import java.util.List;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;
//????¥ê?//////////
public class NdService extends Service{
	public PackageManager pm;
	public ActivityManager am1;
	private boolean RunSW;
	private int cnt;
	public List<ResolveInfo> app;
	public ApplicationInfo appInfo;
	ArrayList<Info> mInfo;
	
	private DBAdapter mDb;
	
	String newValue;
	int i,c;
	String oldValue;
	
	public void onCreate(){
		
		mDb = new DBAdapter(this);
		oldValue="com.sec.android.app.launcher";
		newValue="com.sec.android.app.launcher";

		am1 =(ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
	}
	
	public void onStart(Intent intent, int startId){
		super.onStart(intent, startId);
		RunSW = true;
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while(RunSW){					
					List<ActivityManager.RunningTaskInfo> appList2 = am1.getRunningTasks(1);
					ComponentName cn = appList2.get(0).topActivity;

						i=0;
						newValue=cn.getPackageName();
						
						if(oldValue.equals(newValue)){
						
	
						}
						else{
							oldValue=newValue;
							mInfo = mDb.getAllInfo();
							while(i<mInfo.size())
							{
								if(mInfo.get(i).getApplicationname().equals(newValue))
								{
									if(mInfo.get(i).getApplicationname().equals("com.example.ndtwo") || mInfo.get(i).getApplicationname().equals("com.sec.android.app.launcher")){
	
									}
									else{
										
										Info tempinfo=mInfo.get(i);//////////////

										mDb.updateInfo(tempinfo);
								        
									}
								}
								i++;
							}
						}
							
					try{
						Thread.sleep(100); //0.01????? ????
					}
					catch(InterruptedException e)
					{
						
					} 
				}
			}
		});
		thread.start();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
