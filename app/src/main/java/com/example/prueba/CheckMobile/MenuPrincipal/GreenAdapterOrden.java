package com.example.prueba.CheckMobile.MenuPrincipal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEnc;
import com.example.prueba.CheckMobile.R;


import java.util.ArrayList;

/**
 * Created by Prueba on 26-jun-17.
 */

public class GreenAdapterOrden extends RecyclerView.Adapter<GreenAdapterOrden.NumberViewHolder> {

    private final String TAG = GreenAdapterOrden.class.getSimpleName();
    private ArrayList<OrdenTrabajoEnc> ordenTrabajo = new ArrayList<>();
    final private ListItemClickListenerOrden mOnClickListener;
    private int mNumberItems;
    int contador = 0;

    public interface ListItemClickListenerOrden {
        void onListItemClickOrden(int clickedItemIndex);
        void onReciclyListItemClickOrden(int Iditem, int clickedItemIndex);
    }


    public GreenAdapterOrden(int numberOfItems, ArrayList<OrdenTrabajoEnc> OrdenTrabajoEncs, ListItemClickListenerOrden listener) {
        mNumberItems = numberOfItems;
        ordenTrabajo = OrdenTrabajoEncs;
        mOnClickListener = listener;
    }

    public void setFilter(ArrayList<OrdenTrabajoEnc> ordenes) {
        ordenTrabajo = new ArrayList<>();
        ordenTrabajo.addAll(ordenes);
        this.mNumberItems = ordenes.size();
        contador = 0;
        notifyDataSetChanged();

    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.row_orden_trabajo;
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
        TextView listClienteName;
        TextView listidOrden;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listidOrden = (TextView) itemView.findViewById(R.id.txtRowOrden0);
            listClienteName = (TextView) itemView.findViewById(R.id.txtRowOrden1);
            itemView.setId(Integer.parseInt(ordenTrabajo.get(contador).getId()));
            contador++;
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(int listIndex) {
            String encabezado = "Cliente >> " + ordenTrabajo.get(listIndex).getNombreCliente() + " " + ordenTrabajo.get(listIndex).getApellidosCte();
            listidOrden.setText("(OTT-" + ordenTrabajo.get(listIndex).getId() + ")");
            listClienteName.setText(encabezado);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClickOrden(clickedPosition);

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            MenuItem itemAGenerar = contextMenu.add(0, R.id.action_ver_inspeccion, 0, "Ver inspección");
            MenuItem itemAnular = contextMenu.add(0, R.id.action_anular, 0, "Descartar");
            itemAnular.setOnMenuItemClickListener(mOnMyActionClickListener);
            itemAGenerar.setOnMenuItemClickListener(mOnMyActionClickListener);

        }

        private final MenuItem.OnMenuItemClickListener mOnMyActionClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mOnClickListener.onReciclyListItemClickOrden(item.getItemId(), getAdapterPosition());
                return true;
            }
        };

    }

}


