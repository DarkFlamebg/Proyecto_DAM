package com.example.miprimeraapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    CheckBox recordarPass;
    private final String authUser = "Stevenbg";
    private final String authPass = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recordarPass = findViewById(R.id.chkaceptaracuerdo);

        // Verifica si hay credenciales almacenadas en las SharedPreferences
        SharedPreferences shplogin = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String storedUsername = shplogin.getString("usuarioSHP", "");
        String storedPassword = shplogin.getString("claveSHP", "");

        if (!storedUsername.isEmpty() && !storedPassword.isEmpty()) {
            // Si hay credenciales almacenadas, inicia sesión automáticamente
            Intent intentVentana = new Intent(this, MainActivity.class);
            startActivity(intentVentana);
            finish(); // Finaliza esta actividad para que el usuario no pueda regresar a ella
        }
    }

    /*public void cancelar(View v){
        Toast.makeText(v.getContext(), "Regresando", Toast.LENGTH_SHORT).show();
    }*/
    public void registarse(View v){
        Intent intentRegistrarse = new Intent(v.getContext(), FormRegistrarse.class);
        startActivity(intentRegistrarse);
    }

    private void guardarSharedPreference(String username, String password){
        SharedPreferences shplogin = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor shpLoginEdit = shplogin.edit();
        shpLoginEdit.putString("usuarioSHP", username);
        shpLoginEdit.putString("claveSHP", password);
        shpLoginEdit.commit();
    }

    public void login(View v){
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        recordarPass = findViewById(R.id.chkrecordarPass);

        String enteredUsername = username.getText().toString();
        String enteredPassword = password.getText().toString();

        if (recordarPass.isChecked())
        {
            //guardarSharedPreference(String.valueOf(username.getText()), String.valueOf(password.getText()));
            guardarSharedPreference(enteredUsername, enteredPassword);

        }
        if (enteredUsername.equals(authUser) && enteredPassword.equals(authPass)) {
            // Si la validación es correcta, muestra un mensaje de bienvenida y abre la nueva actividad
            //Toast.makeText(v.getContext(), "Accediendo... \nBienvenido de nuevo: " + enteredUsername, Toast.LENGTH_SHORT).show();
            Toast.makeText(v.getContext(), "Accediendo...", Toast.LENGTH_SHORT).show();
            guardarSharedPreference(enteredUsername, enteredPassword);
            Intent intentVentana = new Intent(v.getContext(), MainActivity.class);
            startActivity(intentVentana);
            finish();
        } else {
            // Si la validación falla, muestra un mensaje de error
            Toast.makeText(v.getContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }

    }
}