package com.expenseManager.gestionespese.Utility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;

import Account.Categoria;
import Account.Conto;
import Account.Operazione;
import Account.Spesa;
import Utility.SigninActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Notification.Action;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Activity.AggiungiSpesa;
import com.expenseManager.gestionespese.Activity.aggiungiPosizione;
import com.expenseManager.gestionespese.Activity.popupCateEn;
import com.expenseManager.gestionespese.Activity.popupConto;
import com.expenseManager.gestionespese.Database.DbAdapter;

public class ListaSpesa {

	public Context context;
	public Activity act;
	public PopupWindow popup1,popup,popup2;
	public View child1;
	public LayoutInflater inflate1;
	public int width,height;
	aggiungiPosizione posizione1;
	Categoria categ=null,categ1=null;
	popupConto ll=null;
	Conto conto_scelto2=null;
	Calendario calendario1=null;
	public ListaSpesa(Context context1,Cursor cursor,DbAdapter dbAdapter,RelativeLayout layoutLista,Calendario cal,final Activity act1,TextView mese_anno)
	{
		ArrayList<String> date=new ArrayList<String>();
        context=context1;
        act=act1;
        
		int cont=0;
		float importo_tot=0;
		while(cursor.moveToNext())
		{
			cont++;
			/* */
			String data=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITADATA));
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
				ArrayList<Account.Spesa> spese = new ArrayList<Account.Spesa>();
				Cursor cursor1=dbAdapter.fetchOpUscita(date.get(k));
				while(cursor1.moveToNext())
				{
					int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITAID)));
					float importo=Float.parseFloat(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITAIM)));
					String data=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITADATA));
					String descrizione=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITADESC));
					int categoria_id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITACAT)));
					int conto_id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITACON)));
					int map=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITAMAP)));
					double lat=Double.parseDouble(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITALAT)));
					double lng=Double.parseDouble(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITALON)));
					String city=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITACIT));
					spese.add(new Account.Spesa(id, importo, data, descrizione, categoria_id, conto_id,map,lat,lng,city));
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
				for(int i=0;i<spese.size();i++)
				{

					value_att+=spese.get(i).getImporto();

					ArrayList<Categoria> categoria=new ArrayList<Categoria>();
					cursor=dbAdapter.fetchCatUs(spese.get(i).categoria_id);
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
						importo.setText("€ "+spese.get(i).getImporto());
						impparams.setMargins(0, 0, 10, 0);
						importo.setGravity(Gravity.CENTER);
						importo.setLayoutParams(impparams);	
						final Spesa spesa=spese.get(i);
						layout_cate.addView(image);
						layout_cate.addView(nomecat);
						layout_cate.addView(importo);
						
						layout_cate.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
                                final View child2=v;
                               
                                
								LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								final View child=inflate.inflate(R.layout.popup_info, null);
								final EditText importo=(EditText)child.findViewById(R.id.importo);
								importo.setText(""+spesa.getImporto());
								final TextView categoria=(TextView)child.findViewById(R.id.cate_addspesa1);
								DbAdapter dbHelper=new DbAdapter(context);
								dbHelper.open();
								Cursor cursor=dbHelper.fetchCatUs(spesa.getCategoria());
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
								descrizione.setText(spesa.getDescrizione());
								final TextView data=(TextView)child.findViewById(R.id.textData_addspesa);
								new Calendario(data,spesa.getMonth(),spesa.getDay(),spesa.getYear());
								final TextView note=(TextView)child.findViewById(R.id.note);
								note.setText("Opzionale");
								final TextView posizione=(TextView)child.findViewById(R.id.luogo);
								if(spesa.lat!=0.0 && spesa.lng!=0.0)
								posizione.setText("Luogo registrato");
								final TextView conto=(TextView)child.findViewById(R.id.sel_txtconti);
								cursor=dbHelper.fetchConto(spesa.conto_id);
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
								//conto.setText(spesa.getConto());
								
								
									
								

								//imageIcon
								final ImageView btncate=(ImageView)child.findViewById(R.id.sel_btncat);
								final ImageView seldata=(ImageView)child.findViewById(R.id.addData);
								final ImageView selconti=(ImageView)child.findViewById(R.id.sel_conti);
								final ImageView selposizi=(ImageView)child.findViewById(R.id.getPosizione);
                                
								final LinearLayout.LayoutParams paramsIcon=(android.widget.LinearLayout.LayoutParams) btncate.getLayoutParams();
								final LinearLayout.LayoutParams paramsComponent1=(android.widget.LinearLayout.LayoutParams) categoria.getLayoutParams();
								LinearLayout.LayoutParams paramsComponent=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

								

								LinearLayout cate_addSpesaLayout=(LinearLayout)child.findViewById(R.id.cat_addspesa);
								LinearLayout date_addSpesaLayout=(LinearLayout)child.findViewById(R.id.data_addspesa);
								LinearLayout conto_addSpesaLayout=(LinearLayout)child.findViewById(R.id.selConto_addspesa);
								LinearLayout pos_addSpesaLayout=(LinearLayout)child.findViewById(R.id.luogo_addspesa);
                                
								
								popup=new PopupWindow(child,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

								Display display=act.getWindowManager().getDefaultDisplay();
								Point size=new Point();
								display.getSize(size);
								width=size.x;
								height=size.y;
								popup.setHeight(height-100);
								popup.setWidth(width-100);
								popup.setFocusable(false);
								
                              
								if(!popup.isShowing())
								{
									popup.update();
									popup.showAtLocation(child2, Gravity.CENTER, 0, 0);
									
								}
								else
									popup.dismiss();

								final Button modifica=(Button)child.findViewById(R.id.DataDec);
								modifica.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										Log.v("V focus",""+v.hasFocus());
										Log.v("V focusable",""+v.hasFocusable());
										
										
										
										// TODO Auto-generated method stub
										if(modifica.getText().toString().equalsIgnoreCase("salva")==false)
										{
										final RelativeLayout bottomBar=(RelativeLayout)child.findViewById(R.id.bottom_bar);
										
										final Button annulla=new Button(child.getContext());
										final Button cancella=new Button(child.getContext());
										// Bottoni per gestire la modifica
										ImageButton sel_categoria=(ImageButton)child.findViewById(R.id.sel_btncat);
										ImageButton sel_data=(ImageButton)child.findViewById(R.id.addData);
										ImageButton sel_conto=(ImageButton)child.findViewById(R.id.sel_conti);
										ImageButton sel_luogo=(ImageButton)child.findViewById(R.id.getPosizione);
										
										inflate1=(LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
										child1=inflate1.inflate(R.layout.activity_categoria_us, null);
										
										 popup1=new PopupWindow(child1,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
										
										popup1.setHeight(height-100);
										popup1.setWidth(width-100);
										
										
										
										
										DbAdapter dbHelper1; 
										 
										dbHelper1 = new DbAdapter(context);
								        dbHelper1.open();
								        Cursor cursor1=dbHelper1.fetchAllCatUs();
								        
								        ArrayList<Account.Categoria> categorie=new ArrayList<Account.Categoria>();
								        
								        RelativeLayout layout=(RelativeLayout)child1.findViewById(R.id.Categorie);
								        RelativeLayout intestaCate=(RelativeLayout)child1.findViewById(R.id.intestaCate);
										ImageButton image=(ImageButton)child1.findViewById(R.id.addCategoria1);
										
										intestaCate.removeView(image);
								        Log.v("layout",layout.toString());
								        while(cursor1.moveToNext())
								        {
								        	
								          	int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATID)));
								            String nome=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATNOME));
								            int color=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_COLORID)));
								            int icon=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ICONID)));
								            
								            categorie.add(new Account.Categoria(id, icon, color, nome, null));
								        }
								    		
								        
								    		TextView text1=(TextView)child.findViewById(R.id.cate_addspesa1);
								    		
										Log.v("inflate1",inflate1.toString());
										Log.v("child1",child1.toString());
										//Log.v("popup1",popup1.toString());
									    categ=new Categoria();
										cursor1=dbHelper1.fetchAllCatUs();
										popupCateEn cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,null,text1,popup1,categ,act,"Uscita");
										cursor1.close();
										
										//Log.v("fuori popup1",""+popup1.isShowing());
								    	sel_categoria.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
												
												if(!popup1.isShowing())
												{
												 popup1.showAtLocation(child2, Gravity.CENTER, 0, 0);
												}
												else
												popup1.dismiss();
											}
											
										});
								    	
								    	 LayoutInflater inflate=(LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
											View child3=inflate.inflate(R.layout.popup_conti, null);
											
											 Cursor cursor;
											DbAdapter dbHelper = new DbAdapter(context);
									        dbHelper.open();
									        cursor=dbHelper.fetchAllCont();
									        
									        boolean flag=false;
									        RelativeLayout below=(RelativeLayout)child3.findViewById(R.id.conti12);
									        RelativeLayout scroll=(RelativeLayout)child3.findViewById(R.id.conti2);
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
									        popup2=new PopupWindow(child3,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
									        Account.Conto conto_scelto=null;
									        ll=new popupConto(conticino, scroll, child3.getContext(),child3,below,conto,popup2,conto_scelto);
									        popup2.setHeight(height-100);
								    		popup2.setWidth(width-100);
									        
									        cursor.close();
									        selconti.setOnClickListener(new View.OnClickListener() {
												
												@Override
												public void onClick(View v) {
													// TODO Auto-generated method stub
												if(!popup2.isShowing())
												{
													popup2.showAtLocation(child2, Gravity.CENTER, 0, 0);
												}
												else
												popup2.dismiss();
												Log.v("Is Open",""+popup.isShowing());
												
												}
											});
								    	
								    	
								    	posizione1=new aggiungiPosizione(context,act,posizione,"inflate");
								    	sel_luogo.setOnClickListener(posizione1);
								    	
								    	final Calendar data=Calendar.getInstance();
										TextView var=(TextView)child.findViewById(R.id.textData_addspesa);
										calendario1=new Calendario(data);
										ImageButton setData=(ImageButton)child.findViewById(R.id.addData);
										setData.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
											    final DatePickerDialog setData=new DatePickerDialog(act,null,data.get(Calendar.YEAR),data.get(Calendar.MONTH),data.get(Calendar.DAY_OF_MONTH));
											    setData.setButton(DialogInterface.BUTTON_POSITIVE,"Imposta", new DialogInterface.OnClickListener() {
													
													@Override
													public void onClick(DialogInterface dialog, int which) {
														// TODO Auto-generated method stub
													DatePicker data=setData.getDatePicker();
													TextView txt_data=(TextView)child.findViewById(R.id.textData_addspesa);	
												    calendario1.updateData(txt_data,data.getMonth(),data.getYear(),data.getDayOfMonth());
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
								    	/*sel_data.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
												
											}
										});*/
								    		
										RelativeLayout.LayoutParams modificaParams=(LayoutParams) modifica.getLayoutParams();
										modificaParams.width=modifica.getWidth()/3;
										modificaParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
										modifica.setLayoutParams(modificaParams);
										RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
										params.width=modificaParams.width;
										
										annulla.setTextColor(child.getResources().getColor(R.color.grey));
										annulla.setText("Annulla");
										annulla.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
										//params.addRule(RelativeLayout.ALIGN_LEFT,modifica.getId());
										params.addRule(RelativeLayout.CENTER_IN_PARENT);
										annulla.setGravity(modifica.getGravity());
										annulla.setLayoutParams(params);
										annulla.setBackgroundColor(child.getResources().getColor(android.R.color.transparent));
										cancella.setTextColor(child.getResources().getColor(R.color.grey));
										cancella.setText("Elimina");
										cancella.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
										params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
										params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
										params.width=modificaParams.width;
										cancella.setGravity(modifica.getGravity());
										cancella.setLayoutParams(params);
										cancella.setBackgroundColor(child.getResources().getColor(android.R.color.transparent));
										bottomBar.addView(annulla);
										bottomBar.addView(cancella);
										LinearLayout.LayoutParams paramsComponent=new LinearLayout.LayoutParams(180,LayoutParams.MATCH_PARENT);

										

										final LinearLayout cate_addSpesaLayout=(LinearLayout)child.findViewById(R.id.cat_addspesa);
										final LinearLayout date_addSpesaLayout=(LinearLayout)child.findViewById(R.id.data_addspesa);
										final LinearLayout conto_addSpesaLayout=(LinearLayout)child.findViewById(R.id.selConto_addspesa);
										final LinearLayout pos_addSpesaLayout=(LinearLayout)child.findViewById(R.id.luogo_addspesa);
										
										
										
										popup.setFocusable(true);
										popup.update();
										modifica.setText("Salva");
										annulla.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
												
												RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
												modifica.setLayoutParams(params);
												modifica.setText("Abilita Modifica");
												bottomBar.removeView(annulla);
												bottomBar.removeView(cancella);
												popup.setFocusable(false);
												popup.update();
											}
										});
										cancella.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
											Log.v("Operazione",""+spesa.getId());
											
											AlertDialog.Builder alert=new AlertDialog.Builder(context);
											alert.setTitle("Cancellazione spesa");
											alert.setMessage("Eliminare spesa : "+spesa.getData()+" "+spesa.getImporto()+"?");
											alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
												
												@Override
												public void onClick(DialogInterface dialog, int which) {
													// TODO Auto-generated method stub
													DbAdapter dbHelper=new DbAdapter(context);
													dbHelper.open();
													int n=dbHelper.deleteOpUscita(spesa.getId());
													dbHelper.close();
													if(n==1)
													{Toast.makeText(context, "Operazione avvenuta", Toast.LENGTH_SHORT).show();
													
													act.startActivity(new Intent(act,com.expenseManager.gestionespese.Activity.Spesa.class));}
													else
													Toast.makeText(context, "Non è stato possibile effettuare l'operazione", Toast.LENGTH_SHORT).show();
													popup.dismiss();
													
												}
											});
											alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
												
												@Override
												public void onClick(DialogInterface dialog, int which) {
													// TODO Auto-generated method stub
												dialog.dismiss();	
												}
											});
											alert.create();
											alert.show();
											}
										});
										}
										else
										{
											
											Log.v("posizione",""+posizione1.getCity());
											
											Log.v("Lat - Long",""+posizione1.lat+"-"+posizione1.lng);
											Log.v("Categoria",categ.toString());
											Account.Conto conto_scelto1=new Account.Conto();
											TextView text=(TextView)child.findViewById(R.id.sel_txtconti);
											TextView text2=(TextView)child.findViewById(R.id.cate_addspesa1);
											EditText value=(EditText)child.findViewById(R.id.importo);
											if(!value.getText().toString().equalsIgnoreCase(""))
											{
												if(text.getText().toString().equalsIgnoreCase(act.getResources().getString(R.string.sel_conto_default)) == false )
											{
													if(text2.getText().toString().equalsIgnoreCase(act.getResources().getString(R.string.cat_default)) == false)
													{
												Log.v("Value ",""+text2.getText().toString().equalsIgnoreCase(act.getResources().getString(R.string.cat_default)));
												Log.v("Value is ",text2.getText().toString());
												if(ll.getConto()!=null)
												conto_scelto1.clone(ll.getConto());
												else
												conto_scelto1.clone(conto_scelto2);
												DbAdapter dbHelper = new DbAdapter(context);
										        dbHelper.open();
										        
												float importo=Float.parseFloat(value.getText().toString());
											int categoria=categ.getId();
											
											String data=calendario1.getData();
											TextView description=(TextView)child.findViewById(R.id.descrizione);
											TextView note=(TextView)child.findViewById(R.id.note);
											String nota=note.getText().toString();
											String descrizione=description.getText().toString();
											Log.v("Data",data);
										
											Log.v("Categoria",""+categ.getId());
											if(posizione1.lat==0.0 && posizione1.lng==0.0)
											{
											    if(categoria==0)
											    	dbHelper.updateUscita(spesa.getId(), importo, data, descrizione, categ1.getId(), conto_scelto1.getId(), nota, 1, 0.0, 0.0, null, "Uscita");
											    else
											    dbHelper.updateUscita(spesa.getId(), importo, data, descrizione, categoria, conto_scelto1.getId(), nota, 1, 0.0, 0.0, null, "Uscita");
											    
											}
											else
											{
												Log.v("City",posizione1.city);
												if(categoria==0)
												dbHelper.updateUscita(spesa.getId(), importo, data, descrizione, categ1.getId(), conto_scelto1.getId(), nota, 0,posizione1.lat,posizione1.lng,posizione1.city,"Uscita");	
												else
													dbHelper.updateUscita(spesa.getId(), importo, data, descrizione, categoria, conto_scelto1.getId(), nota, 0,posizione1.lat,posizione1.lng,posizione1.city,"Uscita");	

 												
											}
											dbHelper.close();
											Toast.makeText(context, "Spesa Aggiunta", Toast.LENGTH_SHORT).show();
											String stringa="["+importo+"]["+categ.getNome()+"]["+data+"]["+descrizione+"]["+conto_scelto1.getNome()+"]["+nota+"]["+posizione1.getCity()+"]["+posizione1.lat+"]["+posizione1.lng+"]";
											Log.v("Spesa",stringa);
											popup.dismiss();
											act.startActivity(new Intent(act,com.expenseManager.gestionespese.Activity.Spesa.class));

											}else{Toast.makeText(context, "Scegli la categoria", Toast.LENGTH_SHORT).show();}
											}
											else
												{Toast.makeText(context, "Scegli il conto", Toast.LENGTH_SHORT).show();
												//conto_scelto1.clone(ll.getConto());
												Log.v("Conto Scelto",conto_scelto1.toString());}
											}
											else
											{
												Toast.makeText(context, "Inserire un importo valido", Toast.LENGTH_SHORT).show();
											}
											
										}
										


									}
								});
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
					//lista_date.addView(operazioni_lay);

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
	
	public PopupWindow getPrimaFinestra()
	{
		return popup;
		
	}
	
	public PopupWindow getPopupCategoria()
	{
		return popup1;
	}
	
	
}
