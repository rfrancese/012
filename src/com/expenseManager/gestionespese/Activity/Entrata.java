package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.Calendario;
import com.expenseManager.gestionespese.Utility.ListaEntrate;
import com.google.android.gms.maps.model.VisibleRegion;

import Account.Categoria;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Entrata extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entrata);
		final Context context=this;
		final Activity act=this;
		ImageButton addEntrata=(ImageButton)findViewById(R.id.addEntrata);
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
		addEntrata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent addEntrata=new Intent(Entrata.this,AggiungiEntrata.class);
			startActivity(addEntrata);
			}
		});
		
		//Selezionare i bottoni per spostarsi avanti e indietro nel tempo
		// if < mese-1 (if mese==0) anno-1 if > mese+1 (if mese==11) anno+1
		Calendar calendar=Calendar.getInstance();
		final TextView mese_anno = (TextView)findViewById(R.id.mese_anno);
		final String[] month=getResources().getStringArray(R.array.mese);
		final Calendario cal=new Calendario(mese_anno,calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),month);
		final DbAdapter dbAdapter=new DbAdapter(this);
		dbAdapter.open();
		Cursor cursor=dbAdapter.fetchData(cal.getData());
		final RelativeLayout layoutLista=(RelativeLayout)act.findViewById(R.id.listuccia);
		new ListaEntrate(this, cursor,dbAdapter,layoutLista,cal, this, mese_anno);
         crescente.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			crescente.setTextColor(getResources().getColor(R.color.white));
			decrescente.setTextColor(getResources().getColor(R.color.grey));
			Cursor cursor=dbAdapter.fetchDataDec(cal.getData());
			new ListaEntrate(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
			}
		});
         decrescente.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				crescente.setTextColor(getResources().getColor(R.color.grey));
 				decrescente.setTextColor(getResources().getColor(R.color.white));
 				Cursor cursor=dbAdapter.fetchData(cal.getData());
 				new ListaEntrate(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
 			}
 		});
		/*ArrayList<String> date=new ArrayList<String>();
		
		int cont=0;
		float importo_tot=0;
		while(cursor.moveToNext())
		{
		 cont++;
		
		 String data=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA));
		 date.add(data);
		}
		cursor.close();
		if(cont!=0)
		{
			RelativeLayout layoutLista=(RelativeLayout)findViewById(R.id.listuccia);
			TextView not_record=(TextView)findViewById(R.id.not_record);
			
			
			not_record.setVisibility(View.INVISIBLE);
			
			for(int k=0;k<date.size();k++)
			{
				ArrayList<Account.Entrata> entrate = new ArrayList<Account.Entrata>();
				cursor=dbAdapter.fetchOpEntrata(date.get(k));
				while(cursor.moveToNext())
				{
					int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAID)));
					 float importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
			         String data=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA));
			         String descrizione=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADESC));
			         int categoria_id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					 int conto_id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON)));
			         entrate.add(new Account.Entrata(id, importo, data, descrizione, categoria_id, conto_id));
				}
				cursor.close();
				RelativeLayout lista_date=new RelativeLayout(this);
				TextView data=new TextView(this);
				data.setTextColor(this.getApplicationContext().getResources().getColor(R.color.white));
				TextView imp_data=new TextView(this);
				RelativeLayout.LayoutParams listaParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams impData=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams datalay=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				lista_date.setId((k+1)*10000+1);
				listaParams.setMargins(0, 5, 0, 5);
				lista_date.setPadding(0, 5, 0, 5);
				lista_date.setBackgroundResource(R.color.black);
				Calendario calendario=new Calendario(data, date.get(k));
                imp_data.setText("€ 0.0");
                imp_data.setTextColor(getResources().getColor(R.color.white));
                datalay.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                impData.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                data.setLayoutParams(datalay);
                imp_data.setLayoutParams(impData);
                lista_date.addView(data);
                lista_date.addView(imp_data);
                
                RelativeLayout operazioni_lay=new RelativeLayout(this);
				RelativeLayout.LayoutParams op_params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				//operazioni_lay.setLayoutParams(op_params);
				operazioni_lay.setId((k+1)*10000+3);
				float value_att=0;
				for(int i=0;i<entrate.size();i++)
				{
					
					value_att+=entrate.get(i).getImporto();
					
					
					ArrayList<Categoria> categoria=new ArrayList<Categoria>();
					cursor=dbAdapter.fetchCat(entrate.get(i).categoria_id);
					while(cursor.moveToNext())
					{
						int _id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbAdapter.KEY_CATID)));
						int icon=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbAdapter.KEY_ICONID)));
						int color=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbAdapter.KEY_COLORID)));
						String nome=cursor.getString(cursor.getColumnIndex(dbAdapter.KEY_CATNOME));
						categoria.add(new Categoria(_id,icon,color,nome,null));
					}
					cursor.close();
					
					
					for(int j=0;j<categoria.size();j++)
					{
						RelativeLayout layout_cate=new RelativeLayout(this);
						RelativeLayout.LayoutParams cateParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
						layout_cate.setBackgroundResource(R.color.grey);
						layout_cate.setId((i+1)*10000+2);
						cateParams.setMargins(5, 5, 5, 0);
						
						ImageView image=new ImageView(this);
						RelativeLayout.LayoutParams imgparams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
						imgparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						image.setImageDrawable(categoria.get(j).getDrawableIcon(this));
						image.setBackgroundColor(categoria.get(j).getColor());
						image.setLayoutParams(imgparams);
						
						TextView nomecat=new TextView(this);
						RelativeLayout.LayoutParams txtparams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
						txtparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
						nomecat.setText(categoria.get(j).getNome());
						nomecat.setLayoutParams(txtparams);
						nomecat.setGravity(Gravity.CENTER);
						
						TextView importo=new TextView(this);
						RelativeLayout.LayoutParams impparams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
						impparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						importo.setText("€ "+entrate.get(i).getImporto());
						impparams.setMargins(0, 0, 10, 0);
						importo.setGravity(Gravity.CENTER);
						importo.setLayoutParams(impparams);	
						
						layout_cate.addView(image);
						layout_cate.addView(nomecat);
						layout_cate.addView(importo);
						
						
						if(i==0)
						{
						 operazioni_lay.addView(layout_cate);
						 layout_cate.setLayoutParams(cateParams);
						}
						else
						{
							cateParams.addRule(RelativeLayout.BELOW,i*10000+2);
							layout_cate.setLayoutParams(cateParams);
							operazioni_lay.addView(layout_cate);
						}
							
							
							
						//Log.v("Valori","i= "+i+" k="+k+" data_id= "+data.getId()+" layout2_id= "+layout2.getId());
					} // chiude j
					//lista_date.addView(operazioni_lay);
					importo_tot+=value_att;				
				} // chiude i
				imp_data.setText("€ "+value_att);
				if(k==0)
                {
                	lista_date.setLayoutParams(listaParams);
                	layoutLista.addView(lista_date);
                	op_params.addRule(RelativeLayout.BELOW,lista_date.getId());
                	operazioni_lay.setLayoutParams(op_params);
                	layoutLista.addView(operazioni_lay);
                }
                else
                {
                	listaParams.addRule(RelativeLayout.BELOW,k*10000+3);
                	lista_date.setLayoutParams(listaParams);
                	op_params.addRule(RelativeLayout.BELOW,lista_date.getId());
                	operazioni_lay.setLayoutParams(op_params);
                	layoutLista.addView(operazioni_lay);
                	layoutLista.addView(lista_date);
                }	
				
				//layoutLista.addView(lista_date);	
			} // chiude k
			String stringa=mese_anno.getText().toString()+" € "+importo_tot;
			mese_anno.setText(stringa);
			
			
		}*/
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
			Cursor cursor=dbAdapter.fetchData(cal.getData());
			new ListaEntrate(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
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
				Cursor cursor=dbAdapter.fetchData(cal.getData());
				new ListaEntrate(context, cursor,dbAdapter,layoutLista, cal, act, mese_anno);
				Log.v("Data",cal.getData());
				}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entrata, menu);
		return true;
	}

}
