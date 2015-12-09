package com.arp.practicafragmentos.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.arp.practicafragmentos.Contacto;
import com.arp.practicafragmentos.OnInteraccion;
import com.arp.practicafragmentos.R;
import com.arp.practicafragmentos.fragmentos.FragmentoDetalle;

public class Principal extends AppCompatActivity implements OnInteraccion {

    private FragmentoDetalle fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        fd=(FragmentoDetalle)getSupportFragmentManager().findFragmentById(R.id.fragmentoDetalle);
    }

    @Override
    public void pasaContacto(Contacto c) {
        if(fd==null || !fd.isInLayout())
        {
            Intent i=new Intent(this,Secundaria.class);
            Bundle b=new Bundle();
            b.putSerializable("contacto",c);
            i.putExtras(b);
            startActivity(i);
        }else {
            fd.setContacto(c);
            fd.mostrar();
        }
    }

}
