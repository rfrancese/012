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
		return "id: "+id+" icon: "+icon+" Nome: "+nome+" Tipo: "+tipo;
		
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
}
