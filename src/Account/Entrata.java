package Account;

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
	
}
