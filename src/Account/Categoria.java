package Account;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Categoria {
	
	private Drawable icon_drw;
	private int color,id,icon;
	private String tipo,nome;

	public Categoria(int id,int icon,int color,String nome,String tipo)
	{
	this.id=id;
	this.icon=icon;	
	this.color=color;
	this.nome=nome;
	this.tipo=tipo;
	}
	
	public Categoria(int id,String nome,int color,int icon)
	{
		this.id=id;
		this.nome=nome;
		this.color=color;
		this.icon=icon;
	}
	
	public Categoria()
	{
		this.id=0;
		this.icon=0;	
		this.color=0;
		this.nome=null;
		this.tipo=null;
	}
	
	public String getNome()
	{
	 return nome;	
	}
	
	public String getType()
	{
		return tipo;
	}
	
	public String toString()
	{
		return "id: "+id+" icon: "+icon+" Nome: "+nome+" Color: "+color;
		
	}
	
	public Drawable getDrawableIcon(Context context)
	{
		Drawable img=context.getResources().getDrawable(icon);
		Bitmap bitmap=((BitmapDrawable)img).getBitmap();
		Drawable img1=new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap, 70, 70, true));
		icon_drw=img1;
		return icon_drw;
	}
	
	public int getColor()
	{
		return color;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public void clona(Categoria cat)
	{
		this.id=cat.id;
		this.icon=cat.icon;	
		this.color=cat.color;
		this.nome=cat.nome;
		this.tipo=cat.tipo;
	}

	public void setNome(String string) {
		// TODO Auto-generated method stub
	  this.nome=string;
	}

	public void setId(int id2) {
		// TODO Auto-generated method stub
	 icon=id2;	
	}
	
	public void setColor(int color)
	{
		this.color=color;
	}
	
	public int getIcon()
	{
		return icon;
	}


}
