package com.expenseManager.gestionespese.Utility;

import java.util.ArrayList;

import Account.Categoria;
import Account.Conto;
import Account.Entrata;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;

public class ListaEntrate {

	public Categoria categ=null,categ1=null;
	public Account.Conto conto_scelto2=null;
	public ListaEntrate(final Context context,Cursor cursor,DbAdapter dbAdapter,RelativeLayout layoutLista,Calendario cal,final Activity act,TextView mese_anno)
	{
		
		ArrayList<String> date=new ArrayList<String>();
		
		int cont=0;
		float importo_tot=0;
		while(cursor.moveToNext())
		{
		 cont++;
		/* */
		 String data=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA));
		 date.add(data);
		}
		cursor.close();
		if(cont!=0)
		{
			
			TextView not_record=(TextView)act.findViewById(R.id.not_record);
			
			
			//not_record.setVisibility(View.INVISIBLE);
			layoutLista.removeAllViews();
			for(int k=0;k<date.size();k++)
			{
				ArrayList<Account.Entrata> entrate = new ArrayList<Account.Entrata>();
				Cursor cursor1=dbAdapter.fetchOpEntrata(date.get(k));
				while(cursor1.moveToNext())
				{
					int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ENTRATAID)));
					 float importo=Float.parseFloat(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
			         String data=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ENTRATADATA));
			         String descrizione=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ENTRATADESC));
			         int categoria_id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					 int conto_id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ENTRATACON)));
			         entrate.add(new Account.Entrata(id, importo, data, descrizione, categoria_id, conto_id));
				}
				cursor1.close();
				RelativeLayout lista_date=new RelativeLayout(context);
				TextView data=new TextView(context);
				data.setTextColor(context.getApplicationContext().getResources().getColor(R.color.white));
				TextView imp_data=new TextView(context);
				RelativeLayout.LayoutParams listaParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams impData=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams datalay=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				lista_date.setId((k+1)*10000+1);
				listaParams.setMargins(0, 5, 0, 5);
				lista_date.setPadding(0, 5, 0, 5);
				lista_date.setBackgroundResource(R.color.black);
				Calendario calendario=new Calendario(data, date.get(k));
                imp_data.setText("€ 0.0");
                imp_data.setTextColor(context.getResources().getColor(R.color.white));
                datalay.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                impData.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                data.setLayoutParams(datalay);
                imp_data.setLayoutParams(impData);
                
                lista_date.addView(data);
                lista_date.addView(imp_data);
                
                RelativeLayout operazioni_lay=new RelativeLayout(context);
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
						RelativeLayout layout_cate=new RelativeLayout(context);
						RelativeLayout.LayoutParams cateParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
						layout_cate.setBackgroundResource(R.color.grey);
						layout_cate.setId((i+1)*10000+2);
						cateParams.setMargins(5, 5, 5, 0);
						
						ImageView image=new ImageView(context);
						RelativeLayout.LayoutParams imgparams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
						imgparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
						image.setImageDrawable(categoria.get(j).getDrawableIcon(context));
						image.setBackgroundColor(categoria.get(j).getColor());
						image.setLayoutParams(imgparams);
						
						TextView nomecat=new TextView(context);
						RelativeLayout.LayoutParams txtparams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
						txtparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
						nomecat.setText(categoria.get(j).getNome());
						nomecat.setLayoutParams(txtparams);
						nomecat.setGravity(Gravity.CENTER);
						
						TextView importo=new TextView(context);
						RelativeLayout.LayoutParams impparams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
						impparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
						importo.setText("€ "+entrate.get(i).getImporto());
						impparams.setMargins(0, 0, 10, 0);
						importo.setGravity(Gravity.CENTER);
						importo.setLayoutParams(impparams);	
						
						layout_cate.addView(image);
						layout_cate.addView(nomecat);
						layout_cate.addView(importo);
						final Entrata entrata=entrate.get(i);
						layout_cate.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
							View child2=v;
							LayoutInflater inflate=(LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							View child=inflate.inflate(R.layout.popup_infoen, null);
							final EditText importo=(EditText)child.findViewById(R.id.importo);
							importo.setText(""+entrata.getImporto());
							final TextView categoria=(TextView)child.findViewById(R.id.cate_addspesa1);
							DbAdapter dbHelper=new DbAdapter(context);
							dbHelper.open();
							Cursor cursor=dbHelper.fetchCat(entrata.getCategoria());
							while(cursor.moveToNext())
							{
							int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CATID)));
							int color=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_COLORID)));
							int image=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ICONID)));
							String nome=(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CATNOME)));
							categoria.setText(nome);
							Categoria categorie=new Categoria(id,image,color,nome,null);
							categ1=categorie;
							}
							cursor.close();
						    Log.v("Categoria associata",categ1.toString());
							final TextView descrizione=(TextView)child.findViewById(R.id.descrizione);
							descrizione.setText(entrata.getDescrizione());
							final TextView data=(TextView)child.findViewById(R.id.textData_addspesa);
							new Calendario(data,entrata.getMonth(),entrata.getDay(),entrata.getYear());
							final TextView note=(TextView)child.findViewById(R.id.note);
							note.setText("Opzionale");
							final TextView conto=(TextView)child.findViewById(R.id.sel_txtconti);
							cursor=dbHelper.fetchConto(entrata.conto_id);
							while(cursor.moveToNext())
							{
								int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID)));
							    String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
							    float importo2=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM)));
								float importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
							    String nome=(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM)));
							    conto.setText(nome);
							    conto_scelto2=new Conto(id,nome,tipo,importo2,importo_att);
							}
								cursor.close();
							dbHelper.close();
							
							}
						});
						
						
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
							
							
							
					} // chiude j
								
				} // chiude i
				importo_tot+=value_att;	
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
			String stringa=cal.getData2()+" € "+importo_tot;
			mese_anno.setText(stringa);
			
			
		}
		else
		{
			layoutLista.removeAllViews();
			TextView text=new TextView(context);
			text.setText("No record");
			text.setTextColor(context.getResources().getColor(R.color.white));
			layoutLista.addView(text);
		}
	}
}
