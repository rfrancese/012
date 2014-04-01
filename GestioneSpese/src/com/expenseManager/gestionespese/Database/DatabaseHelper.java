package com.expenseManager.gestionespese.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	/*
	 *  Setto due variabili costanti, una riferita al nome del database, 
	 *  un'altra alla versione
	 */
	
	private static final String DB_NAME="gestioneSpese.db";
	private static final int DB_VERSION=1;
    
	/**
     * Statment SQL per la creazione delle tabelle 
     */
	
	private static final String Tab_Account="create table account(_id primary key, nome text,cognome text, cf_piva text)";
	private static final String Tab_Operazioni="create table operazioni(_id primary key,importo float, data text not null, descrizione text, categoria integer, conto integer, foreign key(categoria) references categoria(_id), foreign key(conto) references conto(_id)) ";
	private static final String Tab_Entrata="create table op_entrata(_id primary key,importo float, data text not null, descrizione text, categoria integer, conto integer, foreign key(categoria) references categoria(_id), foreign key(conto) references conto(_id)) ";
	private static final String Tab_Uscita="create table op_uscita(_id primary key,importo float, data text not null, descrizione text, categoria integer, conto integer, foreign key(categoria) references categoria(_id), foreign key(conto) references conto(_id)) ";
	private static final String Tab_Cat="create table categoria(_id primary key,nome text,color_id integer,icon_id integer)";
	private static final String Tab_Conto="create table conto(_id primary key,importo float,nome text,tipo text)";
	private static final String Tab_Contatti="create table contatti(_id primary key,nome text,cognome text,numero_casa text,numero_cell text,indirizzo text,citta text)";
	
	/**
	 * 
	 * @param context contesto
	 * @param name nome del database
	 * @param version versione del database
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
     db.execSQL(Tab_Account);
     db.execSQL(Tab_Operazioni);
     db.execSQL(Tab_Entrata);
     db.execSQL(Tab_Uscita);
     db.execSQL(Tab_Conto);
     db.execSQL(Tab_Cat);
     db.execSQL(Tab_Contatti);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
