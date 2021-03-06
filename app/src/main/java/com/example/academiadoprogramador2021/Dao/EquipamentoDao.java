package com.example.academiadoprogramador2021.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.academiadoprogramador2021.BancodeDadosSchema.EquipamentoSchema;
import com.example.academiadoprogramador2021.Classes.Equipamento;

import java.util.ArrayList;

public class EquipamentoDao implements EquipamentoSchema {
    SQLiteDatabase bancoDeDados;

    public EquipamentoDao(SQLiteDatabase bancoDeDados){
        this.bancoDeDados = bancoDeDados;
    }


    public void inserirEquipamento(Equipamento e){
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, e.getNome());
        values.put(COLUNA_PRECO, e.getPreco());
        values.put(COLUNA_NUMERO_SERIE, e.getNumeroSerie());
        values.put(COLUNA_DATA_FABRICACAO, e.getData_fabricacao());
        values.put(COLUNA_FABRICANTE, e.getFabricante());

        this.bancoDeDados.insert(TABELA_EQUIPAMENTO, null, values);
    }

    public void deleteAllEquipamentos(){
            this.bancoDeDados.execSQL("DELETE from " + TABELA_EQUIPAMENTO);
    }

//    public Equipamento getPropriedadeId(String nome){
//
//        String sql_query = "SELECT * FROM " + TABELA_EQUIPAMENTO + " WHERE " + COLUNA_NOME + "=" + "\"" + nome + "\"";
//        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);
//
//        Equipamento e = new Equipamento();
//        if (cursor != null && cursor.moveToFirst()) {
//            e.setNome(cursor.getString(1));
//            e.setPreco(cursor.getString(2));
//            e.setNumeroSerie(cursor.getString(3));
//            e.setData_fabricacao(cursor.getString(4));
//            e.setFabricante(cursor.getString(5));
//            cursor.close();
//        }
//        return e;
//
//    }

    public int geteEquipamentoId(String nome){
        String sql_query = "SELECT * FROM " + TABELA_EQUIPAMENTO + " WHERE " + COLUNA_NOME + "=" + "\"" + nome + "\"";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        int id = -1;
        if (cursor.moveToLast()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        return id;
    }


    public ArrayList<Equipamento> getAllEquipamentos() {

            ArrayList<Equipamento> listaEquipamentos = new ArrayList<>();

            Cursor cursor = this.bancoDeDados.query(TABELA_EQUIPAMENTO, EQUIPAMENTO_COLUNAS,
                    null, null, null, null, null);

            while(cursor.moveToNext()){
                Equipamento e = new Equipamento();
                e.setNome(cursor.getString(1));
                e.setPreco(cursor.getString(2));
                e.setNumeroSerie(cursor.getString(3));
                e.setData_fabricacao(cursor.getString(4));
                e.setFabricante(cursor.getString(5));
                listaEquipamentos.add(e);
            }
            cursor.close();

            return listaEquipamentos;

    }

    public void updateEquipamento(int id, String nome, String preco, String numeroSerie, String data, String fabricante){
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME,nome); //These Fields should be your String values of actual column names
        values.put(COLUNA_PRECO, preco);
        values.put(COLUNA_NUMERO_SERIE, numeroSerie);
        values.put(COLUNA_DATA_FABRICACAO, data);
        values.put(COLUNA_FABRICANTE, fabricante);


        this.bancoDeDados.update(TABELA_EQUIPAMENTO, values, COLUNA_ID + " = " + id, null);
    }

    public Equipamento getEquipamento(int id) {

        String sql_query = "SELECT * FROM " + TABELA_EQUIPAMENTO + " WHERE " + COLUNA_ID + "=" + id;
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, null);

        Equipamento e = new Equipamento();
        if (cursor != null && cursor.moveToFirst()) {
            e.setId(cursor.getInt(0));
            e.setNome(cursor.getString(1));
            e.setPreco(cursor.getString(2));
            e.setNumeroSerie(cursor.getString(3));
            e.setData_fabricacao(cursor.getString(4));
            e.setFabricante(cursor.getString(5));
            cursor.close();
        }

        return e;

    }

    public void deleteEquipamentoById(int id){
        this.bancoDeDados.delete(TABELA_EQUIPAMENTO, COLUNA_ID + "=?", new String[]{Integer.toString(id)});
    }

    public boolean checkEquipamentoExists(String nome, String numeroSerie, String fabricante){
        boolean check = false;

        String sql_query = "SELECT * FROM " + TABELA_EQUIPAMENTO+ " WHERE " + COLUNA_NOME + "=? and "+ COLUNA_NUMERO_SERIE + "=? and " + COLUNA_FABRICANTE + "=?";
        Cursor cursor = this.bancoDeDados.rawQuery(sql_query, new String[]{nome,numeroSerie, fabricante});

        if(cursor.getCount()>0){
            cursor.close();
            check = true;
        }
        else {
            check = false;
        }

        return check;

    }
}

