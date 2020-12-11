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
    String dicaPresente ;
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
                tvAmigoEnviar.setText("Pressione o botão próximo para começar a enviar");
            }
        });

        btnProximoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               cliente = proximoAmigo();
               tvAmigoEnviar.setText("Envie o amigo oculto para " + cliente.getNome()  );

                /*dicaPresente = presenteAmigo();

                tvAmigoEnviar.setText( cliente.getNome()+ " Você pegou :"+ cliente.getAmigoOculto() +
                        " Sugestão de Presente: " + dicaPresente);*/
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


        dicaPresente = presenteAmigo();
        StringBuilder data = new StringBuilder();
        data.append( cliente.getNome() + " Voce pegou: "+  cliente.getAmigoOculto()  + "\n" +
        " Sugestao de Presente: " + dicaPresente + "\n");

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

    public String presenteAmigo(){
        List<Cliente> clientes= ClienteDAO.getClientes(this);
        Cliente cliente = clientes.get(posicaoLista);
        String amigoOculto = cliente.getAmigoOculto();
        for(Cliente obj: clientes) {
            if (obj.getNome().equals(amigoOculto)) {
                return obj.getDicaPresente();
            }

        }
        return "erro";
    }


    public void sortear(List<Cliente> lista){

         Cliente cliente = new Cliente();
        int tamanhoLista = lista.size();
        if(lista.size() > 2) {
       // List<Cliente> ListaFInal = new ArrayList<>();
        List<String> listaNomes = new ArrayList<>();

        //separando os nomes dos participantes
        for (Cliente obj: lista){
            listaNomes.add(obj.getNome());
        }

        Random random = new Random();
        int i=0;

        //percorre a lista para adicionar o amigo oculto


            while (i < lista.size()) {//9 10
                Cliente obj = lista.get(i);
                int indexRandom = random.nextInt(listaNomes.size());
                String nomeOculto = listaNomes.get(indexRandom);
                //caso o participante receba seu proprio nome não recebe o amigo oculto e inicia outra tentativa
                if (!nomeOculto.equals(obj.getNome())) {
                    obj.setAmigoOculto(nomeOculto);
                    ClienteDAO.editar(ExportaLista.this, obj);
                    listaNomes.remove(indexRandom);
                    i++;
                    //caso o ultimo amigo oculto seja o proprio participante, trocasse o amigo oculto com o anterior
                } else if (i + 1 == lista.size()) { // testa se é o ultimo

                        String troca = obj.getAmigoOculto();
                        Cliente cliente1 = lista.get(i-1);
                        obj.setAmigoOculto(cliente1.getAmigoOculto());
                    ClienteDAO.editar(ExportaLista.this, cliente1);
                        obj.setAmigoOculto(troca);
                    ClienteDAO.editar(ExportaLista.this, obj);

                        i++;


                }
            }

        }
    }
}