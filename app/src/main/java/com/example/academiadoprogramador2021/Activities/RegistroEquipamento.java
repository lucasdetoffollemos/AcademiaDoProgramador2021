package com.example.academiadoprogramador2021.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academiadoprogramador2021.Classes.BancodeDados;
import com.example.academiadoprogramador2021.Classes.Equipamento;
import com.example.academiadoprogramador2021.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RegistroEquipamento extends AppCompatActivity {
    private EditText et_nome, et_preco,  et_numeroSerie, et_fabricante;
    private TextView tv_date;
    private String nome, preco, numeroSerie, dataFabricacao, fabricante, dataAtual;
    private Button bt_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipamento);

        inicializa();

        setListeners();
    }

    private void inicializa() {
        tv_date = findViewById(R.id.tv_date);
        bt_registrar = findViewById(R.id.bt_registrar);
        et_nome = findViewById(R.id.et_nomeEquipamento);
        et_preco = findViewById(R.id.et_precoAquisicao);
        et_numeroSerie = findViewById(R.id.et_numeroSerie);
        et_fabricante  = findViewById(R.id.et_fabricante);
    }

    private void setListeners() {
        // perform click event on edit text
        tv_date.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pegandoData();
            }
        });

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDados();
            }
        });


    }

    public void pegandoData(){
        //classe do calendario pega a data atual, mes e ano do calend??rio
        final Calendar calendario = Calendar.getInstance();
        int mYear = calendario.get(Calendar.YEAR); // ano atual

        try {
            calendario.setTime(new SimpleDateFormat("MMM").parse("March"));
            int mMonth = calendario.get(Calendar.MONTH);//mes
            int mDay = calendario.get(Calendar.DAY_OF_MONTH); // dia


            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroEquipamento.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                            dataFabricacao = format.format(calendar.getTime());
                            dataAtual = calendar.getTime().toString();
                           tv_date.setText(dataFabricacao);
                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void registrarDados() {
        nome = et_nome.getText().toString().trim();
        preco = et_preco.getText().toString().trim();
        numeroSerie = et_numeroSerie.getText().toString().trim();
        fabricante = et_fabricante.getText().toString().trim();

        boolean comparaData = comparaDatas(dataFabricacao);


        if ((nome.length() >= 6) && (preco.length() > 0) && (numeroSerie.length() > 0) && (dataFabricacao != null) && (fabricante.length() > 0)) {
            //se a data inserida for maior que atual, nao deixa cadastrar
            if (comparaData == true){

                //se o nome o numero de serie e o fabricante for igual a algum equipamento registrado no banco, nao deixar equipamento se cadastrar.
                if(BancodeDados.equipamentoDao.checkEquipamentoExists(nome, numeroSerie, fabricante)){
                    Toast.makeText(this, "Equipamento j?? existe ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Equipamento e = new Equipamento(nome, preco, numeroSerie, dataFabricacao, fabricante);
                    BancodeDados.equipamentoDao.inserirEquipamento(e);

                    Toast.makeText(this, "Equipamento registrado com sucesso! ", Toast.LENGTH_SHORT).show();

                    //O c??digo abaixo aguarda 2 seg e redireciona o usu??rio para o IndexFragment.
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("nome_equipamento", nome);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }
                    }, 1000);
                }





            }
            else {
                Toast.makeText(this, "Data maior que a atual, por favor insira uma data menor.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Preencha os dados.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean comparaDatas(String dataF)
        {
            boolean checkDate = false;
            try{

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                if(dataF != null){

                    Date date1 = sdf.parse(dataF);

                    Date dataAtual = Calendar.getInstance().getTime();


                    if(date1.before(dataAtual)){
                        checkDate = true;
                    }
                    else {
                        checkDate = false;
                    }

                }

                else {
                    checkDate = false;
                }


            }
            catch(ParseException ex){
                ex.printStackTrace();
            }

            return checkDate;
        }



    public void voltarIndex(View v){
        finish();

    }





}