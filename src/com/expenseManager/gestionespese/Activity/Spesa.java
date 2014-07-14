package com.expenseManager.gestionespese.Activity;

import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;
import com.expenseManager.gestionespese.Utility.ListaEntrate;
import com.expenseManager.gestionespese.Utility.ListaSpesa;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Spesa extends Activity {
    public PopupWindow popup,popup1;
    public ListaSpesa lista;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spesa);
		final Context context=this;
		final Activity act=this;
		final Button decrescente=(Button)findViewById(R.id.DataDec);
		decrescente.setTextColor(getResources().getColor(R.color.white));
		final Button crescente=(Button)findViewById(R.id.DataCre);
		decrescente.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				crescente.setTextColor(getResources().getColor(R.color.grey));
				decrescente.setTextColor(getResources().getColor(R.color.white));
			}
		});
		ImageButton addSpesa=(ImageButton)findViewById(R.id.addSpesa);
		addSpesa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent addSpesa=new Intent(Spesa.this,AggiungiSpesa.class);
			startActivity(addSpesa);
			}
		});
		Calendar calendar=Calendar.getInstance();
		final TextView mese_anno = (TextView)findViewById(R.id.mese_anno);
		final String[] month=getResources().getStringArray(R.array.mese);
		final Calendario cal=new Calendario(mese_anno,calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),month);
		final DbAdapter dbAdapter=new DbAdapter(this);
		dbAdapter.open();
		Cursor cursor=dbAdapter.fetchOpUsData(cal.getData());
		final RelativeLayout layoutLista=(RelativeLayout)act.findViewById(R.id.listuccia);
		lista=new ListaSpesa(this, cursor,dbAdapter,layoutLista,cal, this, mese_anno);
		popup=lista.getPrimaFinestra();
		popup1=lista.getPopupCategoria();
		 crescente.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				crescente.setTextColor(getResources().getColor(R.color.white));
				decrescente.setTextColor(getResources().getColor(R.color.grey));
				Cursor cursor=dbAdapter.fetchSpesaDataDec(cal.getData());
				new ListaSpesa(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
				cursor.close();
				}
			});
	         decrescente.setOnClickListener(new View.OnClickListener() {
	 			
	 			@Override
	 			public void onClick(View v) {
	 				// TODO Auto-generated method stub
	 				crescente.setTextColor(getResources().getColor(R.color.grey));
	 				decrescente.setTextColor(getResources().getColor(R.color.white));
	 				Cursor cursor=dbAdapter.fetchOpUsData(cal.getData());
	 				new ListaSpesa(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
	 			}
	 		});
	         ImageButton next=(ImageButton)findViewById(R.id.next);
	         next.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.v("Click","Clickato");
					crescente.setTextColor(getResources().getColor(R.color.grey));
	 				decrescente.setTextColor(getResources().getColor(R.color.white));
					if(cal.getMonth()+1>11)
				{
					cal.setData(mese_anno, month, 0, cal.getYear()+1);
					
				}
				else
				{
					cal.setData(mese_anno, month, cal.getMonth()+1, cal.getYear());
					
				}
				Cursor cursor=dbAdapter.fetchOpUsData(cal.getData());
				new ListaSpesa(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
				Log.v("Data",cal.getData());
				}
			});
	         ImageButton back=(ImageButton)findViewById(R.id.back);
	         back.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					crescente.setTextColor(getResources().getColor(R.color.grey));
	 				decrescente.setTextColor(getResources().getColor(R.color.white));
					if(cal.getMonth()-1<0)
					{
						cal.setData(mese_anno, month, 11, cal.getYear()-1);
						
					}
					else
					{
						cal.setData(mese_anno, month, cal.getMonth()-1, cal.getYear());
						
					}
					Cursor cursor=dbAdapter.fetchOpUsData(cal.getData());
					new ListaSpesa(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
					Log.v("Data",cal.getData());
					}
				
			});
	         cursor.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spesa, menu);
		return true;
	}
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		{
			popup=lista.getPrimaFinestra();
			popup1=lista.getPopupCategoria();
			Log.v("Key Pressed in lista spesa","YES");
			if(popup!=null)
			{
			Log.v("Popup",""+popup.isShowing());
			
			int cont=3;
			if(popup.isShowing()==true)
			{ popup.dismiss(); return false;} 
			
			Log.v("Cont",""+cont);
			}
			else
				startActivity(new Intent(this,MainActivity.class));
		}
		return super.onKeyDown(keyCode, event);
	}

}
