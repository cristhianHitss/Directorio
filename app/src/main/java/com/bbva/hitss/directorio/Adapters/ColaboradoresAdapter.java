package com.bbva.hitss.directorio.Adapters;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bbva.R;
import com.bbva.hitss.directorio.Controllers.DetalleColaboradorController;
import com.bbva.hitss.directorio.Models.ColaboradorModel;
import com.bbva.hitss.directorio.Utils.Utils;
import com.bumptech.glide.Glide;

import java.util.List;


public class ColaboradoresAdapter extends RecyclerView.Adapter<ColaboradoresAdapter.SimpleViewHolder>
        implements ItemClickListener {
    private final Context context;
    private List<ColaboradorModel> items;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public ImageView avatar;
        public ItemClickListener listener;
        public TextView flecha;

        public SimpleViewHolder(View v, ItemClickListener listener,Context context) {
            super(v);
            flecha=(TextView) v.findViewById(R.id.flecha);
            nombre = (TextView) v.findViewById(R.id.list_item_textview);
            avatar = (ImageView) v.findViewById(R.id.avatar);
            Utils.getTypeFace(flecha, context.getAssets());
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }

    public ColaboradoresAdapter(Context context, List<ColaboradorModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_colaboradores_view, viewGroup, false);
        return new SimpleViewHolder(v, this,context );
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int i) {
        ColaboradorModel currentItem = items.get(i);
        viewHolder.nombre.setText(Html.fromHtml("<font color=\"blue\">"+currentItem.getName() + " " +currentItem.getApellido_paterno() +" "+currentItem.getApelido_materno()+"</font><br><font color=\"gray\">" + currentItem.getPerfil()+"</font>"));
        Glide.with(viewHolder.avatar.getContext())
                .load(currentItem.getIdDrawable())
                .centerCrop()
                .into(viewHolder.avatar);
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
        DetalleColaboradorController.createInstance(
                (Activity) context, items.get(position));
    }
}

interface ItemClickListener {
    void onItemClick(View view, int position);
}