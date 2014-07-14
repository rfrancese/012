package com.expenseManager.gestionespese.Activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.expenseManager.gestionespese.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class aggiungiPosizione implements OnClickListener {

	
	public double lat=0;
	public double lng=0;
	Context context;
	Object act;
	View child3=null;
	public String address=null,city="",country=null,flag;
	
	public aggiungiPosizione(Context context,Activity act)
	{
		this.context=context;
		this.act=act;
		this.flag="activity";
	}
	
	public aggiungiPosizione(Context context,Activity act,View view,String flag)
	{
		this.context=context;
		this.act=act;
		this.child3=view;
		this.flag=flag;
	}
	public String getCity()
	{
		return city;
	}
	@Override
	public void onClick(View v) {
		LocationManager location=null;
		// TODO Auto-generated method stub
		if(flag.equalsIgnoreCase("inflate")==true)
		{
			LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View child3=inflate.inflate(R.layout.popup_conti, null);
		location=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		}
		else
		{
		location=(LocationManager)((Activity)act).getSystemService(Context.LOCATION_SERVICE);
		}
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
				{*/
					location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1000*6*1, new LocationListener(){

						@Override
						public void onLocationChanged(Location location) {
							// TODO Auto-generated method stub
							TextView text=null;
							if(flag.equalsIgnoreCase("inflate")==true)
							{
								
								text=(TextView)child3;
								
							}
							else
							{
								text=(TextView)((Activity)act).findViewById(R.id.luogo);
							}
							 Geocoder geocoder; 
								List<Address> addresses; 
								geocoder = new Geocoder(context,Locale.ENGLISH);
								
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
                       
						}});
					
				
				  if(location!=null)
				  {
					  locat=location.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				  }
				   if(locat!=null)
				   {
					   
					   lat=locat.getLatitude();
					   lng=locat.getLongitude();
					   Log.v("lat-lon gps",""+lat+"-"+lng);
					   TextView text=null;
						if(flag.equalsIgnoreCase("inflate")==true)
						{
							
							text=(TextView)child3;
							
						}
						else
						{
							text=(TextView)((Activity)act).findViewById(R.id.luogo);
						}
						 Geocoder geocoder; 
							List<Address> addresses; 
							geocoder = new Geocoder(context,Locale.ENGLISH);
							
							try {
								addresses = geocoder.getFromLocation(lat , lng, 1);
							Locale local=new Locale("en","EN");
							
							Address prova=new Address(local);
							
							
							address = addresses.get(0).getAddressLine(0); 
							city = addresses.get(0).getAddressLine(1);
							country = addresses.get(0).getAddressLine(2); 
							prova.setCountryName(country);
							Log.v("addresses",prova.getCountryName());
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

					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						
					}

					
					public void onStatusChanged(String provider,
							int status, Bundle extras) {
						// TODO Auto-generated method stub
						
					}

					
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						
					}

					
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
		
		TextView text=null;
		if(flag.equalsIgnoreCase("inflate")==true)
		{
			
			text=(TextView)child3;
			
		}
		else
		{
			text=(TextView)((Activity)act).findViewById(R.id.luogo);
		}
		 Geocoder geocoder; 
			List<Address> addresses;
			geocoder = new Geocoder(v.getContext(),Locale.ENGLISH);
			
			try {
				addresses = geocoder.getFromLocation(lat , lng, 1);
				Locale local=new Locale("en","EN");
				
				Address prova=new Address(local);
				
			address = addresses.get(0).getAddressLine(0); 
			city = addresses.get(0).getAddressLine(1);
			country = addresses.get(0).getAddressLine(2); 
			prova.setCountryCode(addresses.get(0).getCountryCode());
			//Log.v("Country name ",prova.getCountryName());
			Log.d("TAG network", "address = "+address+", city ="+city+", country = "+country );
			Log.v("Text",text.toString());
			text.setText(address+" "+city);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
		}
	}



