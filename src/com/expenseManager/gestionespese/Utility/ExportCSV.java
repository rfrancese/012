package com.expenseManager.gestionespese.Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.expenseManager.gestionespese.Database.DbAdapter;

public class ExportCSV {
	 
		
		 public Context context;	
			public ExportCSV(Context context)
			{   
				this.context=context;
				generaFileCsv();
			}
			

			
		 public void generaFileCsv()
		 {
			 Calendar ub_calendario=Calendar.getInstance();
			 Calendario ub_myCal =new Calendario(ub_calendario);
			 //String url=Environment.getExternalStorageDirectory().getAbsolutePath();
			 String url="/storage/extSdCard/gestioneSpeseBackUp";
			 String pathFile=url+"/gestioneSpese"+ub_myCal.getData()+".csv";
			 File ub_openFile=new File(pathFile);
			 try{
				 
			 if(!ub_openFile.exists())
			 { 
					ub_openFile.createNewFile();
			 }
			 
			 FileWriter out=new FileWriter(ub_openFile);
			 BufferedWriter wr=new BufferedWriter(out);
			 elaborazioneContenuto(wr);
			 wr.close();
			 out.close();
			 }
			 catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 
		public void elaborazioneContenuto(BufferedWriter out)
		{
			DbAdapter dbHelper=new DbAdapter(context);
			dbHelper.open();
			ArrayList<ArrayList<String>> dati=new ArrayList<ArrayList<String>>();
			dati.add(generaTabellaConto(dbHelper));
			dati.add(generaTabellaReddito(dbHelper));
			dati.add(generaTabellaSpese(dbHelper));
			//dati.add(generaStatistiche());
			dbHelper.close();
			//Scrivi su file
			
			for(int i=0;i<dati.size();i++)
			{
				switch(i)
				{
				case 0: {String dataToWrite="Id,Nome,Tipo,Importo Iniziale,Importo Attuale";try {
					out.write(dataToWrite);out.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}break;}
				case 1: {String dataToWrite="Id,Data,Categoria,Conto,Descrizione,Note,Importo";try {
					out.write(dataToWrite);out.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}break;}
				case 2: {String dataToWrite="Id,Data,Categoria,Conto,Descrizione,Note,Importo,Latitude,Longitude,City";try {
					out.write(dataToWrite);out.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}break;}
				
				}
				ArrayList<String> val=dati.get(i);
				
				for(int j=0;j<val.size();j++)
				{
					
					try
					{
						
						out.write(val.get(j));
						Log.e("out.write:val.get("+j+")",val.get(j));
							out.newLine();
					} 
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			
		}



		private void generaStatistiche() {
			// TODO Auto-generated method stub
			
		}



		private ArrayList<String> generaTabellaSpese(DbAdapter dbHelper) {
			// TODO Auto-generated method stub
			Cursor cursor=dbHelper.fetchAllOpUscita();
			ArrayList<String> conto_xml=new ArrayList<String>();
			while(cursor.moveToNext())
			{
			  String _id=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAID));
			  String importo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM));
			  String data=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA));
			  String descrizione=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADESC));
			  String categoria=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT));
			  String conto=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON));
			  String note=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATANOT));
			  String lat=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITALAT));
			  String lon=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITALON));
			  String map=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITACIT));
			  String dati=_id+","+data+","+conto+","+categoria+","+descrizione+","+note+","+importo+","+lat+","+lon+","+map;
			  conto_xml.add(dati);
			  
			}
			
			cursor.close();		
			return conto_xml;
		}



		private ArrayList<String> generaTabellaReddito(DbAdapter dbHelper) {
			// TODO Auto-generated method stub
			Cursor cursor=dbHelper.fetchAllOpEntrata();
			ArrayList<String> conto_xml=new ArrayList<String>();
			while(cursor.moveToNext())
			{
			  String _id=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAID));
			  String importo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM));
			  String data=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA));
			  String descrizione=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADESC));
			  String categoria=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT));
			  String conto=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON));
			  String note=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATANOT));
			  String dati=_id+","+data+","+conto+","+categoria+","+descrizione+","+note+","+importo;
			  conto_xml.add(dati);

			 
			}
			cursor.close();	
			return conto_xml;
		}



		private ArrayList<String> generaTabellaConto(DbAdapter dbHelper) {
			// TODO Auto-generated method stub
			Cursor cursor=dbHelper.fetchAllCont();
			ArrayList<String> conto_xml=new ArrayList<String>();
			while(cursor.moveToNext())
			{
			  String _id=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID));
			  String importo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM));
			  String importo_att=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT));
			  String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM));
			  String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
			  String dati=_id+","+nome+","+tipo+","+importo+","+importo_att;
			  conto_xml.add(dati);

			  
			}
			cursor.close();
			return conto_xml;
		}

		}

