package com.expenseManager.gestionespese.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
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

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
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
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if(status!=ConnectionResult.SUCCESS){
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }else{
		GoogleMap map=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		Log.v("map",map.toString());
		ArrayList<LatLng> latlng=new ArrayList<LatLng>();
		latlng.add(new LatLng(40.611,14.989));
		latlng.add(new LatLng(40.605,14.995));
		latlng.add(new LatLng(40.600970,14.978505));
		map.setMyLocationEnabled(true);
		
		 Geocoder geocoder; 
		List<Address> addresses; 
		geocoder = new Geocoder(this); 
	    String address=null,city=null,country=null;
		
		for(int i=0;i<latlng.size();i++)
		{
			
			
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
			BitmapDescriptor btmp_benz=BitmapDescriptorFactory.fromResource(R.drawable.caticon10);
			BitmapDescriptor btmp_svago=BitmapDescriptorFactory.fromResource(R.drawable.caticon37);
			BitmapDescriptor btmp_spesa=BitmapDescriptorFactory.fromResource(R.drawable.caticon15);
			switch(i)
			{
			
			case 0:map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng.get(i), 13));
			map.addMarker(new MarkerOptions().title(""+city+" "+address).snippet("Alimentari - Conad €24.00").position(latlng.get(i)));break;
			case 1:map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng.get(i), 13));
			map.addMarker(new MarkerOptions().title(""+city+" "+address).snippet("Trasporti - Benzina €10.00").position(latlng.get(i)));break;
			case 2:map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng.get(i), 13));
			map.addMarker(new MarkerOptions().title(""+city+" "+address).snippet("Divertimento - Stadio €5").position(latlng.get(i)));break;
			case 3:map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng.get(i), 13));break;
			}
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
