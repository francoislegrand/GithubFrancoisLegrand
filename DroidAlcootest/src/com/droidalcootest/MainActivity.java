package com.droidalcootest;

import java.text.DecimalFormat;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// déclaration des objets enfants de la classe widget 
	private Button Calculer , Reset, Taxi ;
	private EditText Wpoids, Wqte, Wdegre ;
	private RadioButton Homme , Femme ; 
	private TextView Taux ;
	private CheckBox Ajeun ;
    private double TauxAlcool, p_poids, p_deg, p_qte;
	private boolean p_estUnHomme, ParametresVides; 
		

    protected void onCreate(Bundle savedInstanceState) 
    {
    	// init de la page principale
    	setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        // Init des widgets 
        Calculer = (Button) findViewById(R.id.bpCalcuer) ;
        Reset  = (Button) findViewById(R.id.bpReset) ;
        Taxi = (Button) findViewById(R.id.bpTaxi) ;
        Ajeun = (CheckBox) findViewById(R.id.cbAjeun) ;
        Wpoids = (EditText) findViewById(R.id.etPoids); 
        Wqte = (EditText) findViewById(R.id.etQte);
        Wdegre = (EditText) findViewById(R.id.etDegre);
        Homme = (RadioButton) findViewById(R.id.rbHomme) ;
        Femme = (RadioButton) findViewById(R.id.rbFemme) ;
        
        Taux = (TextView) findViewById(R.id.tvTaux) ;
        Taxi.setVisibility(0); 
        TauxAlcool = 0.00 ;
        Taux.setText("0.00");
        // au clic sur le bouton calculer
        Calculer.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v1) 
			{
				CaclulButtonActions();
			}
		});
	        
        // au clic sur le bouton reset
        Reset.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v2) {
				ResetButtonActions();
			}
        });
  
        Taxi.setOnClickListener(new View.OnClickListener(){	
			@Override
			public void onClick(View v){
				finish();
				Intent intent = new Intent(MainActivity.this, PageAppel.class);
				startActivity(intent);		
			}
        });
     }
    @Override
    // création d'un menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void CaclulButtonActions(){
    	if(Wqte.getText().toString().matches("")|| Wpoids.getText().toString().matches("") || Wdegre.getText().toString().matches(""))
    	{
    		ParametresVides = true ;
        	p_qte = 1;
        	p_poids = 1;
        	p_deg =  1;
    	}
    	else{
    		ParametresVides = false ;
        	p_qte = Double.parseDouble(Wqte.getText().toString());
        	p_poids = Double.parseDouble(Wpoids.getText().toString());
        	p_deg =  Double.parseDouble(Wdegre.getText().toString());
    	}
    	// test si le calcul est réalisable
		if(runnable(p_poids, p_qte,p_deg, Homme, Femme)){
			p_estUnHomme = Homme.isChecked();
			// appel de la fonction TauxAlcoolemie qui calcul le Taux d'alcoolémie
			TauxAlcool += TauxAlcoolemie(p_qte,p_poids, p_estUnHomme,p_deg,Ajeun.isChecked()) ;
			
			DecimalFormat df = new DecimalFormat("0.00");
			Taux.setText("" + df.format(TauxAlcool) );
			//gestion des appels de taxis si TauxAlcool > 0.5
		}
    }
    public void ResetButtonActions(){
    	
    	TauxAlcool = 0.00 ;
		Taux.setText("0.00");
		Wpoids.setText(""); 
		Wqte.setText(""); 
		Wdegre.setText(""); 
		Homme.setChecked(false);
		Femme.setChecked(false);
    }
    
    //Retourne vrai si le paramètres permettent bien un calcul du taux d'alcoolémie 
    public boolean runnable(double poids, double qte, double degre ,RadioButton Homme ,RadioButton Femme){
    	if(ParametresVides){
			this.popupWarning("Veuillez saisir toutes les valeurs.");
			return false; 
    	}
    	else{ 
    		// si les paramètres sont impossibles
    		if( poids <= 0.0){
    			this.popupWarning("Vous ne pouvez pas peser 0 Kg.");
    			return false; 
    	    }
    		else	
    			if( ! Homme.isChecked() &&  ! Femme.isChecked()){
    		   		this.popupWarning("Je ne sais pas si vous êtes un homme ou une femme.");
    		   		return false; 			
    			}else
    				return true ;        
    	}
    }
    
	public double TauxAlcoolemie (double qte, double poids, boolean estUnHomme, double deg, boolean Ajeun){
		double coeffAjeun ;
		
		if(Ajeun){
			coeffAjeun = 1.0; 
		}else{
			coeffAjeun = 0.815 ;// le fait d'avoir mangé réduit le taux d'alcoolémie
		}
		
		// pour ne pas diviser par 0
		if(poids == 0.0 )
			return 0.0 ;
		else{
			double TauxFemme = ((qte*10*deg*0.01*0.8) / (0.6*poids))* coeffAjeun ;
			double TauxHomme = ((qte*10*deg*0.01*0.8) / (0.7*poids))* coeffAjeun;	
			
			if(estUnHomme)	
				return TauxHomme; 
			else 
				return TauxFemme; 
		}
	}
    // affichage d'une popup de warning avec un message passé en paramètre
    public void popupWarning ( String input){
		new AlertDialog.Builder(this)
        .setTitle("Attention !")
        .setMessage(input)
        .setNeutralButton("OK",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // continue with delete
            }
         }).show();
    }
}
