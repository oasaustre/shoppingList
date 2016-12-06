package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.ArticleSaveActivity;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.adapter.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;
import com.shop.oasaustre.shoppinglist.dto.ListaCompraDto;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadCategoriesTask extends AsyncTask<Void,Void,List<Categoria>> {

    private Activity activity = null;
    private CategoriaAdapter adapter = null;

    public LoadCategoriesTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected List<Categoria> doInBackground(Void... voids) {
        CategoriaService categoriaService = null;
        List<Categoria> lstCategoria = null;


        try {
            categoriaService = new CategoriaService((App) activity.getApplication());

            lstCategoria = categoriaService.loadAll();

        }catch(Exception ex){
                Log.e(this.getClass().getName(),"Error al obtener la lista de categorías");
            }

        return lstCategoria;
    }

    @Override
    protected void onPostExecute(List<Categoria> lstCategoria) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new CategoriaAdapter(activity, lstCategoria);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                ListaCompra listaCompra = ((ListaCompraAdapter)((RecyclerView) view.getParent()).
                        getAdapter()).getLista().get(positionItemSelect);

                Intent i = new Intent(activity,ArticleSaveActivity.class);
                i.putExtra("idListaCompra",listaCompra.getId());
                activity.startActivityForResult(i,AppConstant.RES_UPDATE_ARTICLE);
            }
        });

        RecyclerView rvCategoria = (RecyclerView) activity.findViewById(R.id.rv_categoriaList);
        rvCategoria.addItemDecoration(did);
        rvCategoria.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvCategoria.setLayoutManager(layoutManager);

    }
}