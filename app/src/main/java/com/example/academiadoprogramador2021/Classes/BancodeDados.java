package com.example.academiadoprogramador2021.Classes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.academiadoprogramador2021.BancodeDadosSchema.EquipamentoSchema;
import com.example.academiadoprogramador2021.Dao.EquipamentoDao;


public class BancodeDados {

    private static final String BANCO_DE_DADOS_NOME = "bancoDeDadosAcademiadoProgramador.db";
    private static final int BANCO_DE_DADOS_VERSAO = 1;

    private final Context context;
    private BancoHelper bancoHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static EquipamentoDao equipamentoDao;

    public BancodeDados(Context context) {
        this.context = context;
    }

    /**
     * Método responsável por inicializar as interações com o banco de dados.
     * Cria-se de fato o objeto do banco de dados (SQLiteDatabase) e inicializa-se os DAOs.
     * @throws SQLException representa qualquer exceção que possa ocorrer com o banco de dados.
     */
    public void abreConexao() throws SQLException {
        this.bancoHelper = new BancoHelper(this.context);
        sqLiteDatabase = bancoHelper.getWritableDatabase();

        equipamentoDao = new EquipamentoDao(sqLiteDatabase);
    }

    /**
     * Método responsável por encerrar as interações com o banco de dados.
     */
    @SuppressWarnings("unused")
    public void fechaConexao(){
        sqLiteDatabase.close();
        bancoHelper.close();
    }

    private static class BancoHelper extends SQLiteOpenHelper{

        BancoHelper(Context context){
            super(context, BANCO_DE_DADOS_NOME, null, BANCO_DE_DADOS_VERSAO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(EquipamentoSchema.CREATE_TABELA_EQUIPAMENTO);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }


}

