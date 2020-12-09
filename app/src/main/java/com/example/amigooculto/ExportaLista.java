package com.example.amigooculto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExportaLista extends AppCompatActivity {
    Button btnSortear;
    TextView amigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exporta_lista);

        amigo = findViewById(R.id.tvAmigo);
        Button btnIncluir = findViewById(R.id.btnIncluir);
        btnSortear = findViewById(R.id.btnSortear);
        btnSortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortear(ClienteDAO.getClientes( ExportaLista.this ));


            }
        });


        btnIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExportaLista.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public  void exportCSV(View view){

        //geração do dados dos Clientes para exportacao
        List<Cliente> lista = ClienteDAO.getClientes(this);
        StringBuilder data = new StringBuilder();
       /* data.append("nome"  + ","
                + "cpf" + ","
                + "telefone" + ","
                + "cep" + ","
                + "ativo" + "\n");*/
        for(Cliente obj : lista) {
            data.append(  obj.getDicaPresente() + ","
                    + obj.getNome() + "\n" );
        }
        //saving the file into device
        try{
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //export
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"data.csv");
            Uri path = FileProvider.getUriForFile(context,"com.example.amigooculto.fileprovider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,path);
            startActivity(Intent.createChooser(fileIntent,"Export Data"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sortear(List<Cliente> lista){
         Cliente cliente = new Cliente();
        int tamanhoLista = lista.size();
       // List<Cliente> ListaFInal = new ArrayList<>();
        List<String> listaNomes = new ArrayList<>();

        //separando os nomes dos participantes
        int i = 0;
        for (Cliente obj: lista){
           // String nomeOculto = listaNomes.get(i);
            String nomeOculto = "text";
            obj.setAmigoOculto(nomeOculto);
            ClienteDAO.editar(ExportaLista.this, obj);
           // listaNomes.remove(i);
           // i++;
        }


    }
}