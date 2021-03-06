package com.arp.practicafragmentos.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arp.practicafragmentos.R;

import java.util.ArrayList;

/**
 * Created by Alex on 04/12/2015.
 */
public class AdaptadorTlf extends ArrayAdapter<String> {
    private Context contexto;
    private int recurso;
    private ArrayList<String> lista;
    private LayoutInflater i;

    public AdaptadorTlf(Context context, ArrayList<String> lista) {
        super(context, R.layout.lista_tlf, lista);
        this.contexto=context;
        this.recurso=R.layout.lista_tlf;
        this.lista=lista;
        i=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(vh==null){
            vh=new ViewHolder();
            convertView=i.inflate(recurso,null);
            vh.btBorrar=(ImageButton)convertView.findViewById(R.id.btBorrar);
            vh.tvTelefono=(TextView)convertView.findViewById(R.id.tvTlf);
            convertView.setTag(vh);
        }else {
            vh=(ViewHolder)convertView.getTag();
        }

        vh.tvTelefono.setText(lista.get(position));
        vh.btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder{
        private TextView tvTelefono;
        private ImageButton btBorrar;
    }
}