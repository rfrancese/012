package com.expenseManager.gestionespese.Activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;
import com.expenseManager.gestionespese.Utility.ContiAdapter;
import com.expenseManager.gestionespese.Utility.CustomBaseAdapter;
import com.expenseManager.gestionespese.Utility.ListaConti;
import com.expenseManager.gestionespese.Utility.layoutConto;

import Account.Categoria;
import Account.Conto;
import Utility.SigninActivity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AggiungiSpesa extends Activity implements LocationListener{
	DbAdapter dbHelper;
	int a=0;
	popupCateEn cat;
	Context context=this;
	RelativeLayout layout;
	LayoutInflater inflate1;
	View child1;
	PopupWindow popup1,popup;
	Cursor cursor1;
    String address=null,city=null,country=null;
	Categoria categ=new Categoria();
	aggiungiPosizione position=new aggiungiPosizione(context,this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aggiungi_spesa);
		final Context context=this;
		Display display=getWindowManager().getDefaultDisplay();
		Point size=new Point();
		display.getSize(size);
		int width=size.x;
		int height=size.y;
		final Calendar data=Calendar.getInstance();
		TextView var=(TextView)findViewById(R.id.textData_addspesa);
		final Calendario calendario=new Calendario(var,data.get(Calendar.MONTH),data.get(Calendar.YEAR),data.get(Calendar.DAY_OF_MONTH));
		ImageButton setData=(ImageButton)findViewById(R.id.addData);
		setData.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    final DatePickerDialog setData=new DatePickerDialog(AggiungiSpesa.this,null,data.get(Calendar.YEAR),data.get(Calendar.MONTH),data.get(Calendar.DAY_OF_MONTH));
			    setData.setButton(DialogInterface.BUTTON_POSITIVE,"Imposta", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					DatePicker data=setData.getDatePicker();
					TextView txt_data=(TextView)findViewById(R.id.textData_addspesa);	
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
		LocationManager location=(LocationManager)this.getSystemService(LOCATION_SERVICE);
		Location locat = null;
		final Double lat = new Double(0);
		final double lng=0;
		boolean isGPSEnabled=location.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if(!isGPSEnabled)
		{
			AlertDialog.Builder dialog=new AlertDialog.Builder(context);
            dialog.setTitle("Abilitazione GPS");
            dialog.setMessage("Per ottenere la posizione precisa è consigliato l'utilizzo del GPS. Attivarlo ora?");
            dialog.setPositiveButton("Attiva Ora", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent attivaGPS=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(attivaGPS);
				}
			});
            dialog.setNegativeButton("No grazie!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				 dialog.dismiss();	
				}
			});
            dialog.show();	
		}
		
		
		ImageButton posizione=(ImageButton)findViewById(R.id.getPosizione);
		ImageButton conti=(ImageButton)findViewById(R.id.sel_conti);
		TextView text=(TextView)this.findViewById(R.id.sel_txtconti);
		
		    // ImageButton sel_cat=(ImageButton)findViewById(R.id.sel_btncat);
		     LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View child=inflate.inflate(R.layout.popup_conti, null);
				
				 Cursor cursor;
				dbHelper = new DbAdapter(this);
		        dbHelper.open();
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
		        Account.Conto conto_scelto=null;
		        final popupConto ll=new popupConto(conticino, scroll, child.getContext(),child,below,text,popup,conto_scelto);
		        popup.setHeight(height-100);
	    		popup.setWidth(width-100);
		        stopManagingCursor(cursor);
		        cursor.close();
				
				
				Log.v("Is Open",""+popup.isShowing());
		     conti.setOnClickListener(new View.OnClickListener() {
				
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
		     
		ImageButton categoria=(ImageButton)findViewById(R.id.sel_btncat);
		
		inflate1=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		child1=inflate1.inflate(R.layout.activity_categoria_us, null);
		 DbAdapter dbHelper1; 
		 
		dbHelper1 = new DbAdapter(this);
        dbHelper1.open();
        cursor1=dbHelper.fetchAllCatUs();
        startManagingCursor(cursor1);
        ArrayList<Account.Categoria> categorie=new ArrayList<Account.Categoria>();
        
        layout=(RelativeLayout)child1.findViewById(R.id.Categorie);
        Log.v("layout",layout.toString());
        while(cursor1.moveToNext())
        {
          	int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATID)));
            String nome=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATNOME));
            int color=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_COLORID)));
            int icon=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ICONID)));
            
            categorie.add(new Account.Categoria(id, icon, color, nome, null));
        }
    	stopManagingCursor(cursor1);	
        cursor1.close();
    		TextView text1=(TextView)findViewById(R.id.cate_addspesa1);
    		inflate1=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child1=inflate1.inflate(R.layout.activity_categoria_us, null);
    		popup1=new PopupWindow(child1,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    		
    		popup1.setHeight(height-100);
    		popup1.setWidth(width-100);
	        //popupCateEn cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,below,text1,popup1,null,this);
	        
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
	        Button salva=(Button)findViewById(R.id.btn_salva);
		    salva.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Log.v("Lat - Long",""+position.lat+"-"+position.lng);
				Log.v("Categoria",categ.toString());
				Account.Conto conto_scelto1=new Account.Conto();
				TextView text=(TextView)findViewById(R.id.sel_txtconti);
				TextView text2=(TextView)findViewById(R.id.cate_addspesa1);
				EditText value=(EditText)findViewById(R.id.importo);
				if(!value.getText().toString().equalsIgnoreCase(""))
				{
					if(text.getText().toString().equalsIgnoreCase(getResources().getString(R.string.sel_conto_default)) == false )
				{
						if(text2.getText().toString().equalsIgnoreCase(getResources().getString(R.string.cat_default)) == false)
						{
					Log.v("Value ",""+text2.getText().toString().equalsIgnoreCase(getResources().getString(R.string.cat_default)));
					Log.v("Value is ",text2.getText().toString());
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
				if(position.lat==0.0 && position.lng==0.0)
				{
				dbHelper.createUscita(importo, data, descrizione, categoria, conto_scelto1.getId(),nota,1,0.0,0.0,null,"Uscita");
				}
				else
				{
					Log.v("City",position.city);
					dbHelper.createUscita(importo, data, descrizione, categoria, conto_scelto1.getId(),nota,0,position.lat,position.lng,position.city,"Uscita");	
					new Thread(new Runnable(){
						public void run(){
						try {
							Log.v("position",position.country);
							Log.v("position city",position.city);
						new SigninActivity(context,position.country,position.lng,position.lat);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					}
					}).start();
				}
				dbHelper.addUscita(conto_scelto1.getId(), importo);
				Toast.makeText(context, "Spesa Aggiunta", Toast.LENGTH_SHORT).show();
				String stringa="["+importo+"]["+categ.getNome()+"]["+data+"]["+descrizione+"]["+conto_scelto1.getNome()+"]["+nota+"]["+position.getCity()+"]["+position.lat+"]["+position.lng+"]";
				Log.v("Spesa",stringa);
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
				
				
			});
		     
		/*posizione.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			LocationManager location=(LocationManager)v.getContext().getSystemService(LOCATION_SERVICE);
			Location locat = null;
			
			boolean isGPSEnabled=location.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean isNetworkEnabled=location.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			Log.v("GPS enable",""+isGPSEnabled);
			Log.v("NetworkEnable",""+isNetworkEnabled);
			if(!isGPSEnabled && !isNetworkEnabled)
			{
				Toast.makeText(context, "Per ottenere la posizione corrente è necessario attivare GPS o Internet", Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(isGPSEnabled)
				{
					/*if(locat==null)
					{
						location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1000*6*1, new LocationListener(){

							@Override
							public void onLocationChanged(Location location) {
								// TODO Auto-generated method stub
								TextView text=(TextView)findViewById(R.id.luogo);
								 Geocoder geocoder; 
									List<Address> addresses; 
									geocoder = new Geocoder(context);
									String address=null,city=null,country=null;
									try {
										addresses = geocoder.getFromLocation(location.getLatitude() , location.getLongitude(), 1);
									
									address = addresses.get(0).getAddressLine(0); 
									city = addresses.get(0).getAddressLine(1);
									country = addresses.get(0).getAddressLine(2); 
									Log.d("TAG gps on location" +
											"", "address = "+address+", city ="+city+", country = "+country );
									text.setText(address+" "+city);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
									
								
							}

							@Override
							public void onStatusChanged(String provider,
									int status, Bundle extras) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onProviderEnabled(String provider) {
								// TODO Auto-generated method stub
							
							}

							@Override
							public void onProviderDisabled(String provider) {
								// TODO Auto-generated method stub
	                        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
	                        dialog.setTitle("Abilitazione GPS");
	                        dialog.setMessage("Per ottenere la posizione precisa è consigliato l'utilizzo del GPS. Attivarlo ora?");
	                        dialog.setPositiveButton("Attiva Ora", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent attivaGPS=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(attivaGPS);
								}
							});
	                        dialog.setNegativeButton("No grazie!", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
								 dialog.dismiss();	
								}
							});
	                        dialog.show();
							}});
						
					
					  if(location!=null)
					  {
						  locat=location.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					  }
					   if(locat!=null)
					   {
						   Double lat1=new Double(locat.getLatitude());
						   lat=locat.getLatitude();
						   lng=locat.getLongitude();
						   Log.v("lat-lon gps",""+lat+"-"+lng);t
						   TextView text=(TextView)findViewById(R.id.luogo);
							 Geocoder geocoder; 
								List<Address> addresses; 
								geocoder = new Geocoder(context);
								String address=null,city=null,country=null;
								try {
									addresses = geocoder.getFromLocation(lat , lng, 1);
								
								address = addresses.get(0).getAddressLine(0); 
								city = addresses.get(0).getAddressLine(1);
								country = addresses.get(0).getAddressLine(2); 
								Log.d("TAG gps", "address = "+address+", city ="+city+", country = "+country );
								text.setText(address+" "+city);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
						   
					   }
				}
				
				if(isNetworkEnabled)
				{
					location.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 1000*60*1,new LocationListener(){

						@Override
						public void onLocationChanged(Location location) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onStatusChanged(String provider,
								int status, Bundle extras) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onProviderEnabled(String provider) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onProviderDisabled(String provider) {
							// TODO Auto-generated method stub
							
						}});
				}
				if(location!=null)
				{
					locat=location.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					 if(locat!=null)
					 {
						lat=locat.getLatitude();
						lng=locat.getLongitude();
					 }
				}
			}
			
			TextView text=(TextView)findViewById(R.id.luogo);
			 Geocoder geocoder; 
				List<Address> addresses; 
				geocoder = new Geocoder(v.getContext());
				String address=null,city=null,country=null;
				try {
					addresses = geocoder.getFromLocation(lat , lng, 1);
				
				address = addresses.get(0).getAddressLine(0); 
				city = addresses.get(0).getAddressLine(1);
				country = addresses.get(0).getAddressLine(2); 
				Log.d("TAG network", "address = "+address+", city ="+city+", country = "+country );
				text.setText(address+" "+city);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
			}
		});*/
            
            posizione.setOnClickListener(position);
            
           
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aggiungi_spesa, menu);
		return true;
	}
	
	public void onResume()
	{
		Log.v("popup1 prima di resume",""+popup1.isShowing());
		
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
		cursor1=dbHelper.fetchAllCatUs();
		Display display=getWindowManager().getDefaultDisplay();
		Point size=new Point();
		display.getSize(size);
		int width=size.x;
		int height=size.y; 
	
		layout=(RelativeLayout)child1.findViewById(R.id.Categorie);
	        
 		TextView text1=(TextView)findViewById(R.id.cate_addspesa1);
		popup1.setHeight(height-100);
		popup1.setWidth(width-100);
 		cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,null,text1,popup1,categ,this,"Uscita");
		if(popup1.isShowing())
		{
        	layout.invalidate();
        	cat=new popupCateEn(cursor1, layout, child1.getContext(),child1,null,text1,popup1,categ,this,"Uscita");
        	popup1.dismiss();
        	Log.v("popup1",""+popup1.isShowing());
			popup1.showAtLocation(this.getCurrentFocus(), Gravity.CENTER, 0, 0);
			Log.v("popup1",""+popup1.isShowing());
		} 
		Log.v("Is Open",""+popup1.isShowing());
		cursor1.close();
		
	    // Remove the unique action so the next time onResume is called it will restart
	   
		Log.v("onresume","onresume");
		
		super.onResume();
 		
	       /* for(int i=0;i<cat.getArrayList().size();i++)
			{
				Log.v("onResume : I = "," "+i+" ArrayList : " +cat.getArrayList().get(i).getNome());
			}*/
	        
			/*
	        popup1.update();*/
	
			
	 
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		TextView text=(TextView)findViewById(R.id.luogo);
		 Geocoder geocoder; 
			List<Address> addresses; 
			geocoder = new Geocoder(this);
			
			try {
				addresses = geocoder.getFromLocation(location.getLatitude() , location.getLongitude(), 1);
			
			address = addresses.get(0).getAddressLine(0); 
			city = addresses.get(0).getAddressLine(1);
			country = addresses.get(0).getAddressLine(2); 
			Log.d("TAG gps main", "address = "+address+", city ="+city+", country = "+country );
			text.setText(address+" "+city);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		{
			int cont=3;
			if(popup.isShowing()==true)
			{ popup.dismiss(); return false;} 
			else if(popup1.isShowing()==true)
				{popup1.dismiss();return false;}
			else if(popup1.isShowing()==false && popup.isShowing()==false)cont=0;
			
			Log.v("Cont",""+cont);
		}
		return super.onKeyDown(keyCode, event);
	}

}
