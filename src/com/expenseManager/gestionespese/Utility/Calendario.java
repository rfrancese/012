package com.expenseManager.gestionespese.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

public class Calendario {
	private String data="",data2;
	private int month,day,year;
	private ArrayList<String> intervallo=new ArrayList<String>();
	
/**
 * Questo costruttore prendere 4 variabili
 * @param var la textview da aggiornare
 * @param Month il mese corrispondente
 * @param Year l'anno corrispondente
 * @param Day il giorno corrispondente
 */
	
	
public Calendario(TextView var,int Month,int Year,int Day)
{
	updateData(var,Month,Year,Day);
	this.month=Month;
	this.day=Day;
	this.year=Year;
}

public Calendario(int Month,int Year,int Day,int Flag)
{
	int month=Month;
	int year=Year;
	int day=Day;
	calcolaIntervallo(month,year,day);
}

public Calendario(Calendar date)
{
	int month=date.get(Calendar.MONTH);
	int year=date.get(Calendar.YEAR);
	int day=date.get(Calendar.DAY_OF_MONTH);
	
    generaData(month, day, year);
}
public void calcolaIntervallo(
		int month, int year, int day) {
	// TODO Auto-generated method stub
	 GregorianCalendar data=new GregorianCalendar(year,month,day);
	 long yourDateMillis=data.getTimeInMillis();
	 Time primaData=new Time();
	 primaData.set(yourDateMillis);
	 String formatPrimaData=primaData.format("%Y-%m-%d");
	 
	 for(int i=0;i<6;i++)
	 {
		 if(i==0)
		 {
		   intervallo.add(formatPrimaData);	 
		 }
	 long yourDateMillis1 = yourDateMillis - ((i+1) * 24 * 60 * 60 * 1000);
	 Time yourDate = new Time(); 
	 yourDate.set(yourDateMillis1); 
	 intervallo.add(yourDate.format("%Y-%m-%d"));
	 
	 }
	 for(int i=0;i<intervallo.size();i++)
	 {
		 Log.v("Periodo",intervallo.get(i));
	 }
	 //Aggiungere le date all'array
	 
	
}

/**
 * Questo costruttore prende come paramentri sempre 4 valori
 * @param var 
 * @param Month
 * @param Year
 * @param Month_array l'array dei mesi
 */

public Calendario(TextView var,int Month,int Year,String[] Month_array)
{
	this.month=Month;
	this.year=Year;
	updateDataArray(var,Month,Year,Month_array);
}

public Calendario(TextView data2, String string) {
	// TODO Auto-generated constructor stub
	this.year=Integer.parseInt(string.substring(0, 4));
	this.month=Integer.parseInt(string.substring(5, 7))-1;
	this.day=Integer.parseInt(string.substring(8));
	updateData(data2,month,year,day);
}

public Calendario(TextView settimana,String[] b) {
	// TODO Auto-generated constructor stub

	updateCalcoloSettimana(settimana,b);
	
}

public Calendario(int year2) {
	// TODO Auto-generated constructor stub
	this.data="'"+year2+"-01-01' and '"+year2+"-12-31'";
}

private void updateCalcoloSettimana(TextView settimana,String[] month_array) {
	// TODO Auto-generated method stub
	 long yourDateMillis=System.currentTimeMillis();
	 Time primaData=new Time();
	 primaData.set(yourDateMillis);
	 String formatPrimaData=primaData.format("%Y-%m-%d");
	 long yourDateMillis1 = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000); 
	 Time yourDate = new Time(); 
	 yourDate.set(yourDateMillis1); 
	 String formattedDate = yourDate.format("%Y-%m-%d");
	 this.data="'"+formattedDate+"' and '"+formatPrimaData+"'";
		switch(primaData.month)
		{
		case 0: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 1: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 2: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 3: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 4: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 5: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 6: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 7: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 8: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 9: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 10: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		case 11: this.data2=month_array[primaData.month]+","+primaData.monthDay+","+primaData.year;break;
		}
		switch(yourDate.month)
		{
		case 0: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 1: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 2: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 3: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 4: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 5: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 6: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 7: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 8: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 9: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 10: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		case 11: this.data2+=" - "+month_array[yourDate.month]+","+yourDate.monthDay+","+yourDate.year;break;
		}

	 settimana.setText("Ultima settimana : "+data2);
	 Log.v("C - B",""+data);
}

private void updateDataArray(TextView var, int month, int year,
		String[] month_array) {
	// TODO Auto-generated method stub
	this.month=month;
	this.year=year;
	if(month<9)
	data="'"+year+"-0"+(month+1)+"-"+"01' AND '"+year+"-0"+(month+1)+"-"+"31'";
	else
		data="'"+year+"-"+(month+1)+"-"+"01' AND '"+year+"-"+(month+1)+"-"+"31'";	
	Log.v("Data",data);
	switch(month)
	{
	case 0: var.setText(month_array[month]+","+year);break;
	case 1: var.setText(month_array[month]+","+year);break;
	case 2: var.setText(month_array[month]+","+year);break;
	case 3: var.setText(month_array[month]+","+year);break;
	case 4: var.setText(month_array[month]+","+year);break;
	case 5: var.setText(month_array[month]+","+year);break;
	case 6: var.setText(month_array[month]+","+year);break;
	case 7: var.setText(month_array[month]+","+year);break;
	case 8: var.setText(month_array[month]+","+year);break;
	case 9: var.setText(month_array[month]+","+year);break;
	case 10: var.setText(month_array[month]+","+year);break;
	case 11: var.setText(month_array[month]+","+year);break;
	}
	data2=var.getText().toString();
}

public void setData(TextView var,String[] month_array,int month,int year)
{
 updateDataArray(var, month, year, month_array)	;
}

public void generaData(int month,int day,int year)
{
	if(month<9)
	data=year+"-0"+(month+1)+"-"+day;
	else
	data=year+"-"+(month+1)+"-"+day;
	
}

public String getData()
{
	Log.v("Data",data);
 	return data;
}

public void updateData(TextView var,int Month,int Year,int Day)
{
	this.month=Month;
	this.year=Year;
	this.day=Day;
	Log.v("Var",var.toString());
	if(Month<9 && Day<10)
	data=""+Year+"-0"+(Month+1)+"-0"+Day;
	if(Month<9 && Day>=10)
		data=""+Year+"-0"+(Month+1)+"-"+Day;
	if(Month>=9 && Day<10)
		data=""+Year+"-"+(Month+1)+"-0"+Day;
	if(Month>=9 && Day>=10)
		data=""+Year+"-"+(Month+1)+"-"+Day;
	switch(Month)
	{
	case 0: var.setText("Gennaio,"+Day+","+Year);break;
	case 1: var.setText("Febbraio,"+Day+","+Year);break;
	case 2: var.setText("Marzo,"+Day+","+Year);break;
	case 3: var.setText("Aprile,"+Day+","+Year);break;
	case 4: var.setText("Maggio,"+Day+","+Year);break;
	case 5: var.setText("Giugno,"+Day+","+Year);break;
	case 6: var.setText("Luglio,"+Day+","+Year);break;
	case 7: var.setText("Agosto,"+Day+","+Year);break;
	case 8: var.setText("Settembre,"+Day+","+Year);break;
	case 9: var.setText("Ottobre,"+Day+","+Year);break;
	case 10: var.setText("Novembre,"+Day+","+Year);break;
	case 11: var.setText("Dicembre,"+Day+","+Year);break;
	}	
}

public int getMonth()
{
	return month;
}

public ArrayList<String> getIntervallo()
{
	return intervallo;
}

public int getDay()
{
	return day;
}

public int getYear()
{
	return year;
}

public String toString()
{
	return data;
}

public String getData2()
{
	return data2;
}
}
