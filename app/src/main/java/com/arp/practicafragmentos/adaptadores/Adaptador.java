package com.arp.practicafragmentos.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arp.practicafragmentos.Contacto;
import com.arp.practicafragmentos.R;
import com.arp.practicafragmentos.Telefonos;

import java.util.ArrayList;

/**
 * Created by Alex on 04/12/2015.
 */
public class Adaptador extends ArrayAdapter<Contacto> {
    private Context contexto;
    private int recurso;
    private ArrayList<Contacto> lista;
    private LayoutInflater i;

    public Adaptador(Context context, ArrayList<Contacto> lista) {
        super(context,R.layout.lista_detalle, lista);
        this.contexto = context;
        this.recurso = R.layout.lista_detalle;
        this.lista = lista;
        i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (vh == null) {
            vh = new ViewHolder();
            convertView = i.inflate(recurso, null);
            vh.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            vh.tvTelefono = (TextView) convertView.findViewById(R.id.tvTlf);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Contacto c = lista.get(position);
        vh.tvNombre.setText(c.getNombre());
        vh.tvTelefono.setText(c.getTlfs().get(0));

        return convertView;
    }

    static class ViewHolder {
        private TextView tvNombre, tvTelefono;

    }
}