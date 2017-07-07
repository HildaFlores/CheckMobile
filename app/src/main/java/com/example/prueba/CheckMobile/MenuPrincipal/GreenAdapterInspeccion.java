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
        TextView listItemNumberView;
        TextView listClienteName;
        TextView listIdInspeccion;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listIdInspeccion = (TextView) itemView.findViewById(txtRowInspeccion0);
            listItemNumberView = (TextView) itemView.findViewById(R.id.txtRowInspeccion);
            listClienteName = (TextView) itemView.findViewById(R.id.txtRowInspeccion2);
            itemView.setId(Integer.parseInt(inspeccionVeh.get(contador).getId()));
            contador++;
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(int listIndex) {
            String encabezado = inspeccionVeh.get(listIndex).getTipo_veh() + " " +
                    inspeccionVeh.get(listIndex).getNombre_vehiculo() +
                    " (Ref." + inspeccionVeh.get(listIndex).getReferencia() + ")";

            String detalle = "Cliente >> " + inspeccionVeh.get(listIndex).getNombre_cliente();
            listIdInspeccion.setText("(IV-" + inspeccionVeh.get(listIndex).getId() + ")");
            listItemNumberView.setText(encabezado);
            listClienteName.setText(detalle);


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


