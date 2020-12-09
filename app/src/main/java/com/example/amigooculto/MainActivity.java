package com.example.amigooculto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvClientes = findViewById(R.id.lvProdutos);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , FormularioActivity.class);
                intent.putExtra("acao" , "inserir");
                startActivity( intent );

            }
        });

        carregarClientes();



        lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cliente prodSelecionado = (Cliente) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this , FormularioActivity.class);
                intent.putExtra("acao" , "editar");
                intent.putExtra("idCliente", prodSelecionado.getId() );
                startActivity( intent );
            }

        });

        lvClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cliente clienteSelec = (Cliente) adapterView.getItemAtPosition(i);
                excluir(clienteSelec);
                return true;
            }
        });

    }
    private  void excluir(final Cliente clienteSelec){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluir Cliente");
        alerta.setMessage("Deseja excluir o cliente selecionado: "  );
        alerta.setNeutralButton("cancelar", null);
        alerta.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClienteDAO.excluir(MainActivity.this, clienteSelec.getId());
                carregarClientes();
            }
        });
        alerta.show();
    }


    private void carregarClientes(){
        List<Cliente> lista = ClienteDAO.getClientes( this );
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        lvClientes.setAdapter( adapter );
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        carregarClientes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}