package com.example.periodicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class NuevoPeriodico extends AppCompatActivity implements View.OnClickListener {

    DBInterface dbInterface;
    public Chip chip_Generalista;
    public Chip chip_Politica;
    public Chip chip_Deportes ;
    public Button botonInsertar;
    public EditText editText;
    private String tematica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_periodico);

        dbInterface = new DBInterface(this);
        dbInterface.abre();

        editText = (EditText) findViewById(R.id.nuevo_nombre);
        chip_Generalista= (Chip) findViewById(R.id.chip_Generalista);
        chip_Politica = (Chip) findViewById(R.id.chip_Politica);
        chip_Deportes = (Chip) findViewById(R.id.chip_Deportes);
        botonInsertar = (Button) findViewById(R.id.boton_inserta);
        tematica = "";
        chip_Politica.setOnClickListener(this);
        chip_Deportes.setOnClickListener(this);
        chip_Generalista.setOnClickListener(this);
        botonInsertar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == chip_Generalista.getId()) {
            if(chip_Generalista.isChecked()) {
                tematica = "Generalista";
            } else  {
                tematica = "";
            }

        }

        if (view.getId() == chip_Deportes.getId()) {
            if(chip_Deportes.isChecked()) {
                tematica = "Deportes";
            } else  {
                tematica = "";

            }

        }

        if (view.getId() == chip_Politica.getId()) {
            if(chip_Politica.isChecked()) {

                tematica = "Politica";
            } else  {
                tematica = "";

            }


        }

        if (view.getId() == botonInsertar.getId()) {
            if(editText.getText().toString().equals("") || tematica.equals("")) {
                Toast.makeText(this, "Rellene los campos para realizar la inserción", Toast.LENGTH_SHORT).show();
            } else {


                dbInterface.insertarPeriodico(editText.getText().toString(), tematica);
                dbInterface.cierra();
                Toast.makeText(this, "Se ha insertado correctamente", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);

            }


        }
    }
}
