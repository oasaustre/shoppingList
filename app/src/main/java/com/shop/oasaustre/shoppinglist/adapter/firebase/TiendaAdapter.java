package com.shop.oasaustre.shoppinglist.adapter.firebase;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteTiendaDialog;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.ViewHolder> implements ValueEventListener {
    private LayoutInflater inflador;
    private List<TiendaDto> lista;
    private Context context;
    private View.OnClickListener onClickListener;
    private Query reference;

    public TiendaAdapter(Context context, Query reference) {
        this.reference = reference;
        this.context = context;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.element_tienda, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        TiendaDto tienda = lista.get(i);

        holder.getIdTienda().setText(tienda.getUid());
        holder.getTituloTienda().setText(tienda.getNombre());

        if (i % 2 == 0) {
            holder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.line_divider_even));
        } else {
            holder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.line_divider_odd));
        }

    }

    @Override
    public int getItemCount() {
        int result = 0;
        if(lista != null){
            result = lista.size();
        }

        return result;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        TiendaDto tiendaDto = null;

        lista = new ArrayList<TiendaDto>();

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            tiendaDto = child.getValue(TiendaDto.class);
            lista.add(tiendaDto);
        }

        notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void activateListener(){
        lista = new ArrayList<TiendaDto>();
        //FirebaseDatabase.getInstance().goOnline();
        reference.addValueEventListener(this);
    }
    public void deactivateListener(){
        reference.removeEventListener(this);
        //FirebaseDatabase.getInstance().goOffline();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tituloTienda;
        private TextView idTienda;
        private ImageView iconoDel;
        private ImageView iconoEdit;
        private RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_tienda);
            tituloTienda = (TextView)itemView.findViewById(R.id.txtTienda);
            idTienda = (TextView)itemView.findViewById(R.id.txtIdTienda);
            iconoDel = (ImageView)itemView.findViewById(R.id.imgDeleteTienda);
            iconoEdit = (ImageView)itemView.findViewById(R.id.imgEditTienda);


            iconoDel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DeleteTiendaDialog dialog = new DeleteTiendaDialog();
                    dialog.setDelPosition(getAdapterPosition());
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"Eliminar tienda");
                }
            });

        }

        public TextView getTituloTienda() {
            return tituloTienda;
        }

        public void setTituloTienda(TextView tituloTienda) {
            this.tituloTienda = tituloTienda;
        }

        public TextView getIdTienda() {
            return idTienda;
        }

        public void setIdTienda(TextView idTienda) {
            this.idTienda = idTienda;
        }

        public ImageView getIconoDel() {
            return iconoDel;
        }

        public void setIconoDel(ImageView iconoDel) {
            this.iconoDel = iconoDel;
        }

        public ImageView getIconoEdit() {
            return iconoEdit;
        }

        public void setIconoEdit(ImageView iconoEdit) {
            this.iconoEdit = iconoEdit;
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public void setLayout(RelativeLayout layout) {
            this.layout = layout;
        }
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public List<TiendaDto> getLista() {
        return lista;
    }

    public void setLista(List<TiendaDto> lista) {
        this.lista = lista;
    }
}
