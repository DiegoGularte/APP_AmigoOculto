package com.example.amigooculto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome, etDicaPresente, etEmail;
    private Button btnSalvar;
    private String acao;
    private Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        acao = getIntent().getExtras().getString("acao");


        etNome = findViewById(R.id.etNome);
        etDicaPresente = findViewById(R.id.etDicaPresente);
        etEmail = findViewById(R.id.etEmail);

        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        if( acao.equals("editar")){
            int id = getIntent().getExtras().getInt("idCliente");
            cliente = ClienteDAO.getProdutoById(FormularioActivity.this, id);

            etNome.setText(cliente.getNome());
            etDicaPresente.setText(cliente.getDicaPresente());
            etEmail.setText(cliente.getEmail());

            // setar o valor da tabela no campo sexo

        }

    }

    //Botao salvar um novo Cliente
    private void salvar(){
        if ( cliente == null ){
            cliente = new Cliente();
        }

        String nome = etNome.getText().toString();
        if ( nome.isEmpty() ){
            AlertDialog.Builder alerta = new AlertDialog.Builder( this);
            alerta.setTitle("Atenção!");
            alerta.setMessage("O nome deve ser preenchido!");
            alerta.setIcon( android.R.drawable.ic_dialog_alert );
            alerta.setPositiveButton("OK", null);
            alerta.show();
        }else{

            cliente.setNome( nome );
            cliente.setDicaPresente(etDicaPresente.getText().toString());; // validacao
            cliente.setEmail(etEmail.getText().toString());


            if( acao.equals( "inserir" ) ){
                ClienteDAO.inserir( this , cliente);
                cliente = null;
                etNome.setText("");
                etDicaPresente.setText("");
                etEmail.setText("");
                finish();
            }else{
                ClienteDAO.editar(this, cliente);
                finish();
            }

        }
    }

}