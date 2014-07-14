package com.expenseManager.gestionespese.Utility;


import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;

public class PartitaDoppiaRow {

	public PartitaDoppiaRow(Context context,DbAdapter dbHelper,Cursor cursor,TableLayout tabella,int cont,TextView data1,TextView categoria1,TextView descrizione1,TextView entrata1,TextView uscita1,TextView conto1)
	{
		while(cursor.moveToNext())
		{
		Log.v("Cursor",cursor.toString());
		TableRow row=new TableRow(context);
		TableLayout.LayoutParams rowParams=new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		rowParams.bottomMargin=10;
		
		
		row.setLayoutParams(rowParams);
		Color color=new Color();
		color.rgb(99, 11, 99);
		row.setBackgroundColor(color.hashCode());
		TableRow.LayoutParams smallParams=null;
		TableRow.LayoutParams extraParams=null;
		TextView data=new TextView(context);
		TextView categoria=new TextView(context);
		TextView descrizione=new TextView(context);
		TextView conto=new TextView(context);
		TextView entrata=new TextView(context);
		TextView uscita=new TextView(context);
		
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT)
		{
		smallParams=new TableRow.LayoutParams(108,LayoutParams.MATCH_PARENT);
		extraParams=new TableRow.LayoutParams(144,LayoutParams.MATCH_PARENT);
		data.setTextSize(TypedValue.COMPLEX_UNIT_PT, 4);
		categoria.setTextSize(TypedValue.COMPLEX_UNIT_PT, 4);
		descrizione.setTextSize(TypedValue.COMPLEX_UNIT_PT, 4);
		conto.setTextSize(TypedValue.COMPLEX_UNIT_PT, 4);
		entrata.setTextSize(TypedValue.COMPLEX_UNIT_PT, 4);
		uscita.setTextSize(TypedValue.COMPLEX_UNIT_PT, 4);
		}
		else
		{
			smallParams=new TableRow.LayoutParams(192,LayoutParams.MATCH_PARENT);
			extraParams=new TableRow.LayoutParams(256,LayoutParams.MATCH_PARENT);
			data.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			categoria.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			descrizione.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			conto.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			entrata.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			uscita.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			data1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			categoria1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			descrizione1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			conto1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			entrata1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
			uscita1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
		}
		
		
		data.setGravity(Gravity.CENTER);
		categoria.setGravity(Gravity.CENTER);
		descrizione.setGravity(Gravity.CENTER);
		conto.setGravity(Gravity.CENTER);
		entrata.setGravity(Gravity.CENTER);
		uscita.setGravity(Gravity.CENTER);
		
		
		
		data.setTextColor(context.getResources().getColor(R.color.white));
		categoria.setTextColor(context.getResources().getColor(R.color.white));
		descrizione.setTextColor(context.getResources().getColor(R.color.white));
		conto.setTextColor(context.getResources().getColor(R.color.white));
		entrata.setTextColor(context.getResources().getColor(R.color.white));
		uscita.setTextColor(context.getResources().getColor(R.color.white));
		
		data.setBackgroundColor(context.getResources().getColor(R.color.report));
		categoria.setBackgroundColor(context.getResources().getColor(R.color.report));
		descrizione.setBackgroundColor(context.getResources().getColor(R.color.report));
		conto.setBackgroundColor(context.getResources().getColor(R.color.report));
		entrata.setBackgroundColor(context.getResources().getColor(R.color.entrata));
		uscita.setBackgroundColor(context.getResources().getColor(R.color.spesa));
		
		data.setLayoutParams(smallParams);
		categoria.setLayoutParams(smallParams);
		descrizione.setLayoutParams(extraParams);
		conto.setLayoutParams(extraParams);
		entrata.setLayoutParams(smallParams);
		uscita.setLayoutParams(smallParams);
		data1.setLayoutParams(smallParams);
		categoria1.setLayoutParams(smallParams);
		descrizione1.setLayoutParams(extraParams);
		conto1.setLayoutParams(extraParams);
		entrata1.setLayoutParams(smallParams);
		uscita1.setLayoutParams(smallParams);
		Log.v("Cursor count",""+cursor.getCount());
		Log.v("data",cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA)));
        int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAID)));
		data.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA)));
		int cat_id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
	    int conto_id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON)));
		if(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATATAB)).equalsIgnoreCase("entrata")==true)
	    {
		double importo=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
	    entrata.setText("€ "+importo);
	     Cursor cursor2=dbHelper.fetchCat(cat_id);
	     while(cursor2.moveToNext())
	     {
	    	 categoria.setText(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME)));
	     }
	     cursor2.close();
	    }
	    else
	    {
	    	double importo=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
		    uscita.setText("€ "+importo);
		    Cursor cursor2=dbHelper.fetchCatUs(cat_id);
		     while(cursor2.moveToNext())
		     {
		    	 categoria.setText(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME)));
		     }
		     cursor2.close();
	    }
		descrizione.setText(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADESC)));
		Cursor cursor1=dbHelper.fetchConto(conto_id);
	    while(cursor1.moveToNext())
	    {
	      conto.setText(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CONTONOM)));	
	    }
	    cursor1.close();
	    row.addView(data);
	    row.addView(categoria);
	    row.addView(descrizione);
	    row.addView(conto);
	    row.addView(entrata);
	    row.addView(uscita);
	    tabella.addView(row);
	    cont++;
	    Log.v("Dati["+cont+"]","ID:"+id+"Data:"+data.getText().toString()+" Categoria:"+categoria.getText().toString()+" Descrizione:"+descrizione.getText().toString()+" Conto:"+conto.getText().toString()+" Entrata:"+entrata.getText().toString()+" Uscita:"+uscita.getText().toString());
	}
	}
}
