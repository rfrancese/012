package com.expenseManager.gestionespese.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;

public class SelezioneReport implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int conto;
	private String selezione,periodo,data;
	private ArrayList<String> periodoSettimana=null;
	public SelezioneReport(String selezione,int conto,String periodo,String data)
	{
		this.selezione=selezione;
		this.conto=conto;
		this.periodo=periodo;
		this.data=data;
	}
	
	public SelezioneReport(String selezione, int conto, String periodo,
			String data, ArrayList<String> periodoSettimana) {
		// TODO Auto-generated constructor stub
		this.selezione=selezione;
		this.conto=conto;
		this.periodo=periodo;
		this.data=data;
		this.periodoSettimana=periodoSettimana;
	}

	public String getSelezione()
	{
		return selezione;
	}
	
	public int getConto()
	{
		return conto;
	}
	
	public String getPeriodo()
	{
		return periodo;
	}
	
	public String getData()
	{
		return data;
	}
	
	public ArrayList<String> getPeriodoSettimana()
	{
		return periodoSettimana;
	}
	public String toString()
	{
		if(periodoSettimana==null)
		return "["+selezione+"]["+conto+"]["+periodo+"]["+data+"][Array : no ]";
		else
			return "["+selezione+"]["+conto+"]["+periodo+"]["+data+"][Array : si ]";
	}
	
	public ArrayList<String> getPeriodoMensile(int mese)
	{
		ArrayList<String> mensilita=new ArrayList<String>();
		Calendar mySimpleDate=Calendar.getInstance();
		Calendar myDate=new GregorianCalendar(mySimpleDate.get(Calendar.YEAR),mese,1);
		int maxDateInMonth=myDate.getActualMaximum(Calendar.DATE);
		Log.v("maxDateInMonth",""+maxDateInMonth);
		long myDateMillis=myDate.getTimeInMillis();
		Log.v("myDateMillis",""+myDateMillis);
		Time primaData=new Time();
		primaData.set(myDateMillis);
		String formatPrimaData=primaData.format("%Y-%m-%d");
		for(int i=0;i<=maxDateInMonth;i++)
		{
			if(i==0)
				mensilita.add(formatPrimaData);
			else
			{
				if(i>=25)
				{
					Calendar c=myDate;
					Date d=c.getTime();
					
					c.set(Calendar.DATE, i);
					d=c.getTime();
					if(c.get(Calendar.MONTH)<9)
					{
					mensilita.add(c.get(Calendar.YEAR)+"-0"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));
					}
					else
						mensilita.add(c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));	
				}
				else
				{
					Long yourDateMillis = new Long(i * 24 * 60 * 60 * 1000);
					 long yourDateMillis1 = myDateMillis + yourDateMillis;
					 Time yourDate = new Time(); 
					 yourDate.set(yourDateMillis1); 
					
					 
					// Log.v("Giorno mese["+i+"]",yourDate.format("%Y-%m-%d"));
					 mensilita.add(yourDate.format("%Y-%m-%d"));	
				}
				
			}
		}
			
		return mensilita;
	}
	public String getDataOfMese()
	{
		String formatMese[]=data.split("-");
		Calendar cal=Calendar.getInstance();
		return formatMese[0]+" "+cal.get(Calendar.YEAR);
	}
	public String getPeriodoMens(String month)
	{
		String formatMese[]=month.split("-");
		int mese=Integer.parseInt(formatMese[1])+1;
		String data=null;
		if(mese<=9)
	    data="'2014-0"+mese+"-01' AND '2014-0"+mese+"-31'";
		else
			data="'2014-"+mese+"-01' AND '2014-"+mese+"-31'";
		return data;
	}
	public String getPeriodoSett()
	{
		String data="'"+this.getPeriodoSettimana().get(this.getPeriodoSettimana().size()-1)+"' AND '"+this.getPeriodoSettimana().get(0)+"'";
	    return data;
	}
}
