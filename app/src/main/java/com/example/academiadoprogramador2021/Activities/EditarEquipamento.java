package com.example.academiadoprogramador2021.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class EditarEquipamento extends AppCompatActivity {

    private EditText et_nome, et_preco,  et_numeroSerie, et_fabricante;
    private TextView tv_date;
    private String nome, preco, numeroSerie, dataFabricacao, fabricante, dataAtual;
    private Button bt_editar;
    private int idEquipamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipamento);

        inicializa();
        setListeners();
    }

    private void inicializa() {
        Intent intent = getIntent();
        String nomeEquipamento = intent.getStringExtra("nomeEquipamento");

        idEquipamento = BancodeDados.equipamentoDao.geteEquipamentoId(nomeEquipamento);
        Equipamento e = BancodeDados.equipamentoDao.getEquipamento(idEquipamento);

        et_nome = findViewById(R.id.et_nomeEquipamento);
        et_preco = findViewById(R.id.et_precoAquisicao);
        et_numeroSerie = findViewById(R.id.et_numeroSerie);
        tv_date = findViewById(R.id.tv_date);
        et_fabricante = findViewById(R.id.et_fabricante);
        bt_editar = findViewById(R.id.bt_editar);

        et_nome.setText(e.getNome());
        et_preco.setText(e.getPreco());
        et_numeroSerie.setText(e.getNumeroSerie());
        dataFabricacao = e.getData_fabricacao();
        tv_date.setText(dataFabricacao);
        et_fabricante.setText(e.getFabricante());


    }

    private void setListeners() {

            // perform click event on edit text
            tv_date.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    pegandoData();
                }
            });

        bt_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarDados();
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(EditarEquipamento.this,
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

    private void atualizarDados() {


        nome = et_nome.getText().toString().trim();
        preco = et_preco.getText().toString().trim();
        numeroSerie = et_numeroSerie.getText().toString().trim();
        fabricante = et_fabricante.getText().toString().trim();

        Equipamento e = BancodeDados.equipamentoDao.getEquipamento(idEquipamento);
        String nomeBanco = e.getNome();
        String precoBanco = e.getPreco();
        String numeroSerieBanco = e.getNumeroSerie();
        String dataBanco = e.getData_fabricacao();
        String fabricanteBanco = e.getFabricante();

        boolean comparaData = comparaDatas(dataFabricacao);

        if(nomeBanco.equals(nome) && precoBanco.equals(preco) && numeroSerieBanco.equals(numeroSerie) && dataBanco.equals(dataFabricacao) && fabricanteBanco.equals(fabricante)){
            Toast.makeText(EditarEquipamento.this, "Dados iguais aos cadastrados!", Toast.LENGTH_SHORT).show();
        }
        else {
            if ((nome.length() >= 6) && (preco.length() > 0) && (numeroSerie.length() > 0)  && (fabricante.length() > 0)) {
                //se a data inserida for maior que atual, nao deixa cadastrar
                if (comparaData == true){
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditarEquipamento.this);
                        builder.setTitle("ATENÇÃO");
                        builder.setMessage( "Tem certeza que deseja continuar?" );
                        builder.setPositiveButton(" SIM ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Toast.makeText(EditarEquipamento.this, "Nome " + nomeBanco, Toast.LENGTH_SHORT).show();
                                BancodeDados.equipamentoDao.updateEquipamento(idEquipamento, nome, preco, numeroSerie, dataFabricacao, fabricante);

                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent returnIntent = new Intent();
                                        setResult(RESULT_OK, returnIntent);
                                        finish();
                                    }
                                }, 1000);
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
                else {
                    Toast.makeText(this, "Data maior que a atual, por favor insira uma data menor.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Preencha os dados.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public boolean comparaDatas(String dataF)
    {
        boolean checkDate = false;
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");


                Date date1 = sdf.parse(dataF);

                Date dataAtual = Calendar.getInstance().getTime();


                if(date1.before(dataAtual)){
                    checkDate = true;
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