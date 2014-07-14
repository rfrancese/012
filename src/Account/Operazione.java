package Account;

public class Operazione {
	public double importo;
	public Categoria categoria;
  public Operazione(double importo,Categoria categoria)
  {
	  this.importo=importo;
	  this.categoria=categoria;
  }
  public double getImporto()
  {
	  return importo;
  }
  
  public String toString()
  {
	  return "Importo = "+importo+" Categoria : "+categoria.toString();
  }
}
