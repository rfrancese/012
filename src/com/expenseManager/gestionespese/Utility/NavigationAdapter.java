package com.expenseManager.gestionespese.Utility;

import com.expenseManager.gestionespese.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NavigationAdapter extends android.widget.ArrayAdapter<String> {
    private ArrayAdapter<String> string;
	public NavigationAdapter(Context context,ArrayAdapter<String> string) {
		super(context, 0);
		this.string=string;
		// TODO Auto-generated constructor stub
	}
	
	public View getView(int position,View convertView,ViewGroup parent)
	{
		if(convertView==null)
		{
			convertView=LayoutInflater.from(getContext()).inflate(R.layout.my_menu, null);
		}
		/*TextView text=(TextView)convertView.findViewById(R.id.text1);
		text.setText(string.getItem(position));
		Log.v("Item",string.getItem(position));
		Log.v("position",""+position);*/
		return convertView;
	}

}
