package com.example.academiadoprogramador2021.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academiadoprogramador2021.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        //classe do calendario pega a data atual, mes e ano do calendário
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
            if (comparaData == true){
                //Toast.makeText(this, "Nome do equipamento: " + nome, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Preço de aquisição: " + preco, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Número de série:  " + numeroSerie, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Data de fabricação: " + dataFabricacao, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Fabricante: " + fabricante, Toast.LENGTH_SHORT).show();
                //some logic
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









}