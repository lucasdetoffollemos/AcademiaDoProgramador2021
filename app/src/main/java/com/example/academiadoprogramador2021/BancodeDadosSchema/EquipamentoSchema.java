package com.example.academiadoprogramador2021.BancodeDadosSchema;

public interface EquipamentoSchema {

    String TABELA_EQUIPAMENTO = "equipamentos";
    String COLUNA_ID = "id";
    String COLUNA_NOME = "nome";
    String COLUNA_PRECO = "email";
    String COLUNA_NUMERO_SERIE = "telefone";
    String COLUNA_DATA_FABRICACAO = "perfil";
    String COLUNA_FABRICANTE = "estado";

    String CREATE_TABELA_EQUIPAMENTO = "CREATE TABLE IF NOT EXISTS "
            + TABELA_EQUIPAMENTO + "("
            + COLUNA_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_NOME + " varchar(50), "
            + COLUNA_PRECO + " varchar(50), "
            + COLUNA_NUMERO_SERIE + " varchar(50), "
            + COLUNA_DATA_FABRICACAO + " varchar(50),"
            + COLUNA_FABRICANTE + " varchar(50))";

    String[] EQUIPAMENTO_COLUNAS = new String[] {
            COLUNA_ID,
            COLUNA_NOME,
            COLUNA_PRECO,
            COLUNA_NUMERO_SERIE,
            COLUNA_DATA_FABRICACAO,
            COLUNA_FABRICANTE,
    };


}
