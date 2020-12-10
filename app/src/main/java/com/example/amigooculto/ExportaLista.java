package com.example.amigooculto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExportaLista extends AppCompatActivity {
    Button btnSortear, btnProximoA;
    TextView tvAmigo;
    TextView tvAmigoEnviar;
     int posicaoLista = 0;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exporta_lista);
        btnProximoA = findViewById(R.id.btnProximoA);
        tvAmigo = findViewById(R.id.tvAmigo);
        Button btnIncluir = findViewById(R.id.btnIncluir);
        btnSortear = findViewById(R.id.btnSortear);
        tvAmigoEnviar = findViewById(R.id.tvAmigoEnviar);


        btnSortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortear(ClienteDAO.getClientes( ExportaLista.this ));
                tvAmigo.setText("Sorteio Realizado");
            }
        });

        btnProximoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cliente = proximoAmigo();
               tvAmigoEnviar.setText("Envie o amigo oculto para " + cliente.getNome()  );

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
       // List<Cliente> lista = ClienteDAO.getClientes(this);
        StringBuilder data = new StringBuilder();
        data.append( cliente.getNome()+ " Você pegou :"+ cliente.getAmigoOculto() + "\n" +
        " Sugestão de Presente" + cliente.getDicaPresente());
       /* data.append("nome"  + ","
                + "cpf" + ","
                + "telefone" + ","
                + "cep" + ","
                + "ativo" + "\n");*/
      /*  for(Cliente obj : lista) {
            data.append(  obj.getAmigoOculto() + ","
                    + obj.getNome() + "\n" );
        }*/
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

    public int tamanhoDaLista(){
        List<Cliente> clientes= ClienteDAO.getClientes(this);
       return clientes.size();
    }

    public  Cliente proximoAmigo(){
        posicaoLista++;
        List<Cliente> clientes= ClienteDAO.getClientes(this);
        if (posicaoLista <clientes.size()){
            return clientes.get(posicaoLista);
        }else
            posicaoLista=0;
        return clientes.get(posicaoLista);
    }

    public void sortear(List<Cliente> lista){
         Cliente cliente = new Cliente();
        int tamanhoLista = lista.size();
       // List<Cliente> ListaFInal = new ArrayList<>();
        List<String> listaNomes = new ArrayList<>();

        //separando os nomes dos participantes
        for (Cliente obj: lista){
            listaNomes.add(obj.getNome());
        }


        Random random = new Random();
        int i=0;
        while ( i < lista.size()){
            Cliente obj = lista.get(i);
            int indexRandom = random.nextInt(listaNomes.size());
            String nomeOculto = listaNomes.get(indexRandom);
           if(!nomeOculto.equals(obj.getNome())) {
               obj.setAmigoOculto(nomeOculto);
               ClienteDAO.editar(ExportaLista.this, obj);
               listaNomes.remove(indexRandom);
               i++;
           }else if(i+1 == lista.size()){
               //tratar bug
               i++;
           }
        }

    }
}