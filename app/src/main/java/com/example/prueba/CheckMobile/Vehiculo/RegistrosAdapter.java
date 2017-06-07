package com.example.prueba.CheckMobile.Vehiculo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;

/**
 * Created by Prueba on 28-may-17.
 */

public class RegistrosAdapter extends RecyclerView.Adapter<RegistrosAdapter.VehiculoViewHolder> {
    private static final String TAG = RegistrosAdapter.class.getSimpleName();
    ArrayList<Vehiculo> vehiculos = new ArrayList<>();

    private int mNumberItems;


    public RegistrosAdapter(int numberOfItems, ArrayList<Vehiculo> veh) {
        mNumberItems = numberOfItems;
        vehiculos = veh;
    }
    @Override
    public VehiculoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.row_cliente;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        VehiculoViewHolder viewHolder = new VehiculoViewHolder(view);

        return viewHolder;
    }



    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(VehiculoViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * Cache of the children views for a list item.
     */
    class VehiculoViewHolder extends RecyclerView.ViewHolder {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         *
         }
         */
        public VehiculoViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         *
         * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {

            listItemNumberView.setText(vehiculos.get(listIndex).getDesc_marca() + " " + vehiculos.get(listIndex).getDesc_modelo()
            + " " + vehiculos.get(listIndex).getDesc_estilo() +  " \n " + "REF. " + vehiculos.get(listIndex).getReferencia() );

        }
    }



}


