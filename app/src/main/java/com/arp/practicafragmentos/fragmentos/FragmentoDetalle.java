package com.arp.practicafragmentos.fragmentos;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arp.practicafragmentos.Contacto;
import com.arp.practicafragmentos.R;
import com.arp.practicafragmentos.Telefonos;
import com.arp.practicafragmentos.actividades.Principal;
import com.arp.practicafragmentos.actividades.Secundaria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class FragmentoDetalle extends Fragment {

    private View v;
    private ImageView iv;
    private ImageButton ib;
    private TextView tvNombre,tvTlf;
    private Contacto c;
    private final int FOTO=1;

    public FragmentoDetalle() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragmento_detalle, container, false);
        ini();
        return v;
    }

    public void mostrar(){
        String todos="";
        for (int i = 0; i < c.getTlfs().size() ; i++) {
            todos+=c.getTlfs().get(i)+"\n";
        }
        tvNombre.setText(c.getNombre());
        tvTlf.setText(todos);
        iv.setImageResource(R.drawable.persona);
        Bitmap bm=BitmapFactory.decodeFile(String.valueOf(c.getFoto()));
        if(bm!=null) {
            iv.setImageBitmap(bm);
        }else{
            iv.setImageResource(R.drawable.persona);
        }
    }

    public void setContacto(Contacto c){
        this.c=c;
    }

    private void ini(){
        iv=(ImageView)v.findViewById(R.id.imageView);
        ib=(ImageButton)v.findViewById(R.id.ibFoto);
        tvNombre=(TextView)v.findViewById(R.id.tvNombre);
        tvTlf=(TextView)v.findViewById(R.id.tvTlf);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c!=null) {
                    Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(i, FOTO);
                }else{
                    tostada("Elige un contacto");
                }
            }
        });
    }

    private void tostada(String texto){
        Toast.makeText(getActivity(), texto, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == FOTO) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            FileOutputStream salida;
            try {
                salida = new FileOutputStream(getActivity().getExternalFilesDir(null) + "/"
                        +c.getId() +"_"+c.getNombre() + ".jpg");
                foto.compress(Bitmap.CompressFormat.JPEG, 90, salida);
            } catch (FileNotFoundException e) {
            }
            iv.setImageBitmap(foto);
        }
    }

}
