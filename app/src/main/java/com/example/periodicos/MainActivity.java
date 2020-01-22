package com.example.periodicos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdaptadorPeriodico.IOnLongClick{

    ArrayList<Periodico> datos = new ArrayList<>();
    DBInterface dbInterface;
    AdaptadorPeriodico adaptadorPeriodico = null;
    int position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cargarDatos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public void nuevoPeriodico(MenuItem menuItem) {

        Intent i = new Intent(this, NuevoPeriodico.class);
        startActivity(i);
    }

    public void cargarDatos() {

        dbInterface = new DBInterface(this);
        dbInterface.abre();
        Cursor c = dbInterface.obtenerPeriodicos();
        datos.clear();

        if (c.moveToFirst() && c != null) {
            do {
                Periodico periodico = new Periodico(c.getInt(0), c.getString(1), c.getString(2));
                datos.add(periodico);
            } while (c.moveToNext());
        }

        adaptadorPeriodico = new AdaptadorPeriodico(datos);
        adaptadorPeriodico.setiOnLongClick(this);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorPeriodico);

    }

    @Override
    public void showMenu(int position , View view) {
        registerForContextMenu(view);
        this.position = position;
        view.showContextMenu();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        Activity activity= (Activity) v.getContext();
        MenuInflater inflater =activity.getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.mBorrar:
                dbInterface.borrarPeriodico(datos.get(position).getId());//borramos de la base de datos
                Toast.makeText(this, "BORRADO "+datos.get(position).getNombre(),
                        Toast.LENGTH_LONG).show();
                adaptadorPeriodico.notifyDataSetChanged();
                adaptadorPeriodico.remove(position);

                break;


        }
        return super.onContextItemSelected(item);
    }
}
