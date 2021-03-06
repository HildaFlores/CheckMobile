package com.example.prueba.CheckMobile.MenuPrincipal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.GreenAdapter;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.example.prueba.CheckMobile.R.id.txtRowInspeccion0;

/**
 * Created by Prueba on 26-jun-17.
 */

public class GreenAdapterInspeccion extends RecyclerView.Adapter<GreenAdapterInspeccion.NumberViewHolder> {

    private final String TAG = GreenAdapterInspeccion.class.getSimpleName();
    private ArrayList<InspeccionVehiculo> inspeccionVeh = new ArrayList<>();
    final private ListItemClickListener mOnClickListener;
    private int mNumberItems;
    int contador = 0;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
        void onReciclyListItemClick(int Iditem, int clickedItemIndex);
    }


    public GreenAdapterInspeccion(int numberOfItems, ArrayList<InspeccionVehiculo> inspeccionVehiculos, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        inspeccionVeh = inspeccionVehiculos;
        mOnClickListener = listener;
    }

    public void setFilter(ArrayList<InspeccionVehiculo> inspeccion) {
        inspeccionVeh = new ArrayList<>();
        inspeccionVeh.addAll(inspeccion);
        this.mNumberItems = inspeccion.size();
        contador = 0;
        notifyDataSetChanged();

    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.row_inspeccion;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listFecha;
        TextView listItemNumberView;
        TextView listClienteName;
        TextView listIdInspeccion;
        TextView listAsesor;
        TextView etiqueta1;
        TextView etiqueta2;
        TextView etiqueta3;
        TextView etiqueta4;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listFecha = (TextView) itemView.findViewById(R.id.txtRowInspeccion0);
            listIdInspeccion = (TextView) itemView.findViewById(R.id.txtRowInspeccion1);
            listItemNumberView = (TextView) itemView.findViewById(R.id.txtRowInspeccion2);
            listClienteName = (TextView) itemView.findViewById(R.id.txtRowInspeccion3);
            listAsesor = (TextView) itemView.findViewById(R.id.txtRowInspeccion4);
            etiqueta1 = (TextView) itemView.findViewById(R.id.txtEtiqueta1);
            etiqueta2 = (TextView) itemView.findViewById(R.id.txtEtiqueta2);
            etiqueta3 = (TextView) itemView.findViewById(R.id.txtEtiqueta3);
            etiqueta4 = (TextView) itemView.findViewById(R.id.txtEtiqueta4);

            itemView.setId(Integer.parseInt(inspeccionVeh.get(contador).getId()));
            contador++;
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(int listIndex) {
            String encabezado = inspeccionVeh.get(listIndex).getTipo_veh() + " " +
                    inspeccionVeh.get(listIndex).getNombre_vehiculo() +
                    " (Ref." + inspeccionVeh.get(listIndex).getReferencia() + ")";

            String detalle =  inspeccionVeh.get(listIndex).getNombre_cliente();
            listFecha.setText(inspeccionVeh.get(listIndex).getFechaInspeccion().substring(0,10));
            listIdInspeccion.setText("(IV-" + inspeccionVeh.get(listIndex).getId() + ")");
            listItemNumberView.setText(encabezado);
            listClienteName.setText(detalle);
            listAsesor.setText(inspeccionVeh.get(listIndex).getUsuarioInsercion());
            etiqueta1.setText("Inspección >> ");
            etiqueta2.setText("Vehículo >> ");
            etiqueta3.setText("Cliente >> ");
            etiqueta4.setText("Asesor >> ");


        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            MenuItem itemAGenerar = contextMenu.add(0, R.id.action_convert, 0, "Generar orden");
            MenuItem itemAnular = contextMenu.add(0, R.id.action_delete, 0, "Descartar");
            itemAnular.setOnMenuItemClickListener(mOnMyActionClickListener);
            itemAGenerar.setOnMenuItemClickListener(mOnMyActionClickListener);

        }

        private final MenuItem.OnMenuItemClickListener mOnMyActionClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mOnClickListener.onReciclyListItemClick(item.getItemId(), getAdapterPosition());
                return true;
            }
        };

    }

}


