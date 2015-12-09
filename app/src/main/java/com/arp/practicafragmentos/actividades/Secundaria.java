package com.arp.practicafragmentos.actividades;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.arp.practicafragmentos.Contacto;
import com.arp.practicafragmentos.R;
import com.arp.practicafragmentos.fragmentos.FragmentoDetalle;

public class Secundaria extends AppCompatActivity {

    private Contacto c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        Bundle b=getIntent().getExtras();
        c= (Contacto) b.getSerializable("contacto");
        if(getScreenOrientation()==getResources().getConfiguration().ORIENTATION_LANDSCAPE){
            Intent i=new Intent(this,Principal.class);
            startActivity(i);
        }
        FragmentoDetalle fd=(FragmentoDetalle)getSupportFragmentManager().findFragmentById(R.id.fragmentoDetalle);
        fd.setContacto(c);
        fd.mostrar();
    }

    public int getScreenOrientation(){
        Display getOrient = getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

}
