package com.example.academiadoprogramador2021.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academiadoprogramador2021.Classes.BancodeDados;
import com.example.academiadoprogramador2021.Classes.Equipamento;
import com.example.academiadoprogramador2021.Classes.ListViewEquipamentoAdapter;
import com.example.academiadoprogramador2021.R;

import java.util.ArrayList;

public class IndexEquipamento extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Button bt_RegistroEquipamentos;
    private ArrayList<Equipamento> listaEquipamento;
    private ListView lv_equipamentos;
    private ListViewEquipamentoAdapter listViewEquipamentoAdapter;
    private int equipamentoId;
    private TextView tv_bemVindo;
    String nomeEquipamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_equipamento);

        inicializa();
        setListeners();
    }

    private void inicializa() {
        bt_RegistroEquipamentos = findViewById(R.id.bt_registroEquipamentos);
        lv_equipamentos = findViewById(R.id.lv_equipamentos);
        tv_bemVindo = findViewById(R.id.tv_bemVindo);

        manipulandoListaEquipamentos();
    }

    private void manipulandoListaEquipamentos() {
        //Nome do equipamento irá vir pelo resultado da activity REgistro equipamento
         equipamentoId = BancodeDados.equipamentoDao.geteEquipamentoId(nomeEquipamento);
        //Recupera as propriedades do usuário baseado no seu ID.
        listaEquipamento = BancodeDados.equipamentoDao.getAllEquipamentos();

        //Se existirem propriedades, esconde o textview "Bem vindo Usuário"
        if(listaEquipamento.size() > 0){
            tv_bemVindo.setVisibility(View.INVISIBLE);
        }


        listViewEquipamentoAdapter = new ListViewEquipamentoAdapter(this, listaEquipamento);
        lv_equipamentos.setAdapter(listViewEquipamentoAdapter);
        registerForContextMenu(lv_equipamentos);
    }

    private void setListeners() {
        bt_RegistroEquipamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaRegistroEquipamentos();
            }
        });
    }

    private void vaiParaRegistroEquipamentos() {
        int REQUEST_CODE = 1;
        Intent i = new Intent(this, RegistroEquipamento.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void atualizaListView(){
        //Consulta o banco e atualiza o listview.
        listaEquipamento = BancodeDados.equipamentoDao.getAllEquipamentos();
        listViewEquipamentoAdapter.getData().clear();
        listViewEquipamentoAdapter.getData().addAll(listaEquipamento);
        listViewEquipamentoAdapter.notifyDataSetChanged();

        if(listaEquipamento.size() > 0){
            tv_bemVindo.setVisibility(View.INVISIBLE);
        }
        else{
            tv_bemVindo.setVisibility(View.VISIBLE);
        }
    }

//    public void atualizaEqupamento(){
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Quando os dados vem do registrar dados
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            nomeEquipamento = data.getStringExtra("nome_equipamento");
            atualizaListView();
        }

        //quando os dados vem do editar dados
        if (resultCode == RESULT_OK && requestCode == 2) {
            atualizaListView();

        }
    }

    public void voltarMain(View v){
        finish();
    }




}