package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.TiendaSaveActivity;
import com.shop.oasaustre.shoppinglist.adapter.TiendaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.TiendaService;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadTiendasTask extends AsyncTask<Void,Void,List<Tienda>> implements ITask{

    private Activity activity = null;
    private TiendaAdapter adapter = null;

    public LoadTiendasTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected List<Tienda> doInBackground(Void... voids) {
        TiendaService tiendaService = null;
        List<Tienda> lstTienda= null;


        try {
            tiendaService = new TiendaService((App) activity.getApplication());

            lstTienda = tiendaService.loadAll();

        }catch(Exception ex){
                Log.e(this.getClass().getName(),"Error al obtener la lista de tiendas:"+ex);
            }

        return lstTienda;
    }

    @Override
    protected void onPostExecute(List<Tienda> lstTienda) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new TiendaAdapter(activity, lstTienda);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                TiendaAdapter adapter = (TiendaAdapter) ((RecyclerView) view.getParent()).getAdapter();
                Tienda tienda = adapter.getLista().get(positionItemSelect);

                editTienda(tienda);

            }
        });

        RecyclerView rvTienda = (RecyclerView) activity.findViewById(R.id.rv_tiendaList);
        rvTienda.addItemDecoration(did);
        rvTienda.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvTienda.setLayoutManager(layoutManager);

    }

    private void editTienda(Tienda tienda){
        Intent intent = null;
        intent = new Intent(activity, TiendaSaveActivity.class);
        intent.putExtra(AppConstant.ID_INTENT,tienda.getId());
        intent.putExtra(AppConstant.TITLE_INTENT,tienda.getNombre());
        activity.startActivity(intent);

    }

    @Override
    public void run(Object... params) {
        this.execute();
    }
}
