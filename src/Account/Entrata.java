package Account;

import android.util.Log;

public class Entrata {

	public int _id,categoria_id,conto_id;
	public float importo;
	public String data,descrizione;
	
	public Entrata(int _id,float importo,String data,String descrizione,int categoria_id,int conto_id)
	{
		this._id=_id;
		this.importo=importo;
		this.data=data;
		this.descrizione=descrizione;
		this.categoria_id=categoria_id;
		this.conto_id=conto_id;
	}
	
	public float getImporto()
	{
		return importo;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getData()
	{
		return data;
	}
	
	public int getDay()
	{
		int day=Integer.parseInt(data.substring(8));
		return day;
	}
	
	public int getMonth()
	{
		int month=Integer.parseInt(data.substring(5,7))-1;
		return month;
	}
	public int getYear()
	{
		int year=Integer.parseInt(data.substring(0,4));
		return year;
	}
	
	public String getDescrizione()
	{
		return descrizione;
	}
	
	public int getConto()
	{
		return conto_id;
	}
	
	public int getCategoria()
	{
		return categoria_id;
	}
	
	public String toString()
	{
		return "[ id = "+_id+" ][ data = "+data+" ][ importo = "+importo+" ]";
	}
	
}
