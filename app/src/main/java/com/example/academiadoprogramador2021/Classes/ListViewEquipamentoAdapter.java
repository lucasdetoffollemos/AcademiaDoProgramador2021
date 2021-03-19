package com.example.academiadoprogramador2021.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academiadoprogramador2021.Activities.EditarEquipamento;
import com.example.academiadoprogramador2021.Activities.IndexEquipamento;
import com.example.academiadoprogramador2021.R;

import java.util.ArrayList;

public class ListViewEquipamentoAdapter extends BaseAdapter {

    private static final int REQUEST_CODE = 2;
    private final Context context;
    public ArrayList<Equipamento> listaEquipamentos;
    private int idEquipamento;
    private static LayoutInflater inflater=null;

        //Atributo para animação no botão

        public ListViewEquipamentoAdapter(Context context, ArrayList<Equipamento> lista){
            this.context = context;
            this.listaEquipamentos = lista;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public ArrayList<Equipamento> getData() {
            return this.listaEquipamentos;
        }


        public int getCount() {
            return this.listaEquipamentos.size();
        }

        @Override
        public Object getItem(int position) {
            return this.listaEquipamentos.get(position);
        }

        //Implementar para pegar o id da propriedade caso necessário.
        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder{
            TextView tv_nome, tv_numeroSerie, tv_fabricante;
            Button bt_excluir, bt_editar;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            //Infla o layout definido em linha_listview_propriedade.xml.
            View row = inflater.inflate(R.layout.linha_listview_equipamento,null);
            row.setTag(this.getCount());

            //Identifica os elementos da linha.
            holder.tv_nome = row.findViewById(R.id.lv_tv_nome_equipamento);
            holder.tv_numeroSerie = row.findViewById(R.id.lv_tv_numeroSerieDinamico);
            holder.tv_fabricante = row.findViewById(R.id.lv_tv_FabricanteDinamico);
            holder.bt_excluir = row.findViewById(R.id.lv_bt_excluir);
            holder.bt_editar = row.findViewById(R.id.lv_bt_editar);

            //Cria um objeto para cada item da lista.
            final Equipamento equipamento = (Equipamento) this.getItem(position);

            //Define os valores dos textviews.
            holder.tv_nome.setText(equipamento.getNome());
            holder.tv_numeroSerie.setText(String.valueOf(equipamento.getNumeroSerie()));
            holder.tv_fabricante.setText(String.valueOf(equipamento.getFabricante()));

            idEquipamento = BancodeDados.equipamentoDao.geteEquipamentoId(equipamento.getNome());

            //Listener do botão "Editar dados"
            holder.bt_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, EditarEquipamento.class);
                    i.putExtra("nomeEquipamento", equipamento.getNome());
                    ((Activity) context).startActivityForResult(i, REQUEST_CODE);
                }
            });





            //Listener do botão "excluir"
            holder.bt_excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("ATENÇÃO");
                    builder.setMessage( "Tem certeza que deseja remover este equipamento?" );
                    builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          //some logic
                            BancodeDados.equipamentoDao.deleteEquipamentoById(idEquipamento);
                            //ATUALIZA LISTA
                            listaEquipamentos.remove(position);
                            notifyDataSetChanged();
                            notifyDataSetInvalidated();
                        }
                    });

                    builder.setNegativeButton(" NÃO ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
            });

            return row;
        }




}
