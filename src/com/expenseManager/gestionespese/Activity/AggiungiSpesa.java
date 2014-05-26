package com.expenseManager.gestionespese.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.ContiAdapter;
import com.expenseManager.gestionespese.Utility.CustomBaseAdapter;
import com.expenseManager.gestionespese.Utility.ListaConti;
import com.expenseManager.gestionespese.Utility.layoutConto;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
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
		ImageButton posizione=(ImageButton)findViewById(R.id.getPosizione);
		ImageButton conti=(ImageButton)findViewById(R.id.sel_conti);
		TextView text=(TextView)this.findViewById(R.id.sel_txtconti);
		
		    // ImageButton sel_cat=(ImageButton)findViewById(R.id.sel_btncat);
		     LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View child=inflate.inflate(R.layout.popup_conti, null);
				 DbAdapter dbHelper; 
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
		        final PopupWindow popup=new PopupWindow(child,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		        Account.Conto conto_scelto=null;
		        popupConto ll=new popupConto(conticino, scroll, child.getContext(),child,below,text,popup,conto_scelto);
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
    		TextView text1=(TextView)findViewById(R.id.cate_addspesa1);
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
		     
		posizione.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			LocationManager location=(LocationManager)v.getContext().getSystemService(LOCATION_SERVICE);
			Location locat = null;
			double lat = 0,lng=0;
			boolean isGPSEnabled=location.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean isNetworkEnabled=location.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if(!isGPSEnabled && !isNetworkEnabled)
			{
				;
			}
			else
			{
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
			if(isGPSEnabled)
			{
				if(locat==null)
				{
					location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1000*6*1, new LocationListener(){

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
					  locat=location.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				  }
				   if(locat!=null)
				   {
					   lat=locat.getLatitude();
					   lng=locat.getLongitude();
					   Log.v("lat-lon",""+lat+"-"+lng);
					   
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
				Log.d("TAG", "address = "+address+", city ="+city+", country = "+country );
				text.setText(address+" "+city);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aggiungi_spesa, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
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

}
