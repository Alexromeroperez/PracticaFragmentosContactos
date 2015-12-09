package com.arp.practicafragmentos.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arp.practicafragmentos.Contacto;
import com.arp.practicafragmentos.R;
import com.arp.practicafragmentos.Telefonos;
import com.arp.practicafragmentos.adaptadores.AdaptadorTlf;

import java.util.ArrayList;

/**
 * Created by Alex on 04/12/2015.
 */
public class Modifica extends AppCompatActivity {

    private EditText et1,et2;
    private ListView lv;
    private AdaptadorTlf adt;
    private Contacto c;
    private Bundle b;
    private ArrayList<String> tlfs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica);
        ini();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modifica, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String nombre=et1.getText().toString();
        if (id == R.id.aceptar) {
            if(nombre.compareTo("")!=0) {
                c = new Contacto();
                c.setNombre(nombre);
                c.setTlfs(tlfs);
                Intent i = new Intent();
                b = new Bundle();
                b.putSerializable("contacto", c);
                i.putExtras(b);
                setResult(Principal.RESULT_OK, i);
                finish();
                return true;
            }else {
                Toast.makeText(this, "El nombre no puede estar vacio", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void añadir(View v){
        if(et2.getText().toString().compareTo("")==0){
            Toast.makeText(this,"Rellena el campo Telefono",Toast.LENGTH_SHORT).show();
        }else {
            tlfs.add(et2.getText().toString());
            et2.setText("");
            adt.notifyDataSetChanged();
        }
    }

    private void ini(){
        et1=(EditText)findViewById(R.id.etNombre);
        et2=(EditText)findViewById(R.id.etTlf);
        lv=(ListView)findViewById(R.id.lvTlf);
        b=getIntent().getExtras();
        tlfs=new ArrayList<>();
        if(b!=null){
            c=(Contacto)b.get("contacto");
            et1.setText(c.getNombre());
            tlfs= (ArrayList<String>) Telefonos.getListaTelefonos(this, c.getId());
        }
        adt=new AdaptadorTlf(this,tlfs);
        lv.setAdapter(adt);
    }
}
