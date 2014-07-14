package com.expenseManager.gestionespese.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import Account.Categoria;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class Mappe extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mappe);
		Log.v("Cerco","...");
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		Cursor cursor1=dbHelper.fetchOpUscitaWithMap();
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if(status!=ConnectionResult.SUCCESS){
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }else{
		GoogleMap map=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		Log.v("map",map.toString());
		ArrayList<LatLng> latlng=new ArrayList<LatLng>();
		ArrayList<Account.Categoria> categoria=new ArrayList<Account.Categoria>();
		ArrayList<Account.Spesa> spesa=new ArrayList<Account.Spesa>();
		while(cursor1.moveToNext())
		{
			 int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITAID)));
			 float importo=Float.parseFloat(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITAIM)));
	         String data=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITADATA));
	         String descrizione=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITADESC));
	         int categoria_id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITACAT)));
			 int conto_id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITACON)));
			 double lat=Double.parseDouble(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITALAT)));
			 double lng=Double.parseDouble(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITALON)));
			 String city=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_USCITACIT));
			 latlng.add(new LatLng(lat,lng));
			 spesa.add(new Account.Spesa(id, importo, data, descrizione, categoria_id, conto_id, 0, lat, lng, city));
			 Log.v("Map of Spesa[ "+id+" ][ "+importo+" ][ "+data+" ][ "+descrizione+" ][ "+categoria_id+" ][ "+conto_id+" ][ "+lat+" "+lng +" "+city+" ]",""+map);
		}
		cursor1.close();
		cursor1=dbHelper.fetchAllCatUs();
		while(cursor1.moveToNext())
		{
		int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATID)));
		String nome=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATNOME));
		categoria.add(new Account.Categoria(id,0,0,nome,null));	
		}
		cursor1.close();
		map.setMyLocationEnabled(true);
		
		 Geocoder geocoder; 
		List<Address> addresses; 
		geocoder = new Geocoder(this); 
	    String address=null,city=null,country=null;
		
		for(int i=0;i<latlng.size();i++)
		{
		Log.v("latlng size",""+latlng.size());
		Log.v("Ciclo di i",""+i);
			
			if (latlng.get(i).latitude != 0 || latlng.get(i).longitude != 0) 
			{ try {
				addresses = geocoder.getFromLocation(latlng.get(i).latitude , latlng.get(i).longitude, 1);
			
			address = addresses.get(0).getAddressLine(0); 
			city = addresses.get(0).getAddressLine(1);
			country = addresses.get(0).getAddressLine(2); 
			Log.d("TAG", "address = "+address+", city ="+city+", country = "+country );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			} else 
			{ Toast.makeText(this, "latitude and longitude are null",
					Toast.LENGTH_LONG).show();}
			
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng.get(i), 13));
			Categoria categoriascelta=null;
			for(int j=0;j<categoria.size();j++)
			{
				if(categoria.get(j).getId()==spesa.get(i).getCategoria())
				{
					categoriascelta=categoria.get(j);
				}
			}
			map.addMarker(new MarkerOptions().title(""+city+" "+address).snippet(categoriascelta.getNome()+" - "+spesa.get(i).descrizione+" :€ " +spesa.get(i).getImporto()).position(latlng.get(i)));
			
		}
        }
         
        }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mappe, menu);
		return true;
	}

}
