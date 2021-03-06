package com.shop.oasaustre.shoppinglist.activity.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.NewListTask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.app.App;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ListDialog extends DialogFragment {


    private boolean activo;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createListDialog();
    }

    private AlertDialog createListDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.new_list_form, null);

        builder.setView(v);



        TextView btnNewSave = (TextView) v.findViewById(R.id.btnNewSave);
        TextView btnNewCancel = (TextView) v.findViewById(R.id.btnNewCancel);
        final EditText fieldNewList = (EditText) v.findViewById(R.id.fieldNewList);

        btnNewSave.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                            createNewList(fieldNewList);
                    }
                });

        btnNewCancel.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });


        return builder.create();
    }


    private void createNewList(EditText fieldNewList){
        if(fieldNewList.getText().length() > 0){
            ITask task = TaskFactory.getInstance().createNewListTask(getActivity(),
                    (App) getActivity().getApplication(),
                    this,
                    activo);
            task.run(fieldNewList.getText().toString());
        }else{
            Toast.makeText(getActivity(),"El campo de texto está vacío",Toast.LENGTH_LONG);
        }
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
