package com.expenseManager.gestionespese.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbAdapter {

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private static final String TABLE_ENTRATA="op_entrata";
	private static final String TABLE_USCITA="op_uscita";
	private static final String TABLE_CONTO="conto";
	private static final String TABLE_CAT_EN="categoria_en";
	private static final String TABLE_CAT_US="categoria_us";
	
	//Valori tabelle
	public static final String KEY_ENTRATAID="_id";
	public static final String KEY_ENTRATAIM="importo";
	public static final String KEY_ENTRATADATA="data";
	public static final String KEY_ENTRATADESC="descrizione";
	public static final String KEY_ENTRATACAT="categoria";
	public static final String KEY_ENTRATACON="conto";
	public static final String KEY_ENTRATANOT="note";
	public static final String KEY_ENTRATATAB="tabella";
	
	public  static final String KEY_USCITAID="_id";
	public  static final String KEY_USCITAIM="importo";
	public static final String KEY_USCITADATA="data";
	public  static final String KEY_USCITADESC="descrizione";
	public  static final String KEY_USCITACAT="categoria";
	public  static final String KEY_USCITACON="conto";
	public  static final String KEY_USCITALAT="lat";
	public  static final String KEY_USCITALON="lon";
	public  static final String KEY_USCITAMAP="map";
	public  static final String KEY_USCITACIT="city";
	public  static final String KEY_USCITANOT="note";
	public static final String KEY_USCITATAB="tabella";
	
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
	/**
	 * Crea i contentvalues per la creazione del conto
	 * @param importo_init
	 * @param importo_att
	 * @param nome
	 * @param tipo
	 * @return
	 */
	public ContentValues createContentValuesConto(float importo_init,float importo_att,String nome,String tipo)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_CONTOIM, importo_init);
		values.put(KEY_CONTOIM_AT, importo_att);
		values.put(KEY_CONTONOM, nome);
		values.put(KEY_CONTOTIP, tipo);
		return values;
	}
	/**
	 * Crea i content values per la creazione dell'entrata
	 * @param importo
	 * @param data
	 * @param descrizione
	 * @param categoria_id
	 * @param conto_id
	 * @param nota
	 * @return
	 */
	public ContentValues createContentValuesEntrata(float importo,String data,String descrizione,int categoria_id,int conto_id,String nota,String tabella)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_ENTRATAIM, importo);
		values.put(KEY_ENTRATADATA, data);
		values.put(KEY_ENTRATADESC, descrizione);
		values.put(KEY_ENTRATACAT, categoria_id);
		values.put(KEY_ENTRATACON,conto_id);
		values.put(KEY_ENTRATANOT, nota);
		values.put(KEY_ENTRATATAB, tabella);
		return values;
	}
	/**
	 * Creo il content values per creare la categoria in entrata
	 * @param nome
	 * @param color
	 * @param image
	 * @return
	 */
	public ContentValues createContentValuesCatEn(String nome,int color,int image)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_CATNOME, nome);
		values.put(KEY_COLORID,color);
		values.put(KEY_ICONID,image);
		return values;
	}
	/**
	 * Creo il content values per creare la categoria in uscita
	 * @param nome
	 * @param color
	 * @param image
	 * @return
	 */
	public ContentValues createContentValuesCatUs(String nome,int color,int image)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_CATNOME, nome);
		values.put(KEY_COLORID,color);
		values.put(KEY_ICONID,image);
		return values;
	}
	/**
	 * Query che crea il conto
	 * @param importo
	 * @param importo_att
	 * @param nome
	 * @param tipo
	 * @return
	 */
	public long createConto(float importo,float importo_att,String nome,String tipo)
	{
	   ContentValues initialValues=createContentValuesConto(importo,importo_att,nome,tipo);
		return database.insertOrThrow(TABLE_CONTO, null, initialValues);
	}
	/**
	 * Query che crea l'entrata
	 * @param importo
	 * @param data
	 * @param descrizione
	 * @param categoria_id
	 * @param conto_id
	 * @param nota
	 * @return
	 */
	public long createEntrata(float importo,String data,String descrizione,int categoria_id,int conto_id, String nota,String tabella)
	{
		ContentValues initialValues=createContentValuesEntrata(importo ,data,descrizione,categoria_id,conto_id,nota,tabella);
		return database.insertOrThrow(TABLE_ENTRATA, null, initialValues);
	}
	
	public long createUscita(float importo,String data,String descrizione,int categoria_id,int conto_id,String nota,int map,double lat,double lng,String city,String tabella)
	{
		ContentValues initialValues=createContentValuesUscita(importo,data,descrizione,categoria_id,conto_id,nota,map,lat,lng,city,tabella);
		return database.insertOrThrow(TABLE_USCITA, null, initialValues);
	}
	private ContentValues createContentValuesUscita(float importo, String data,
			String descrizione, int categoria_id, int conto_id, String nota,
			int map, double lat, double lng, String city,String tabella) {
		// TODO Auto-generated method stub
		ContentValues values=new ContentValues();
		values.put(KEY_USCITAIM, importo);
		values.put(KEY_USCITADATA ,data);
		values.put(KEY_USCITADESC,descrizione);
		values.put(KEY_USCITACAT,categoria_id);
		values.put(KEY_USCITACON,conto_id);
		values.put(KEY_USCITANOT, nota);
		values.put(KEY_USCITAMAP, map);
		values.put(KEY_USCITALAT,lat);
		values.put(KEY_USCITALON, lng);
		values.put(KEY_USCITACIT,city);
		values.put(KEY_USCITATAB,tabella);
		return values;
		
	}

	/**
	 * Query che crea la categoria d'entrata
	 * @param nome
	 * @param color
	 * @param image
	 * @return
	 */
	public long createCatEn(String nome,int color,int image)
	{
		ContentValues initialValues=createContentValuesCatEn(nome,color,image);
		return database.insertOrThrow(TABLE_CAT_EN, null, initialValues);
	}
	/**
	 * Query che ottiene tutte le categorie in entrata
	 * @return
	 */
	public Cursor fetchAllCatEn()
	{
		return database.query(TABLE_CAT_EN,new String[]{KEY_CATID,KEY_CATNOME,KEY_COLORID,KEY_ICONID},null,null,null, null, null);
	}
	/**
	 * Query che ottiene tutti i conti
	 * @return
	 */
	public Cursor fetchAllCont()
	{
		return database.query(TABLE_CONTO,new String[]{KEY_CONTOID,KEY_CONTOIM,KEY_CONTOIM_AT,KEY_CONTONOM,KEY_CONTOTIP},null,null,null,null, null);
	}
	/**
	 * Query che aggiunge un'entrata
	 * @param id
	 * @param importo
	 * @return
	 */
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
	public int addUscita(int id,float importo)
	{
		ContentValues args=new ContentValues();
		Cursor cont=fetchConto(id);
		int amount=0;
		while(cont.moveToNext())
			amount=Integer.parseInt(cont.getString(cont.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
		amount-=importo;
		cont.close();
		args.put(KEY_CONTOIM_AT, amount);
		return database.update(TABLE_CONTO, args, "_id ="+id, null);	
	}
	/**
	 * Query che ottiene le operazioni in entrata in base alla data
	 * @param data
	 * @return
	 */
	public Cursor fetchOpEntrata(String data)
	{
		String sql="Select * from "+TABLE_ENTRATA+" where data = '"+data+"' ORDER BY "+KEY_ENTRATADATA+" DESC;";
		Log.v("Sql",sql);
		return database.rawQuery(sql,null);
	}
	/**
	 * Query che ottiene tutte le operazioni in entrata
	 * @return
	 */
	public Cursor fetchAllOpEntrata()
	{
		String sql="Select * from "+TABLE_ENTRATA+";";
		return database.rawQuery(sql, null);
	}
	/**
	 * Query che ottiene l'importo delle operazioni in entrata in base alla data
	 * @param data
	 * @return
	 */
	public Cursor fetchOpData(String data)
	{
		String sql="select *  from "+TABLE_ENTRATA+" where data between "+data+" ORDER BY "+KEY_ENTRATADATA+ " DESC;";
		Log.v("Sql",sql);
		return database.rawQuery(sql, null);
	}
	/**
	 * Query che ottiene la data delle operazioni in entrata
	 * @param data
	 * @return
	 */
	public Cursor fetchData(String data)
	{
		String sql="Select distinct data from "+TABLE_ENTRATA+" where data between "+data+" ORDER BY "+KEY_ENTRATADATA+ " DESC;";
		Log.v("Sql",sql);
		return database.rawQuery(sql, null);
	}
	/**
	 * Query che ottiene il conto in base all'id
	 * @param id
	 * @return
	 */
	public Cursor fetchConto(int id)
	{
		String sql="Select * from "+TABLE_CONTO+" where _id="+id+";";
		return database.rawQuery(sql, null);
	}
	/**
	 * Query che ottiene le operazioni in entrata in base all'anno
	 * @param year
	 * @return
	 */
	public Cursor fetchOpByYear(int year)
	{
		String data="'"+year+"-01-01' and '"+year+"-12-31'";
		String sql="Select * from "+TABLE_ENTRATA+" where data between "+data+";";
		Log.v("SQL fetchOpByYear",sql);
		return database.rawQuery(sql, null);
	}
	
	/**
	 * Query che ottiene la categoria in entrata in base all'id
	 * @param id
	 * @return
	 */
	public Cursor fetchCat(int id)
	{
		String sql="Select * from "+TABLE_CAT_EN+" where _id="+id+";";
		return database.rawQuery(sql, null);
	}
    /**
     * Query che ottiene la data in ordine ascendente
     * @param data
     * @return
     */
	public Cursor fetchDataDec(String data) {
		// TODO Auto-generated method stub
		String sql="Select distinct data from "+TABLE_ENTRATA+" where data between "+data+" ORDER BY "+KEY_ENTRATADATA+ " ASC;";
		return database.rawQuery(sql, null);
	}
/**
 * Query che crea la categoria in uscita
 * @param nome
 * @param color
 * @param icon
 * @return
 */
	public long createCatUs(String nome, int color, int icon) {
		// TODO Auto-generated method stub
		ContentValues initialValues=createContentValuesCatUs(nome,color,icon);
		return database.insertOrThrow(TABLE_CAT_US, null, initialValues);
	}
/**
 * Query per ottenere tutte le categorie in uscita
 * @return
 */
public Cursor fetchAllCatUs() {
	// TODO Auto-generated method stub
	return database.query(TABLE_CAT_US,new String[]{KEY_CATID,KEY_CATNOME,KEY_COLORID,KEY_ICONID},null,null,null, null, null);
}

public Cursor fetchOpUsData(String data) {
	// TODO Auto-generated method stub
	String sql="Select distinct data from "+TABLE_USCITA+" where data between "+data+" ORDER BY "+KEY_ENTRATADATA+ " DESC;";
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUsData1(String data)
{
	String sql="select *  from "+TABLE_USCITA+" where data between "+data+" ORDER BY "+KEY_USCITADATA+ " DESC;";
	Log.v("Sql fetchopUsData1",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchSpesaDataDec(String data) {
	// TODO Auto-generated method stub
	String sql="Select distinct data from "+TABLE_USCITA+" where data between "+data+" ORDER BY "+KEY_ENTRATADATA+ " ASC;";
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUscita(String string) {
	// TODO Auto-generated method stub
	String sql="Select * from "+TABLE_USCITA+" where data = '"+string+"' ORDER BY "+KEY_USCITADATA+" DESC;";
	
	return database.rawQuery(sql,null);
}

public Cursor fetchCatUs(int categoria_id) {
	// TODO Auto-generated method stub
	String sql="Select * from "+TABLE_CAT_US+" where _id="+categoria_id+";";
	return database.rawQuery(sql, null);
}

public Cursor fetchAllOpUscita() {
	// TODO Auto-generated method stub
	String sql="Select * from "+TABLE_USCITA+";";
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUscitaWithMap() {
	// TODO Auto-generated method stub
	String sql="Select * from "+TABLE_USCITA+" where map=0;";
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsByYear(int year) {
	// TODO Auto-generated method stub
	String data="'"+year+"-01-01' and '"+year+"-12-31'";
	String sql="Select * from "+TABLE_USCITA+" where data between "+data+";";
	Log.v("SQL fetchOpByYear",sql);
	return database.rawQuery(sql, null);
}

public long removeCategoria(int id)
{
	String sql="delete from "+TABLE_CAT_EN+" where _id="+id+";";
	return database.delete(TABLE_CAT_EN, "_id="+id, null);
}

public Cursor fetchOpEnCatAndData(String data) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_ENTRATA+" where data= '"+data+"';";
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnCatAndDate(String data) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_ENTRATA+" where data between "+data+";";
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnCatByYear(String data) {
	// TODO Auto-generated method stub
	String date="'"+data+"-01-01' AND '"+data+"-12-31'";
	String sql="Select distinct categoria from "+TABLE_ENTRATA+" where data between "+date+";";
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsCatAndData(String data) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_USCITA+" where data= '"+data+"';";
	Log.v("fetchOpUsCatAndData",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsCatAndDate(String data) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_USCITA+" where data between "+data+";";
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsCatByYear(String data) {
	// TODO Auto-generated method stub
	String date="'"+data+"-01-01' AND '"+data+"-12-31'";
	String sql="Select distinct categoria from "+TABLE_USCITA+" where data between "+date+";";
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnByCatAndDate(int id, String data) {
	// TODO Auto-generated method stub
  String sql="select *  from "+TABLE_ENTRATA+" where categoria="+id+" and data between "+data+";";
  Log.v("Sql",sql);
  return database.rawQuery(sql, null);
}

public Cursor fetchOpEnByCatAndData(int id, String data) {
	// TODO Auto-generated method stub
String sql="select *  from "+TABLE_ENTRATA+" where categoria="+id+" and data='"+data+"';";
Log.v("Sql",sql);
return database.rawQuery(sql,null);
}

public Cursor fetchOpEnByCatByYear(int id, String data) {
	// TODO Auto-generated method stub
	String data1="'"+data+"-01-01' and '"+data+"-12-31'";
	String sql="select *  from "+TABLE_ENTRATA+" where categoria="+id+" and data between "+data1+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsByCatAndDate(int id,String data) {
	// TODO Auto-generated method stub
String sql="select * from "+TABLE_USCITA+" where categoria="+id+" and data between "+data+";";
Log.v("Sql",sql);
return database.rawQuery(sql, null);
}

public Cursor fetchOpUsByCatAndData(int id, String data) {
	// TODO Auto-generated method stub
	String sql="select * from "+TABLE_USCITA+" where categoria="+id+" and data='"+data+"';";
	Log.v("Sql",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUsByCatByYear(int id,String data) {
	// TODO Auto-generated method stub
	String data1="'"+data+"-01-01' and '"+data+"-12-31'";
	String sql="select * from "+TABLE_USCITA+" where categoria="+id+" and data between "+data1+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpEnUsByData(String data)
{
	String sql="select data,importo,conto,descrizione,categoria,tabella from "+TABLE_ENTRATA+" where data='"+data+"' union select data,importo,conto,descrizione,categoria,tabella from "+TABLE_USCITA+" where data='"+data+"';";
	Log.v("Sql",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUsByYear(int parseInt, int conto) {
	// TODO Auto-generated method stub
	String data="'"+parseInt+"-01-01' and '"+parseInt+"-12-31'";
	String sql="Select * from "+TABLE_USCITA+" where data between "+data+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("SQL fetchOpByYear",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpByYear(int parseInt, int conto) {
	// TODO Auto-generated method stub
	String data="'"+parseInt+"-01-01' and '"+parseInt+"-12-31'";
	String sql="Select * from "+TABLE_ENTRATA+" where data between "+data+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("SQL fetchOpByYear",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUsData1(String periodoMens, int conto) {
	// TODO Auto-generated method stub
	String sql="Select * from "+TABLE_USCITA+" where data between "+periodoMens+" and "+KEY_ENTRATACON+"="+conto+" ORDER BY "+KEY_USCITADATA+ " DESC;";
	Log.v("Sql fetchopUsData1",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpData(String periodoMens, int conto) {
	// TODO Auto-generated method stub
	String sql="Select * from "+TABLE_ENTRATA+" where data between "+periodoMens+" and "+KEY_ENTRATACON+"="+conto+"ORDER BY "+KEY_ENTRATADATA+ " DESC;";
	Log.v("Sql",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUscita(String data, int conto) {
	// TODO Auto-generated method stub
String sql="Select * from "+TABLE_USCITA+" where data = '"+data+"' and "+KEY_ENTRATACON+"="+conto+" ORDER BY "+KEY_USCITADATA+" DESC;";
Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEntrata(String data, int conto) {
	// TODO Auto-generated method stub
	String sql="Select * from "+TABLE_ENTRATA+" where data = '"+data+"' and "+KEY_ENTRATACON+"="+conto+" ORDER BY "+KEY_ENTRATADATA+" DESC;";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnCatAndData(String data, int conto) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_ENTRATA+" where data= '"+data+"' and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnCatAndDate(String data, int conto) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_ENTRATA+" where data between "+data+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnCatByYear(String data, int conto) {
	// TODO Auto-generated method stub
	String date="'"+data+"-01-01' AND '"+data+"-12-31'";
	String sql="Select distinct categoria from "+TABLE_ENTRATA+" where data between "+date+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsCatAndData(String data, int conto) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_USCITA+" where data= '"+data+"' and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("fetchOpUsCatAndData",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsCatAndDate(String data, int conto) {
	// TODO Auto-generated method stub
	String sql="Select distinct categoria from "+TABLE_USCITA+" where data between "+data+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsCatByYear(String data, int conto) {
	// TODO Auto-generated method stub
	String date="'"+data+"-01-01' AND '"+data+"-12-31'";
	String sql="Select distinct categoria from "+TABLE_USCITA+" where data between "+date+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnByCatAndData(int id, String data, int conto) {
	// TODO Auto-generated method stub
	String sql="select * from "+TABLE_ENTRATA+" where categoria="+id+" and data='"+data+"' and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpEnByCatAndDate(int id, String data, int conto) {
	// TODO Auto-generated method stub
	String sql="select * from "+TABLE_ENTRATA+" where categoria="+id+" and data between "+data+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);  
	return database.rawQuery(sql, null);
}

public Cursor fetchOpEnByCatByYear(int id, String data, int conto) {
	// TODO Auto-generated method stub
	String data1="'"+data+"-01-01' and '"+data+"-12-31'";
	String sql="select * from "+TABLE_ENTRATA+" where categoria="+id+" and data between "+data1+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUsByCatAndData(int id, String data, int conto) {
	// TODO Auto-generated method stub
	String sql="select * from "+TABLE_USCITA+" where categoria="+id+" and data='"+data+"' and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUsByCatAndDate(int id, String data, int conto) {
	// TODO Auto-generated method stub
	String sql="select * from "+TABLE_USCITA+" where categoria="+id+" and data between "+data+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql, null);
}

public Cursor fetchOpUsByCatByYear(int id, String data, int conto) {
	// TODO Auto-generated method stub
	String data1="'"+data+"-01-01' and '"+data+"-12-31'";
	String sql="select * from "+TABLE_USCITA+" where categoria="+id+" and data between "+data1+" and "+KEY_ENTRATACON+"="+conto+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql, null);
}

public int deleteConto(int conto_id)
{
	String sql="delete from "+TABLE_CONTO +"where _id="+conto_id+";";
	deleteOpUscita(0,conto_id);
	deleteOpEntrata(0,conto_id);
	return database.delete(TABLE_CONTO,"_id="+conto_id, null);
}

public int deleteOpUscita(int op_id,int conto_id)
{
	String sql="delete from "+TABLE_USCITA+" where "+KEY_USCITACON+"="+conto_id+";";
	Log.v("Conto_id",""+conto_id);
	return database.delete(TABLE_USCITA,"conto="+conto_id, null);
}

public int deleteOpEntrata(int op_id,int conto_id)
{
	Log.v("Conto_id",""+conto_id);
	String sql="delete from "+TABLE_ENTRATA+" where "+KEY_USCITACON+"="+conto_id+";";
	return database.delete(TABLE_ENTRATA,"conto="+conto_id, null);
}

public int deleteOpEntrata(int op_id)
{
	String sql="delete from "+TABLE_ENTRATA+" where "+KEY_ENTRATAID+"="+op_id+";";
	Cursor cursor=fetchOpEntrata(op_id);
	float importo=0;
	int conto_id=-1;
	while(cursor.moveToNext())
	{
		importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
		conto_id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON)));
	}
	cursor.close();
	aggiornaConto(importo,conto_id,"Entrata");
	return database.delete(TABLE_ENTRATA,"_id="+op_id, null);
	
}

public int deleteOpUscita(int op_id)
{
	String sql="delete from "+TABLE_USCITA+" where "+KEY_ENTRATAID+"="+op_id+";";
	Cursor cursor=fetchOpUscita(op_id);
	float importo=0;
	int conto_id=-1;
	while(cursor.moveToNext())
	{
		importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
		conto_id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON)));
		Log.v("Conto",""+conto_id);
	}
	cursor.close();
	int n=aggiornaConto(importo,conto_id,"Uscita");
	Log.v("Avrei dovuto eseguire","SI");
	Log.v("n",""+n);
	return database.delete(TABLE_USCITA, "_id="+op_id, null);
}

public int aggiornaConto(float importo,int conto_id,String table)
{
	Cursor cursor=fetchConto(conto_id);
	Log.v("Conto id",""+conto_id);
	ContentValues args=new ContentValues();
	float importo_att=0;
	while(cursor.moveToNext())
	importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
	Log.v("Importo_att",""+importo_att);
	if(table.equalsIgnoreCase("Entrata")==true)
	importo_att-=importo;
	else
	importo_att+=importo;
	args.put(KEY_CONTOIM_AT, importo_att);
	Log.v("Avrei dovuto eseguire l'aggiornamento","SI");
	return database.update(TABLE_CONTO, args , "_id="+conto_id, null);
}

public Cursor fetchOpEntrata(int id)
{
	String sql="select * from "+TABLE_ENTRATA+" where _id="+id+";";
	Log.v("Sql",sql);
	return database.rawQuery(sql,null);
}

public Cursor fetchOpUscita(int id)
{
	String sql="select importo,conto from "+TABLE_USCITA+" where _id="+id+";";
	return database.rawQuery(sql,null);
}

public int updateEntrata(int id,float importo,String data,int categoria_id,int conto_id,String descrizione,String nota)
{
	ContentValues args=new ContentValues();
	args.put(KEY_ENTRATAIM, importo);
	args.put(KEY_ENTRATACAT, categoria_id);
	args.put(KEY_ENTRATACON, conto_id);
	args.put(KEY_ENTRATADESC,descrizione);
	args.put(KEY_ENTRATADATA, data);
	args.put(KEY_ENTRATANOT,nota);
	Cursor cursor=fetchOpEntrata(id);
	float importo1=0;
	int conto=-1;
	Log.v("Cursor",""+cursor.getCount());
	while(cursor.moveToNext())
	{
		Log.v("fin qui tutto bene","OK");
		importo1=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
		conto=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON)));
	}
	cursor.close();
	 if(conto==conto_id)
	 {
		 if(importo1>importo)
		 {
			 decrementaConto(importo1-importo,conto_id);
		 }
		 else if(importo1<importo)
		 {
			 incrementaConto(importo-importo1,conto_id);
		 }
	 }
	 else
	 {
		 decrementaConto(importo,conto);
		 incrementaConto(importo,conto_id);
	 }
	return database.update(TABLE_ENTRATA, args, "_id="+id, null);
}



public int decrementaConto(float importo,int conto_id)
{
	Cursor cursor=fetchConto(conto_id);
	ContentValues args=new ContentValues();
	float importo_att=0;
	while(cursor.moveToNext())
		importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
	cursor.close();
	importo_att-=importo;
	args.put(KEY_CONTOIM_AT, importo_att);
	return database.update(TABLE_CONTO, args, "_id="+conto_id, null);
}

public int incrementaConto(float importo,int conto_id)
{
	Cursor cursor=fetchConto(conto_id);
	ContentValues args=new ContentValues();
	float importo_att=0;
	while(cursor.moveToNext())
		importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
	cursor.close();
	importo_att+=importo;
	args.put(KEY_CONTOIM_AT, importo_att);
	return database.update(TABLE_CONTO, args, "_id="+conto_id, null);
}
public int updateUscita(int id,float importo,String data,String descrizione,int categoria_id,int conto_id,String nota,int map,double lat,double lng,String city,String tabella)
{
	ContentValues args=new ContentValues();
	args.put(KEY_USCITAIM, importo);
	args.put(KEY_USCITACAT, categoria_id);
	args.put(KEY_USCITACON, conto_id);
	args.put(KEY_USCITADESC,descrizione);
	args.put(KEY_USCITADATA, data);
	args.put(KEY_USCITANOT,nota);
	args.put(KEY_USCITALAT, lat);
	args.put(KEY_USCITALON,lng);
	args.put(KEY_USCITACIT, city);
	Cursor cursor=fetchOpUscita(id);
	float importo1=0;
	int conto=-1;
	while(cursor.moveToNext())
	{
		importo1=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
		conto=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACON)));
	}
	cursor.close();
	
	 if(conto==conto_id)
	 {
		 if(importo1>importo)
		 {
			 incrementaConto(importo1-importo,conto_id);
		 }
		 else if(importo1<importo)
		 {
			 decrementaConto(importo-importo1,conto_id);
		 }
	 }
	 else
	 {
		 incrementaConto(importo,conto);
		 decrementaConto(importo,conto_id);
	 }
	return database.update(TABLE_USCITA, args, "_id="+id, null);	
}

public int updateConto(int id,String nome,String tipo,float importo_init,float importo_att)
{
	ContentValues args=new ContentValues();
	args.put(KEY_CONTONOM, nome);
	args.put(KEY_CONTOTIP, tipo);
	Cursor cursor=fetchConto(id);
	float importo=0;
	while(cursor.moveToNext())
	importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM)));
	cursor.close();
	if(importo>importo_init)
		args.put(KEY_CONTOIM_AT, (importo-importo_init));
	else
		args.put(KEY_CONTOIM_AT, (importo_init-importo));
	args.put(KEY_CONTOIM, importo_init);
	return database.update(TABLE_CONTO, args, "_id="+id, null);
}

public int updateConto(int id, String nome, String tipo, float importo,
		float balance,int flag) {
	// TODO Auto-generated method stub
	ContentValues args=new ContentValues();
	args.put(KEY_CONTONOM, nome);
	args.put(KEY_CONTOTIP, tipo);
	
	Cursor cursor=fetchConto(id);
	float importo1=0;
	while(cursor.moveToNext())
	importo1=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
	cursor.close();
	if(flag==0)
		args.put(KEY_CONTOIM_AT, (importo1-balance));
	else 
		args.put(KEY_CONTOIM_AT, (importo1+balance));

	return database.update(TABLE_CONTO, args, "_id="+id, null);	
}

/*public Cursor updateCatEn()
{
	
}

public Cursor updateCatUs()
{
	
}

public Cursor deleteCatEn()
{
	
}

public Cursor deleteCatUs()
{
	
}*/
}
