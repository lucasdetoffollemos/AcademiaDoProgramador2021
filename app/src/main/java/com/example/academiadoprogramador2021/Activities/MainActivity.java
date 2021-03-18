package com.example.academiadoprogramador2021.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.academiadoprogramador2021.R;

public class MainActivity extends AppCompatActivity {
    private Button bt_equipamento, bt_manutencao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializa();
        setListeners();
    }

    private void inicializa() {
        bt_equipamento = findViewById(R.id.bt_vaiParaEquipamentos);
        bt_manutencao = findViewById(R.id.bt_vaiParaManutencao);


    }

    private void setListeners() {
        bt_equipamento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                vaiParaEquipamentos();
            }
        });
    }

    private void vaiParaEquipamentos() {
        Intent i = new Intent(MainActivity.this, IndexEquipamento.class);
        startActivity(i);
    }
}