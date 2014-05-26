package com.expenseManager.gestionespese.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private static final String TABLE_ENTRATA="op_entrata";
	private static final String TABLE_USCITA="Tab_Uscita";
	private static final String TABLE_CONTO="conto";
	private static final String TABLE_CAT_EN="categoria_en";
	private static final String TABLE_CAT_US="Tab_Cat_Us";
	
	//Valori tabelle
	private static final String KEY_ENTRATAID="_id";
	private static final String KEY_ENTRATAIM="importo";
	private static final String KEY_ENTRATADATA="data";
	private static final String KEY_ENTRATADESC="descrizione";
	private static final String KEY_ENTRATACAT="categoria";
	private static final String KEY_ENTRATACON="conto";
	
	private static final String KEY_USCITAID="_id";
	private static final String KEY_USCITAIM="importo";
	private static final String KEY_USCITADATA="data";
	private static final String KEY_USCITADESC="descrizione";
	private static final String KEY_USCITACAT="categoria";
	private static final String KEY_USCITACON="conto";
	private static final String KEY_USCITALAT="lat";
	private static final String KEY_USCITALON="lon";
	
	public static final String KEY_CATID="_id";
	public static final String KEY_CATNOME="nome";
	public static final String KEY_COLORID="color_id";
	public static final String KEY_ICONID="icon_id";
	
	public static final String KEY_CONTOID="_id";
	public static final String KEY_CONTOIM="importo_init";
	public static final String KEY_CONTOIM_AT="importo_att";
	public static final String KEY_CONTONOM="nome";
	public static final String KEY_CONTOTIP="tipo";
	
	public DbAdapter(Context context)
	{
		this.context=context;
	}
	
	public DbAdapter open() throws SQLException
	{
		dbHelper=new DatabaseHelper(context);
		database=dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close()
	{
		dbHelper.close();
	}
	
	public ContentValues createContentValuesConto(float importo_init,float importo_att,String nome,String tipo)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_CONTOIM, importo_init);
		values.put(KEY_CONTOIM_AT, importo_att);
		values.put(KEY_CONTONOM, nome);
		values.put(KEY_CONTOTIP, tipo);
		return values;
	}
	
	public ContentValues createContentValuesEntrata(float importo,String data,String descrizione,int categoria_id,int conto_id)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_ENTRATAIM, importo);
		values.put(KEY_ENTRATADATA, data);
		values.put(KEY_ENTRATADESC, descrizione);
		values.put(KEY_ENTRATACAT, categoria_id);
		values.put(KEY_ENTRATACON,conto_id);
		return values;
	}
	
	public ContentValues createContentValuesCatEn(String nome,int color,int image)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_CATNOME, nome);
		values.put(KEY_COLORID,color);
		values.put(KEY_ICONID,image);
		return values;
	}
	public long createConto(float importo,float importo_att,String nome,String tipo)
	{
	   ContentValues initialValues=createContentValuesConto(importo,importo_att,nome,tipo);
		return database.insertOrThrow(TABLE_CONTO, null, initialValues);
	}
	
	public long createEntrata(float importo,String data,String descrizione,int categoria_id,int conto_id)
	{
		ContentValues initialValues=createContentValuesEntrata(importo,data,descrizione,categoria_id,conto_id);
		return database.insertOrThrow(TABLE_ENTRATA, null, initialValues);
	}
	
	public long createCatEn(String nome,int color,int image)
	{
		ContentValues initialValues=createContentValuesCatEn(nome,color,image);
		return database.insertOrThrow(TABLE_CAT_EN, null, initialValues);
	}
	
	public Cursor fetchAllCatEn()
	{
		return database.query(TABLE_CAT_EN,new String[]{KEY_CATID,KEY_CATNOME,KEY_COLORID,KEY_ICONID},null,null,null, null, null);
	}
	
	public Cursor fetchAllCont()
	{
		return database.query(TABLE_CONTO,new String[]{KEY_CONTOID,KEY_CONTOIM,KEY_CONTOIM_AT,KEY_CONTONOM,KEY_CONTOTIP},null,null,null,null, null);
	}
	
	public int addEntrata(int id,float importo)
	{
		ContentValues args=new ContentValues();
		Cursor cont=fetchConto(id);
		int amount=0;
		while(cont.moveToNext())
			amount=Integer.parseInt(cont.getString(cont.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
		amount+=importo;
		cont.close();
		args.put(KEY_CONTOIM_AT, amount);
		return database.update(TABLE_CONTO, args, "_id ="+id, null);
	}
	
	public Cursor fetchConto(int id)
	{
		String sql="Select * from "+TABLE_CONTO+" where _id="+id+";";
		return database.rawQuery(sql, null);
	}
	
}
