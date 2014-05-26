package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AggiungiEntrata extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aggiungi_entrata);
		ImageButton categoria=(ImageButton)findViewById(R.id.addCatEn);
		ImageButton conto=(ImageButton)findViewById(R.id.select_conto);
		final Context context=this;
		Display display=getWindowManager().getDefaultDisplay();
		Point size=new Point();
		display.getSize(size);
		int width=size.x;
		int height=size.y;
		final TextView text=(TextView)this.findViewById(R.id.selconto);
		 DbAdapter dbHelper; 
		 Cursor cursor;
		dbHelper = new DbAdapter(this);
        dbHelper.open();
        
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View child=inflate.inflate(R.layout.popup_conti, null);
			
	        cursor=dbHelper.fetchAllCont();
	        startManagingCursor(cursor);
	        boolean flag=false;
	        RelativeLayout below=(RelativeLayout)child.findViewById(R.id.conti12);
	        RelativeLayout scroll=(RelativeLayout)child.findViewById(R.id.conti2);
	        //ScrollView veroscroll=(ScrollView)child.findViewById(R.id.conti);
	        //TextView text1=(TextView)findViewById(R.id.cate_addspesa1);
	        ArrayList<Account.Conto> conticino=new ArrayList<Account.Conto>();
	        while(cursor.moveToNext())
	        {
	        	int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID)));
	        	String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM));
	        	String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
	        	float importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM)));
	        	float importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
	        	conticino.add(new Account.Conto(id,nome,tipo,importo,importo_att));
	        }
	        final PopupWindow popup=new PopupWindow(child,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	        final Account.Conto conto_scelto = null;
	        final Account.Conto conto_scelto1=new Account.Conto();
	        final popupConto ll=new popupConto(conticino, scroll, child.getContext(),child,below,text,popup,conto_scelto);
	        
	        popup.setHeight(height-100);
 		popup.setWidth(width-100);
	        stopManagingCursor(cursor);
	        cursor.close();
	       LayoutInflater inflate1=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View child1=inflate1.inflate(R.layout.activity_categoria, null);
			 DbAdapter dbHelper1; 
			 Cursor cursor1;
			dbHelper1 = new DbAdapter(this);
	        dbHelper1.open();
	        cursor1=dbHelper.fetchAllCatEn();
	        startManagingCursor(cursor1);
	        ArrayList<Account.Categoria> categorie=new ArrayList<Account.Categoria>();
	        
	        RelativeLayout layout=(RelativeLayout)child1.findViewById(R.id.Categorie);
	        Log.v("layout",layout.toString());
	        while(cursor1.moveToNext())
	        {
	          	int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATID)));
	            String nome=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATNOME));
	            int color=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_COLORID)));
	            int icon=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ICONID)));
	            
	            categorie.add(new Account.Categoria(id, icon, color, nome, null));
	        }
	    		cursor1.close();
	    		TextView text1=(TextView)findViewById(R.id.cate_addentrata);
	    		final PopupWindow popup1=new PopupWindow(child1,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	    		
	    		popup1.setHeight(height-100);
	    		popup1.setWidth(width-100);
		        popupCateEn cat=new popupCateEn(categorie, layout, child1.getContext(),child1,below,text1,popup1);
		categoria.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!popup1.isShowing())
				{
					popup1.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
				else
				popup1.dismiss();
			}
		});
		conto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!popup.isShowing())
				{
					popup.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
				else
				popup.dismiss();
				Log.v("Is Open",""+popup.isShowing());
				
			}
		});
		final Calendar data=Calendar.getInstance();
		TextView var=(TextView)findViewById(R.id.textData_addentrata);
		Calendario calendario=new Calendario(var,data.get(Calendar.MONTH),data.get(Calendar.YEAR),data.get(Calendar.DAY_OF_MONTH));
		ImageButton setData=(ImageButton)findViewById(R.id.entrata_data);
		setData.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    final DatePickerDialog setData=new DatePickerDialog(AggiungiEntrata.this,null,data.get(Calendar.YEAR),data.get(Calendar.MONTH),data.get(Calendar.DAY_OF_MONTH));
			    setData.setButton(DialogInterface.BUTTON_POSITIVE,"Imposta", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					DatePicker data=setData.getDatePicker();
					TextView txt_data=(TextView)findViewById(R.id.textData_addentrata);	
				    Calendario calendario=new Calendario(txt_data,data.getMonth(),data.getYear(),data.getDayOfMonth());
					}
				});
			    setData.setButton(DialogInterface.BUTTON_NEGATIVE,"Annulla", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					setData.dismiss();	
					}
				});
			    setData.show();
			    
			}
		});
		Button salva=(Button)findViewById(R.id.btn_salva);
		salva.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			if(!text.getText().toString().equalsIgnoreCase(getResources().getString(R.string.sel_conto_default)))
			{
				conto_scelto1.clone(ll.getConto());
				DbAdapter dbHelper = new DbAdapter(context);
		        dbHelper.open();
				float importo=100;
			int categoria=1;
			String data="05/12/2010";
			String nota="ciao";
			String descrizione="forse";
			dbHelper.createEntrata(importo, data, descrizione, categoria, conto_scelto1.getId());
			dbHelper.addEntrata(conto_scelto1.getId(), importo);
			}
			else
				Toast.makeText(context, "Scegli un conto", Toast.LENGTH_SHORT).show();
				//conto_scelto1.clone(ll.getConto());
				Log.v("Conto Scelto",conto_scelto1.toString());
			}
				
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aggiungi_entrata, menu);
		return true;
	}

}
