package com.example.ndtwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
///////////���۵��ڸ��� ����ǰ��ϴ°� ///////////////////
public class NdServiceReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			context.startService(new Intent(context,NdService.class));
		}
	}

}
