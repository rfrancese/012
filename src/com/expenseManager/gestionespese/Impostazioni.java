package com.expenseManager.gestionespese;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.expenseManager.gestionespese.Utility.ExportCSV;
import com.expenseManager.gestionespese.Utility.ExportXML;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class Impostazioni extends Activity {
	private Context context=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impostazioni);
		context=this;
		Button creaBackup=(Button)findViewById(R.id.crea_copia);
		creaBackup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputStream in = null;
				OutputStream out = null;
				Log.v("ExternalSd",Environment.getExternalStorageDirectory().getAbsolutePath());
				String pathOu = Environment.getExternalStorageDirectory().getAbsolutePath();
				Log.v("pathOut",pathOu);
				final String inputPath="/data/data/com.expenseManager.gestionespese/databases/";
				final String outputPath=pathOu+"/gestioneSpeseBackup/";
				final String inputFile="gestioneSpese.db";
				try {

					//create output directory if it doesn't exist
					File dir = new File (outputPath); 
					if (!dir.exists())
					{
						dir.mkdirs();
					}


					in = new FileInputStream(inputPath + inputFile);  

					out = new FileOutputStream(outputPath + inputFile);

					byte[] buffer = new byte[1024];
					int read;
					File outFile=new File(outputPath + inputFile);
					Log.v("OutFile exists",""+outFile.exists());
					if(!outFile.exists())
					{
						while ((read = in.read(buffer)) != -1) {
							out.write(buffer, 0, read);
						}
						in.close();
						in = null;
						Toast.makeText(context, "File backup creato ", Toast.LENGTH_SHORT).show();
						// write the output file (You have now copied the file)
						out.flush();
						out.close();
						out = null;  
					}
					else
					{
						Log.v("Eseguo qui","QUI");
						AlertDialog.Builder dialog=new AlertDialog.Builder(context);
						dialog.setTitle("Sovrascrivere file backup");
						dialog.setPositiveButton("Si", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								try{
									FileInputStream in = new FileInputStream(inputPath + inputFile);  

									FileOutputStream   out = new FileOutputStream(outputPath + inputFile);

									byte[] buffer = new byte[1024];
									int read;


									while ((read = in.read(buffer)) != -1) {
										out.write(buffer, 0, read);
									}
									in.close();
									in = null;
									Toast.makeText(context, "File backup creato ", Toast.LENGTH_SHORT).show();
									// write the output file (You have now copied the file)
									out.flush();
									out.close();
									out = null;  	
								}
								catch(FileNotFoundException fnfe1) {
									Log.e("tag", fnfe1.getMessage());
								}
								catch (Exception e) {
									Log.e("tag", e.getMessage());
								}}
						});
						dialog.setMessage("E' presente già un file di backup, intendi sovrascriverlo?\n");
						dialog.setNegativeButton("No", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();	
							}
						});
						dialog.create();
						dialog.show();
					}


				}  catch (FileNotFoundException fnfe1) {
					Log.e("tag", fnfe1.getMessage());
				}
				catch (Exception e) {
					Log.e("tag", e.getMessage());
				}

			}
		});

		Button ripristinaBackup=(Button)findViewById(R.id.ripristina);

		ripristinaBackup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputStream in = null;
				OutputStream out = null;
				String pathOu = Environment.getExternalStorageDirectory().getAbsolutePath();
				final String outputPath="/data/data/com.expenseManager.gestionespese/databases/";
				final String inputPath=pathOu+"/gestioneSpeseBackup/";
				final String inputFile="gestioneSpese.db";
				File file=new File(inputPath);
				if(!file.exists())
				{
					Toast.makeText(context,"Non è presente nessun file di backup!",Toast.LENGTH_SHORT).show();
				}
				else
				{
					AlertDialog.Builder dialog=new AlertDialog.Builder(context);
					dialog.setTitle("Ripristino backup");
					String infoFile=file.getName()+" Ultima modifica: "+file.lastModified();
					dialog.setMessage("E' stato trovato un file di backup : "+infoFile);
					dialog.setPositiveButton("OK", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							try {

								//create output directory if it doesn't exist
								File dir = new File (outputPath); 
								if (!dir.exists())
								{
									dir.mkdirs();
								}


								FileInputStream in = new FileInputStream(inputPath + inputFile);        
								FileOutputStream out = new FileOutputStream(outputPath + inputFile);

								byte[] buffer = new byte[1024];
								int read;
								while ((read = in.read(buffer)) != -1) {
									out.write(buffer, 0, read);
								}
								in.close();
								in = null;

								// write the output file
								out.flush();
								out.close();
								out = null;

								// delete the original file
								new File(inputPath + inputFile).delete();  


							} 

							catch (FileNotFoundException fnfe1) {
								Log.e("tag", fnfe1.getMessage());
							}
							catch (Exception e) {
								Log.e("tag", e.getMessage());
							}
						}
					});
					dialog.setNegativeButton("NO", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.create();
					dialog.show();


				}
			}
		});
		
		Button exportXML=(Button)findViewById(R.id.crea_xml);
		exportXML.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			new ExportXML(context);	
			}
		});
       Button exportCSV=(Button)findViewById(R.id.crea_cvs);
       exportCSV.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new ExportCSV(context);
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.impostazioni, menu);
		return true;
	}


}
