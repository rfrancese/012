package Account;

import java.io.Serializable;

public class Conto implements Serializable {
	
	private int id;
	private String nome,tipo;
	private Float importo,importo_att;

	public Conto(int id,String nome,String tipo,float importo,float importo_att)
	{
		this.id=id;
		this.nome=nome;
		this.tipo=tipo;
		this.importo=importo;
		this.importo_att=importo_att;
	}
	
	public Conto()
	{
		this.id=0;
		this.nome=null;
		this.tipo=null;
		this.importo=(float) 0;
		this.importo_att=(float) 0;
	}
	
	public float getImporto()
	{
		return this.importo;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getNome()
	{
		return this.nome;
	}
	
	public String getTipo()
	{
		return this.tipo;
	}
	
	public String toString()
	{
		return "["+id+"]["+nome+"]["+tipo+"]["+importo+"]";
	}
	
	public float getBalance()
	{
		return importo_att;
	}

	public void clone(Conto conto_scelto) {
		// TODO Auto-generated method stub
		this.id=conto_scelto.id;
		this.nome=conto_scelto.nome;
		this.tipo=conto_scelto.tipo;
		this.importo=conto_scelto.importo;
		this.importo_att=conto_scelto.importo_att;
	}
}
