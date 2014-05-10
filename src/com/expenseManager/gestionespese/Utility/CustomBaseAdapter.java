package com.expenseManager.gestionespese.Utility;

import java.util.List;

import com.expenseManager.gestionespese.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomBaseAdapter extends ArrayAdapter<String> {

	String[] values;
	
	
	public CustomBaseAdapter(Context ctx, int txtViewResourceId, String[] objects,String[] value) {
		super(ctx, txtViewResourceId, objects);
		values=value;
	}
    
	@Override
	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
		LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mySpinner = inflater.inflate(R.layout.list_item, prnt,
				false);
       
		
		TextView subSpinner = (TextView) mySpinner
				.findViewById(R.id.text);
		subSpinner.setText(values[position]);

		ImageView left_icon = (ImageView) mySpinner
				.findViewById(R.id.icon);
		left_icon.setImageResource(R.drawable.account_icon);
		return this.getView(position, mySpinner, prnt);
	}
	@Override
	public View getView(int pos, View cnvtView, ViewGroup prnt) {
		LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mySpinner = inflater.inflate(R.layout.list_item, prnt,
				false);
       
		
		TextView subSpinner = (TextView) mySpinner
				.findViewById(R.id.text);
		subSpinner.setText(values[pos]);

		ImageView left_icon = (ImageView) mySpinner
				.findViewById(R.id.icon);
		left_icon.setImageResource(R.drawable.account_icon);
		return mySpinner;
	}
	public View getCustomView(int position, View convertView,
			ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View mySpinner = inflater.inflate(R.layout.list_item, parent,
				false);
       
		
		final TextView subSpinner = (TextView) mySpinner
				.findViewById(R.id.text);
		subSpinner.setText(values[position]);

		ImageView left_icon = (ImageView) mySpinner
				.findViewById(R.id.icon);
		left_icon.setImageResource(R.drawable.account_icon);
		return mySpinner;
		
	}

	
	


}
