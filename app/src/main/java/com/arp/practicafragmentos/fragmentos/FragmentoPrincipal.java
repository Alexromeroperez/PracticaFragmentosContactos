package com.arp.practicafragmentos.fragmentos;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arp.practicafragmentos.Contacto;
import com.arp.practicafragmentos.OnInteraccion;
import com.arp.practicafragmentos.R;
import com.arp.practicafragmentos.Telefonos;
import com.arp.practicafragmentos.actividades.Modifica;
import com.arp.practicafragmentos.actividades.Principal;
import com.arp.practicafragmentos.adaptadores.Adaptador;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoPrincipal extends Fragment {

    private View v;
    private ListView lv;
    private ArrayList<Contacto> contactos;
    private Adaptador adp;
    private int posicion;
    private OnInteraccion listener;
    private final int AÑADIR=1,MODIFICA=2;


    public FragmentoPrincipal() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnInteraccion){
            listener=(OnInteraccion)activity;
        }else{
            throw new ClassCastException("Solo acetpo OnFragmentoInteraccionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmento_principal, container, false);
        lv=(ListView)v.findViewById(R.id.lv);
        setHasOptionsMenu(true);
        ini();
        return v;
    }

    /*******************MENUS*************************/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_principal, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.contacto){
            Intent i=new Intent(getActivity(),Modifica.class);
            startActivityForResult(i,AÑADIR);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_emergente, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        posicion=info.position;
        switch (item.getItemId()) {
            case R.id.modificar:
                Intent i=new Intent(getActivity(), Modifica.class);
                Bundle b=new Bundle();
                b.putSerializable("contacto", contactos.get(posicion));
                i.putExtras(b);
                startActivityForResult(i, MODIFICA);
                return true;
            case R.id.borrar:
                contactos.remove(posicion);
                adp.notifyDataSetChanged();
                tostada("Contacto borrado");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

/*******************INICIO y AUXILIARES**************************/

    private void ini(){
        contactos= (ArrayList<Contacto>) Telefonos.getListaContactos(this.getActivity());
        añadirTlf(contactos);
        cogerFotos(contactos);
        adp=new Adaptador(this.getActivity(),contactos);
        lv.setAdapter(adp);
        registerForContextMenu(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.pasaContacto((Contacto) lv.getItemAtPosition(position));
            }
        });
    }

    private void cogerFotos(ArrayList<Contacto> contactos){
        File f[] = new File(String.valueOf(getActivity().getExternalFilesDir(null))).listFiles();
        if(f!=null) {
            /*recorro la lista de contactos**/
            for (int i = 0; i < contactos.size(); i++) {
                /*recorro la lista de ficheros*/
                for (int j = 0; j < f.length; j++) {
                    if (f[j].getName().indexOf(contactos.get(i).getNombre()) != -1) {
                        contactos.get(i).setFoto(f[j].getAbsoluteFile());
                    }
                }
            }
        }
    }

    private void añadirTlf(ArrayList <Contacto> lista){
        for (int i = 0; i <lista.size() ; i++) {
            Contacto c=lista.get(i);
            c.setTlfs((ArrayList<String>) Telefonos.getListaTelefonos(getActivity(), c.getId()));
        }
    }

    private void tostada(String texto){
        Toast.makeText(getActivity(),texto,Toast.LENGTH_SHORT).show();
    }

    /*******************RESULTADO**************************/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Contacto c;
        if(resultCode==Principal.RESULT_OK){
            Log.v("ACTIVIDAD CANCELADA", "OK");
            Bundle b=data.getExtras();
            switch (requestCode){
                case AÑADIR:
                    c=(Contacto)b.get("contacto");
                    contactos.add(c);
                    adp.notifyDataSetChanged();
                    tostada("Contacto añadido");
                    return;
                case MODIFICA:
                    c=(Contacto)b.get("contacto");
                    contactos.set(posicion, c);
                    adp.notifyDataSetChanged();
                    tostada("Contacto Modificado");
                    return;
            }
        }
    }
}
