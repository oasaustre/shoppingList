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
import com.shop.oasaustre.shoppinglist.activity.CategoriaSaveActivity;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadCategoriesTask extends AsyncTask<Void,Void,List<Categoria>> implements ITask {

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
                CategoriaAdapter adapter = (CategoriaAdapter)((RecyclerView) view.getParent()).getAdapter();
                Categoria categoria = adapter.getLista().get(positionItemSelect);

                editCategoria(categoria);

            }
        });

        RecyclerView rvCategoria = (RecyclerView) activity.findViewById(R.id.rv_categoriaList);
        rvCategoria.addItemDecoration(did);
        rvCategoria.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvCategoria.setLayoutManager(layoutManager);

    }

    private void editCategoria(Categoria categoria){
        Intent intent = null;
        intent = new Intent(activity, CategoriaSaveActivity.class);
        intent.putExtra(AppConstant.ID_INTENT,categoria.getId());
        intent.putExtra(AppConstant.TITLE_INTENT,categoria.getNombre());
        activity.startActivity(intent);

    }

    @Override
    public void run(Object... params) {
        this.execute();
    }
}
