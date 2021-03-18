package com.example.academiadoprogramador2021.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.academiadoprogramador2021.R;

public class IndexEquipamento extends AppCompatActivity {

    private Button bt_RegistroEquipamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_equipamento);

        inicializa();
        setListeners();

    }

    private void inicializa() {
        bt_RegistroEquipamentos = findViewById(R.id.bt_registroEquipamentos);
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
        Intent i = new Intent(IndexEquipamento.this, RegistroEquipamento.class);
        startActivity(i);
    }
}