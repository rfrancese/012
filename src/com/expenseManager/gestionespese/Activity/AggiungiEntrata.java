package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;

import Account.Categoria;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AggiungiEntrata extends Activity {
	DbAdapter dbHelper;
	int a=0;
	popupCateEn cat;
	Categoria categ=new Categoria();
	Context context=this;
	RelativeLayout layout;
	LayoutInflater inflate1;
	View child1;
	PopupWindow popup1,popup;
	Cursor cursor1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_aggiungi_entrata);
		Log.v("A=",""+a);
		ImageButton categoria=(ImageButton)findViewById(R.id.addCatEn);
		ImageButton conto=(ImageButton)findViewById(R.id.select_conto);
		Display display=getWindowManager().getDefaultDisplay();
		Point size=new Point();
		display.getSize(size);
		int width=size.x;
		int height=size.y;
		final TextView text=(TextView)this.findViewById(R.id.selconto);
		  
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
	        popup=new PopupWindow(child,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	        final Account.Conto conto_scelto = null;
	        final Account.Conto conto_scelto1=new Account.Conto();
	        final popupConto ll=new popupConto(conticino, scroll, child.getContext(),child,below,text,popup,conto_scelto);
	        
	        popup.setHeight(height-100);
 		popup.setWidth(width-100);
 		
	        stopManagingCursor(cursor);
	        cursor.close();
	       /*LayoutInflater inflate1=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View child1=inflate1.inflate(R.layout.activity_categoria, null);
			 DbAdapter dbHelper1; 
			 Cursor cursor1;
			dbHelper1 = new DbAdapter(this);
	        dbHelper1.open();
	        cursor1=dbHelper.fetchAllCatEn();*/
	        
	       /* ArrayList<Account.Categoria> categorie=new ArrayList<Account.Categoria>();
	        
	        layout=(RelativeLayout)child1.findViewById(R.id.Categorie);
	        
	    		TextView text1=(TextView)findViewById(R.id.cate_addentrata);
	    		popup1=new PopupWindow(child1,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	    		popup1.setHeight(height-100);
	    		popup1.setWidth(width-100);
	    		Categoria categ=new Categoria();
		        cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,below,text1,popup1,categ,this);*/
	        inflate1=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child1=inflate1.inflate(R.layout.activity_categoria, null);
			popup1=new PopupWindow(child1,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
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
				{
					popup.dismiss();
				}
				Log.v("Is Open",""+popup.isShowing());
				
			}
		});
		
		final Calendar data=Calendar.getInstance();
		TextView var=(TextView)findViewById(R.id.textData_addentrata);
		final Calendario calendario=new Calendario(var,data.get(Calendar.MONTH),data.get(Calendar.YEAR),data.get(Calendar.DAY_OF_MONTH));
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
				    calendario.updateData(txt_data,data.getMonth(),data.getYear(),data.getDayOfMonth());
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
				EditText value=(EditText)findViewById(R.id.importo);
			if(!value.getText().toString().equalsIgnoreCase(""))
			{
				if(!text.getText().toString().equalsIgnoreCase(getResources().getString(R.string.sel_conto_default)))
			{
				conto_scelto1.clone(ll.getConto());
				DbAdapter dbHelper = new DbAdapter(context);
		        dbHelper.open();
		        
				float importo=Float.parseFloat(value.getText().toString());
			int categoria=categ.getId();
			String data=calendario.getData();
			TextView description=(TextView)findViewById(R.id.descrizione);
			TextView note=(TextView)findViewById(R.id.note);
			String nota=note.getText().toString();
			String descrizione=description.getText().toString();
			dbHelper.createEntrata(importo, data, descrizione, categoria, conto_scelto1.getId(),nota,"Entrata");
			dbHelper.addEntrata(conto_scelto1.getId(), importo);
			Toast.makeText(context, "Entrata Aggiunta", Toast.LENGTH_SHORT).show();
			String stringa="["+importo+"]["+categ.getNome()+"]["+data+"]["+descrizione+"]["+conto_scelto1.getNome()+"]["+nota+"]";
			Toast.makeText(context, stringa, Toast.LENGTH_SHORT).show();
			}
			else
				{Toast.makeText(context, "Scegli il conto e/o la categoria", Toast.LENGTH_SHORT).show();
				//conto_scelto1.clone(ll.getConto());
				Log.v("Conto Scelto",conto_scelto1.toString());}
			}
			else
			{
				Toast.makeText(context, "Inserire un importo valido", Toast.LENGTH_SHORT).show();
			}
			}	
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aggiungi_entrata, menu);
		return true;
	}
	
	@Override
	public void onResume()
	{
		
		
		Log.v("popup1 prima di resume",""+popup1.isShowing());
		super.onResume();
		/*
		final Context context=this;
		Display display=getWindowManager().getDefaultDisplay();
		Point size=new Point();
		display.getSize(size);
		int width=size.x;
		int height=size.y;
		DbAdapter dbAdapter=new DbAdapter(this);
		dbAdapter.open();
		LayoutInflater inflate1=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View child1=inflate1.inflate(R.layout.activity_categoria, null);
		Cursor cursor1=dbAdapter.fetchAllCatEn();
		 RelativeLayout layout=(RelativeLayout)child1.findViewById(R.id.Categorie);
	        
 		TextView text1=(TextView)findViewById(R.id.cate_addentrata);
 		final PopupWindow popup1=new PopupWindow(child1,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
 		
 		popup1.setHeight(height-100);
 		popup1.setWidth(width-100);
 		Categoria categ=new Categoria();
		final popupCateEn cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,null,text1,popup1,categ,this);*/
		dbHelper.open();
		cursor1=dbHelper.fetchAllCatEn();
		Display display=getWindowManager().getDefaultDisplay();
		Point size=new Point();
		display.getSize(size);
		int width=size.x;
		int height=size.y; 
	
		layout=(RelativeLayout)child1.findViewById(R.id.Categorie);
	        
 		TextView text1=(TextView)findViewById(R.id.cate_addentrata);
		popup1.setHeight(height-100);
		popup1.setWidth(width-100);
 		cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,null,text1,popup1,categ,this,"Entrata");
		if(popup1.isShowing())
		{
        	layout.invalidate();
        	cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,null,text1,popup1,categ,this,"Entrata");
        	popup1.dismiss();
        	Log.v("popup1",""+popup1.isShowing());
			popup1.showAtLocation(this.getCurrentFocus(), Gravity.CENTER, 0, 0);
			Log.v("popup1",""+popup1.isShowing());
		} 
		Log.v("Is Open",""+popup1.isShowing());
		
	    // Remove the unique action so the next time onResume is called it will restart
	   
		Log.v("onresume","onresume");
		
 		
	       /* for(int i=0;i<cat.getArrayList().size();i++)
			{
				Log.v("onResume : I = "," "+i+" ArrayList : " +cat.getArrayList().get(i).getNome());
			}*/
	        
			/*
	        popup1.update();*/
	
		
	}
	
	public void onPause()
	{
		super.onPause();

		
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		{
			if(popup.isShowing()==true)
			{ popup.dismiss(); return false;} 
			else if(popup1.isShowing()==true)
				{popup1.dismiss();return false;}
			else if(popup1.isShowing()==false && popup.isShowing()==false);
		}
		return super.onKeyDown(keyCode, event);
	}

}
