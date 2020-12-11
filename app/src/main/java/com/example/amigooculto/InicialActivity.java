package com.example.amigooculto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InicialActivity extends AppCompatActivity {
    TextView tvIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        tvIniciar = findViewById(R.id.tvIniciar);
        tvIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this,ExportaLista.class);
                startActivity(intent);
            }
        });
    }
}