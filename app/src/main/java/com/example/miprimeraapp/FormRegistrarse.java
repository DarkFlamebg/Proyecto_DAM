package com.example.miprimeraapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class FormRegistrarse extends AppCompatActivity {
    private String fechaNacimientoSeleccionada;
    private String nacionalidadSeleccionada;
    private String generoSeleccionado;
    private String estadoCivilSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_registrarse);

        // Configurar Spinner Nacionalidad
        Spinner spnNacionalidad = findViewById(R.id.spnNacionalidad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nacionalidad_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNacionalidad.setAdapter(adapter);

        spnNacionalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                nacionalidadSeleccionada = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Configurar Spinner Género
        Spinner spnGenero = findViewById(R.id.spnGenero);
        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this,
                R.array.genero_array, android.R.layout.simple_spinner_item);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenero.setAdapter(adapterGenero);

        spnGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generoSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Configurar RadioGroup Estado Civil
        RadioGroup radioGroupEstadoCivil = findViewById(R.id.radioGroupEstadoCivil);
        radioGroupEstadoCivil.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                RadioButton radioButtonSeleccionado = findViewById(checkedId);
                estadoCivilSeleccionado = radioButtonSeleccionado.getText().toString();
            }
        });

        // Configurar botón para elegir fecha de nacimiento
        Button btnFechaNacimiento = findViewById(R.id.btnFechaNacimiento);
        btnFechaNacimiento.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            fechaNacimientoSeleccionada = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            Toast.makeText(this, "Fecha seleccionada: " + fechaNacimientoSeleccionada, Toast.LENGTH_SHORT).show();
        }, year, month, day);

        datePickerDialog.show();
    }

    public void cancelar(View v) {
        Intent intentMain = new Intent(v.getContext(), LoginActivity.class);
        Toast.makeText(v.getContext(), "Regresando", Toast.LENGTH_SHORT).show();
        startActivity(intentMain);
    }

    public void borrar(View v) {
        // Limpiar campos de texto
        EditText cedula = findViewById(R.id.lblCedula);
        EditText nombres = findViewById(R.id.txtNombres);
        EditText apellidos = findViewById(R.id.txtApellidos);
        EditText edad = findViewById(R.id.txtEdad);
        cedula.setText("");
        nombres.setText("");
        apellidos.setText("");
        edad.setText("");

        // Restablecer estado de CheckBox
        CheckBox acuerdo = findViewById(R.id.chkaceptaracuerdo);
        acuerdo.setChecked(false);

        // Restablecer selección de Spinners
        Spinner spnNacionalidad = findViewById(R.id.spnNacionalidad);
        spnNacionalidad.setSelection(0);

        Spinner spnGenero = findViewById(R.id.spnGenero);
        spnGenero.setSelection(0);

        // Limpiar RadioGroup
        RadioGroup radioGroupEstadoCivil = findViewById(R.id.radioGroupEstadoCivil);
        radioGroupEstadoCivil.clearCheck();

        // Restablecer RatingBar
        RatingBar valorUsuario = findViewById(R.id.ratingUser);
        valorUsuario.setRating(0);

        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show();
    }

    private void guardarenSD(String datos){
        try {
            File f = new File(getExternalFilesDir(null), "RegistroUsuario");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f, true));
            out.write(datos);
            out.close();
            Toast.makeText(this, "Todos los datos guardados correctamente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e("SD", "Error al guardar los datos");
            Toast.makeText(this, "Todos los datos no se guardaron", Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarRegistro(View v){
        String datos;
        CheckBox acuerdo = findViewById(R.id.chkaceptaracuerdo);
        EditText cedula = findViewById(R.id.lblCedula);
        EditText nombres = findViewById(R.id.txtNombres);
        EditText apellidos = findViewById(R.id.txtApellidos);
        EditText edad = findViewById(R.id.txtEdad);
        //RadioButton masculino = findViewById(R.id.rdnMasculino);
        //RadioButton femenino = findViewById(R.id.rdrFemenino);
        RatingBar valor_usuario = findViewById(R.id.ratingUser);
        if (acuerdo.isChecked())
        {
            datos = "Cédula: " + cedula.getText() + "\n";
            datos += "Nombres: " + nombres.getText() + "\n";
            datos += "Apellidos: " + apellidos.getText() + "\n";
            datos += "Edad: " + edad.getText() + "\n";
            datos += "Nacionalidad: " + nacionalidadSeleccionada + "\n";
            datos += "Género: " + generoSeleccionado + "\n";
            datos += "Estado Civil: " + (estadoCivilSeleccionado != null ? estadoCivilSeleccionado : "No seleccionado") + "\n";
            datos += "Fecha de Nacimiento: " + fechaNacimientoSeleccionada + "\n";
            datos += "Nivel de Ingles: " + valor_usuario.getRating() + "\n";

            Log.i( "Datos personales", datos);
            Toast.makeText(v.getContext(), "Todos los datos han sido cargados correctamente", Toast.LENGTH_SHORT).show();
            // Guardar en memoria externa
            guardarenSD(datos);
            Toast.makeText(v.getContext(), "Datos Guardados", Toast.LENGTH_SHORT).show();

            //Guardar DataBase
            guardarBD(
                    cedula.getText().toString(),
                    nombres.getText().toString() + " " + apellidos.getText().toString(),
                    valor_usuario.getRating(),
                    Integer.parseInt(edad.getText().toString()),
                    nacionalidadSeleccionada,
                    generoSeleccionado,
                    estadoCivilSeleccionado != null ? estadoCivilSeleccionado : "No especificado",
                    fechaNacimientoSeleccionada != null ? fechaNacimientoSeleccionada : "No especificada"
            );
        }
        else {
            Toast.makeText(v.getContext(), "Se requeire aceptar los acuerdos de privacidad", Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarBD(String cedula, String apellidos_nombres, float ratingUser, int edad, String nacionalidad,String genero, String estadoCivil, String fechaNacimiento){

        BDSQLite dbGrupo6 = new BDSQLite(this);
        final SQLiteDatabase dbGrupo6Mode = dbGrupo6.getWritableDatabase();

        if(dbGrupo6Mode != null){

            ContentValues cv = new ContentValues();
            cv.put("cedula", cedula);
            cv.put("nombresapellidos", apellidos_nombres);
            cv.put("edad", edad);
            cv.put("nacionalidad", nacionalidad);
            cv.put("genero", genero);
            cv.put("estadoCivil", estadoCivil);
            cv.put("fechaNacimiento", fechaNacimiento);
            cv.put("ratingUser", ratingUser);

            dbGrupo6Mode.insert("usuario", null, cv);
            Toast.makeText(this, "Se ha guardado satisfactoriamente en la Base de " +
                    "datos", Toast.LENGTH_SHORT).show();
        }

        //dbGrupo6Mode.close();
    }

    public void actualizarRegistro(View v) {
        EditText cedula = findViewById(R.id.lblCedula);
        EditText nombres = findViewById(R.id.txtNombres);
        EditText apellidos = findViewById(R.id.txtApellidos);
        RatingBar valor_usuario = findViewById(R.id.ratingUser);

        String cedulaTexto = cedula.getText().toString();
        String nombresApellidos = nombres.getText().toString() + " " + apellidos.getText().toString();
        float rating = valor_usuario.getRating();

        if (!cedulaTexto.isEmpty()) {
            BDSQLite dbGrupo6 = new BDSQLite(this);
            SQLiteDatabase dbGrupo6Mode = dbGrupo6.getWritableDatabase();

            if (dbGrupo6Mode != null) {
                ContentValues cv = new ContentValues();
                cv.put("nombresapellidos", nombresApellidos);
                cv.put("ratingUser", rating);

                int rowsAffected = dbGrupo6Mode.update("usuario", cv, "cedula=?", new String[]{cedulaTexto});
                if (rowsAffected > 0) {
                    Toast.makeText(this, "Registro actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No se encontró un registro con la cédula proporcionada", Toast.LENGTH_SHORT).show();
                }
            }

            // dbGrupo6Mode.close();
        } else {
            Toast.makeText(this, "Por favor, ingrese una cédula válida para actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarRegistro(View v) {
        EditText cedula = findViewById(R.id.lblCedula);

        String cedulaTexto = cedula.getText().toString();

        if (!cedulaTexto.isEmpty()) {
            BDSQLite dbGrupo6 = new BDSQLite(this);
            SQLiteDatabase dbGrupo6Mode = dbGrupo6.getWritableDatabase();

            if (dbGrupo6Mode != null) {
                int rowsDeleted = dbGrupo6Mode.delete("usuario", "cedula=?", new String[]{cedulaTexto});
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No se encontró un registro con la cédula proporcionada", Toast.LENGTH_SHORT).show();
                }
            }

            // dbGrupo6Mode.close();
        } else {
            Toast.makeText(this, "Por favor, ingrese una cédula válida para eliminar", Toast.LENGTH_SHORT).show();
        }
    }

}