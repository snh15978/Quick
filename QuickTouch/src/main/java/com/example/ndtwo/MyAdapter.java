package com.example.ndtwo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Info> arrData;
	private List<ResolveInfo> info;
	private LayoutInflater inflater;
	Drawable dw = null;
	String pkgname = null;
	public MyAdapter(Context c, ArrayList<Info> arr, List<ResolveInfo> info){
		this.context = c;
		this.arrData = arr;
		this.info = info;
		inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return arrData.size();
	}

	@Override
	public Object getItem(int position) {
		return arrData.get(position).getName();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_layout, parent, false);
		}
		
		ImageView image = (ImageView)convertView.findViewById(R.id.image);
		try {
			dw= context.getPackageManager().getApplicationIcon((arrData.get(position)).getApplicationname());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		image.setImageDrawable(dw);
		TextView name = (TextView)convertView.findViewById(R.id.name);
		name.setText(arrData.get(position).getName());
  
		TextView cnt = (TextView)convertView.findViewById(R.id.cnt);
		cnt.setText("running repeat  : " +arrData.get(position).getTime());
  
  return convertView;
 }

}
