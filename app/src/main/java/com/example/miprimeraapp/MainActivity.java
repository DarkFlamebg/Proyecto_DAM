package com.example.miprimeraapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView txtNombreUsuario = findViewById(R.id.txtUserLog);
        Button btnBorrarSHP = findViewById(R.id.btnBorrarshp);

        SharedPreferences shplogin = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String nombreUsuario = shplogin.getString("usuarioSHP", "Usuario desconocido");
        txtNombreUsuario.setText("Bienvenido de nuevo, "+ nombreUsuario);

        btnBorrarSHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarSharedPreference();
                Toast.makeText(MainActivity.this, "Credenciales borradas", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void Regresar(View v){
        SharedPreferences shplogin = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String usuario = shplogin.getString("usuarioSHP", null);
        String clave = shplogin.getString("claveSHP", null);
        if (usuario != null || clave != null) {
            Toast.makeText(this, "Primero borra tus credenciales", Toast.LENGTH_LONG).show();
        } else {
            Intent intentMain = new Intent(v.getContext(), LoginActivity.class);
            Toast.makeText(v.getContext(), "Regresando", Toast.LENGTH_SHORT).show();
            startActivity(intentMain);
        }
    }

    private void borrarSharedPreference() {
        SharedPreferences shplogin = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor shpLoginEdit = shplogin.edit();
        shpLoginEdit.remove("usuarioSHP");
        shpLoginEdit.remove("claveSHP");
        shpLoginEdit.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuPrincipal = getMenuInflater();
        menuPrincipal.inflate(R.menu.menuprincipal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemConfiguracion){
            Toast.makeText(this, "Has presionado sobre configuraciones", Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == R.id.itemConsulta){
            Intent intentConsulta = new Intent(this, consulta_registro.class);
            Toast.makeText(this, "Has presionado sobre consulta de registros", Toast.LENGTH_LONG).show();
            startActivity(intentConsulta);
        }
        else if (item.getItemId() == R.id.itemacercaDe){
            Intent intentAcercaDe = new Intent(this, acerca_de.class);
            Toast.makeText(this, "Redirigiendo...", Toast.LENGTH_LONG).show();
            startActivity(intentAcercaDe);
        }
        return true;
    }

}
