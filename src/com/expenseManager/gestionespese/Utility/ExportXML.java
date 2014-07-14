package com.expenseManager.gestionespese.Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.expenseManager.gestionespese.Database.DbAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

public class ExportXML {
	
 public Context context;	
	public ExportXML(Context context)
	{   
		this.context=context;
		generaFileXml();
	}
	

	
 public void generaFileXml()
 {
	 Calendar ub_calendario=Calendar.getInstance();
	 Calendario ub_myCal =new Calendario(ub_calendario);
	 //String url=Environment.getExternalStorageDirectory().getAbsolutePath();
	 String url="/storage/extSdCard/gestioneSpeseBackUp";
	 String pathFile=url+"/gestioneSpese"+ub_myCal.getData()+".xml";
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
	String intestaXML="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	String root="<tabelle>";
	try {
		out.write(intestaXML);
		out.newLine();
		out.write(root);
		out.newLine();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	for(int i=0;i<dati.size();i++)
	{
		switch(i)
		{
		case 0: {String dataToWrite="<!--- TABLE CONTO -->";try {
			out.write(dataToWrite);out.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}break;}
		case 1: {String dataToWrite="<!--- TABLE REDDITO -->";try {
			out.write(dataToWrite);out.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}break;}
		case 2: {String dataToWrite="<!--- TABLE SPESE -->";try {
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
				out.newLine();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	root="</tabelle>";
	try {
		out.write(root);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}



private void generaStatistiche() {
	// TODO Auto-generated method stub
	
}



private ArrayList<String> generaTabellaSpese(DbAdapter dbHelper) {
	// TODO Auto-generated method stub
	Cursor cursor=dbHelper.fetchAllOpUscita();
	ArrayList<String> conto_xml=new ArrayList<String>();
	conto_xml.add("<tabella id=\"spese\">");
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
	  conto_xml.add("<id>"+_id+"</id>");
	  conto_xml.add("<data>"+data+"</data>");
	  conto_xml.add("<conto>"+conto+"</conto>");
	  conto_xml.add("<categoria>"+categoria+"</categoria>");
	  conto_xml.add("<descrizione>"+descrizione+"</descrizione>");
	  conto_xml.add("<note>"+note+"</note>");
	  conto_xml.add("<importo>"+importo+"</importo>");
	  conto_xml.add("<latitude>"+lat+"</latitude>");
	  conto_xml.add("<longitude>"+lon+"</longitude>");
	  conto_xml.add("<city>"+map+"</city>");
	}
	conto_xml.add("</tabella>");
	cursor.close();		
	return conto_xml;
}



private ArrayList<String> generaTabellaReddito(DbAdapter dbHelper) {
	// TODO Auto-generated method stub
	Cursor cursor=dbHelper.fetchAllOpEntrata();
	ArrayList<String> conto_xml=new ArrayList<String>();
	conto_xml.add("<tabella id=\"reddito\">");
	while(cursor.moveToNext())
	{
	  String _id=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAID));
	  String importo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM));
	  String data=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADATA));
	  String descrizione=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADESC));
	  String categoria=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT));
	  String conto=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON));
	  String note=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATANOT));
	  
	  conto_xml.add("<id>"+_id+"</id>");
	  conto_xml.add("<data>"+data+"</data>");
	  conto_xml.add("<conto>"+conto+"</conto>");
	  conto_xml.add("<categoria>"+categoria+"</categoria>");
	  conto_xml.add("<descrizione>"+descrizione+"</descrizione>");
	  conto_xml.add("<note>"+note+"</note>");
	  conto_xml.add("<importo>"+importo+"</importo>");
	}
	conto_xml.add("</tabella>");
	cursor.close();	
	return conto_xml;
}



private ArrayList<String> generaTabellaConto(DbAdapter dbHelper) {
	// TODO Auto-generated method stub
	Cursor cursor=dbHelper.fetchAllCont();
	ArrayList<String> conto_xml=new ArrayList<String>();
	conto_xml.add("<tabella id=\"conto\">");
	while(cursor.moveToNext())
	{
	  String _id=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID));
	  String importo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM));
	  String importo_att=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT));
	  String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM));
	  String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
	  conto_xml.add("<id>"+_id+"</id>");
	  conto_xml.add("<denominazione>"+nome+"</denominazione>");
	  conto_xml.add("<tipologia>"+tipo+"</tipologia>");
	  conto_xml.add("<importo_init>"+importo+"</importo_init>");
	  conto_xml.add("<importo_att>"+importo_att+"</importo_att>");
	}
	conto_xml.add("</tabella>");
	cursor.close();
	return conto_xml;
}

}