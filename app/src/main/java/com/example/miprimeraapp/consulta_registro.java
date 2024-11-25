package com.example.miprimeraapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class consulta_registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulta_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("Range")
    public void consultaUsuario (View v) {
        BDSQLite dbGrupo6 = new BDSQLite(this);
        final SQLiteDatabase dbGrupo6Mode = dbGrupo6.getWritableDatabase();

        EditText identificador = findViewById(R.id.txtIdUsuario);
        TextView cedula = findViewById(R.id.lblCedula);
        TextView nombre_apellido = findViewById(R.id.lblNombre);
        TextView rating_usuario = findViewById(R.id.lblRating);
        TextView edad = findViewById(R.id.lblEdad);
        TextView estadocivil = findViewById(R.id.lblEstadoCivil);

        String idUsuario = String.valueOf(identificador.getText());

        Cursor c = dbGrupo6Mode.rawQuery("SELECT id, cedula, nombresapellidos, ratingUser, edad, estadoCivil FROM usuario " +
                "WHERE cedula='" + idUsuario + "'", null);

        if (c != null) {
            if (c.getCount()==0){
                Toast.makeText(this, "No se han encotnrado registros", Toast.LENGTH_LONG).show();
            }
            c.moveToFirst();
            cedula.setText("Cedula: " + c.getString(c.getColumnIndex("cedula")));
            nombre_apellido.setText("Nombre: " + c.getString(c.getColumnIndex("nombresapellidos")));
            edad.setText("Edad: " + c.getInt(c.getColumnIndex("edad")));
            rating_usuario.setText("Rating: " + c.getFloat(c.getColumnIndex("ratingUser")));
            estadocivil.setText("Estado Civil: " + c.getString(c.getColumnIndex("estadoCivil")));

        }
        c.close();
        dbGrupo6Mode.close();
    }
}