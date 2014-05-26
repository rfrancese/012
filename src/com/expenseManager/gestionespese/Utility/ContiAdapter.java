package com.expenseManager.gestionespese.Utility;

import java.util.ArrayList;

import com.expenseManager.gestionespese.R;

import Account.Conto;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContiAdapter extends ArrayAdapter<Conto>{
	
	ArrayList<Conto> values;
	
   public ContiAdapter(Context ctx, int txtViewResourceId, ArrayList<Conto> objects,ArrayList<Conto> value)
   {
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
		subSpinner.setText(values.get(position).getNome());

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
		subSpinner.setText(values.get(pos).getNome());

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
		subSpinner.setText(values.get(position).getNome());

		ImageView left_icon = (ImageView) mySpinner
				.findViewById(R.id.icon);
		left_icon.setImageResource(R.drawable.account_icon);
		return mySpinner;
		
	}

	
	

}
