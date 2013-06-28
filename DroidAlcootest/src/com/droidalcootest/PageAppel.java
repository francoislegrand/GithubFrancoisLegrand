package com.droidalcootest;


import java.util.Vector;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class PageAppel extends Activity {
	

	public Vector <String> nomDesTaxis = new Vector<String>();
	public void onCreate(Bundle TaxiState) {
        setContentView(R.layout.gestion_appel);
        super.onCreate(TaxiState);
    	
        Button Retour;
        final Spinner s1 ;  
        
        Retour = (Button) findViewById(R.id.bpRetour) ;
        s1 = (Spinner) findViewById(R.id.spTaxiNumeros) ;
      
        DataRec(); // Ecriture des taxi dans le vector<String> nomDesTaxis
        // intégration des noms de taxi dans les items du spinner
      ArrayAdapter<String> adapter  = 
      new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,nomDesTaxis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> arg0, 
            View arg1, int arg2, long arg3) 
            {
                int index = s1.getSelectedItemPosition(); 
                /*Toast.makeText(getBaseContext(), 
                    "Vous avez selcetioné le " +AsNom(nomDesTaxi.elementAt(index)), 
                    Toast.LENGTH_SHORT).show(); */
                popupAppel(AsNumero(nomDesTaxis.elementAt(index)),AsNom(nomDesTaxis.elementAt(index))); 	
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });  
   
        Retour.setOnClickListener(new View.OnClickListener() 
        {	
			@Override
			public void onClick(View v2) 
			{
				finish();
				Intent intent = new Intent(PageAppel.this, MainActivity.class);
				startActivity(intent);	
			}
        });
    }
	
	// procédure demandant à l'utilisateur s'il veut appeler le numéro passé en paramètre
	public void popupAppel( final String Numero, String Nom)
	{
		new AlertDialog.Builder(this) //Popup
	    .setTitle("Rentrez chez vous")
	    .setMessage("Voulez vous appeler le "+Nom+" ("+Numero+") ?")
	    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	Intent intent = new Intent( Intent.ACTION_CALL, Uri.parse( "tel:"+Numero.trim() ) );
	        	startActivity( intent ); // Lancement de d'appel
	        }
	     })
	    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // ne rien faire
	        }
	     })
	     .show();
	}

	// Retourne uniquement le nom de la chaine input 
	public String AsNom (String input){
		return  input.substring(input.indexOf('(')+1, input.indexOf(')')) ; 
	}
	
	// Retourne uniquement le numéro de la chaine input 
	public String AsNumero (String input){
		return input.substring(0,input.indexOf('(')-1) ; 
	}
	public void DataRec(){
		
		/* A reférencer dans une BDD SQLite Android*/
        nomDesTaxis.add("0240692222 (taxi Allo Nantes Taxi)");
        nomDesTaxis.add("0620361198 (taxi A.B.N. Taxi)");
        nomDesTaxis.add("0609823131 (taxi Michel Gate)");
        nomDesTaxis.add("0608732454 (taxi Pascal Gregoire)");
        nomDesTaxis.add("0699182534 (taxi A.A. Taxi)");
        nomDesTaxis.add("0664184110 (taxi Nantais Fabrice)");
        nomDesTaxis.add("0607626469 (taxi Abarth)");
        nomDesTaxis.add("0609700900 (taxi AGS taxi d'Ivoire)");
        nomDesTaxis.add("0684653680 (taxi AKT taxi)");
        nomDesTaxis.add("0672621923 (taxi Marc Bonraisin)");
        nomDesTaxis.add("0240495960 (taxi Xavier Couvrand)");
        nomDesTaxis.add("0678041188 (taxi EL HOUCINE Tahri)");
        nomDesTaxis.add("0825740044 (taxi GIE TAXIS 44)");
        nomDesTaxis.add("0664861143 (taxi Drouet Yohann)");
    }
}