package com.marcosledesma.agendacontactosedu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marcosledesma.agendacontactosedu.adapters.ContactosAdapter;
import com.marcosledesma.agendacontactosedu.configuraciones.Configuracion;
import com.marcosledesma.agendacontactosedu.modelos.Contacto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int EDIT_CONTACTO = 12;
    private final int ADD_CONTACTO = 11;
    // Mostrar lista de contactos
    // 1. ListView y Adapter
    private ListView contenedor;
    private ContactosAdapter contactosAdapter;
    // 2. Colección de Objetos
    private ArrayList<Contacto> contactos;
    // 3. Elemento a replicar
    private int resource;

    // Comentamos todo lo relacionado con las sp para utilizar FireBase
    // Shared Preferences (almacenamiento persistente)
    // private SharedPreferences sharedPreferences;
    // private Gson parser;

    // BD y referencia a FireBase
    private FirebaseDatabase database;
    private DatabaseReference refContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // sharedPreferences = getSharedPreferences(Configuracion.SP_LISTA, MODE_PRIVATE);
        // Iniciar database
        database = FirebaseDatabase.getInstance();

        contenedor = findViewById(R.id.contenedorContactosMain);
        resource = R.layout.contacto_fila;



        // String listaCodificada = sharedPreferences.getString(Configuracion.L_contactos, null);
        /*
        if (listaCodificada == null){
            contactos = new ArrayList<>();
        }else{
            parser = new Gson();
            contactos = parser.fromJson(listaCodificada, new TypeToken< ArrayList<Contacto> >(){}.getType());
        }
        */

        // Que no explote al inicar con firebase
        if (contactos == null){
            contactos = new ArrayList<>();
        }

        // LECTURA EN FIREBASE
        // Referencia BD Firebase
        refContactos = database.getReference(Configuracion.REF_CONTACTOS);
        // Cada vez que algo cambien en la BD, se lo trae, y repinta listaContactos
        refContactos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // SI el snapshot existe:
                if (snapshot.exists()){
                    // Genera un tipo genérico para codificar el arraylist de contactos
                    GenericTypeIndicator<ArrayList<Contacto>> gtiContactos = new GenericTypeIndicator<ArrayList<Contacto>>() {};
                    // Limpiamos la lista y le metemos el snapshot
                    contactos.clear();
                    contactos.addAll(snapshot.getValue(gtiContactos));
                    // notificar los cambios al adapter
                    contactosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        contactosAdapter = new ContactosAdapter(this, resource, contactos);
        contenedor.setAdapter(contactosAdapter);

        contenedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Tengo que pasar el item y el objeto
                Bundle bundle = new Bundle();
                bundle.putInt("POS", position);
                bundle.putParcelable("CONTACTO", contactos.get(position));
                Intent intent = new Intent(MainActivity.this, EditContactoActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_CONTACTO);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                startActivityForResult(new Intent(MainActivity.this, AddContactoActivity.class), ADD_CONTACTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_CONTACTO:
                    if (data != null && data.getExtras() != null) {
                        Contacto contacto = data.getExtras().getParcelable("CONTACTO");
                        if (contacto != null) {
                            int posicion = ordenaArray(contacto);
                            contactos.add(posicion, contacto);
                            contactosAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case EDIT_CONTACTO:
                    if (data != null && data.getExtras() != null) {
                        int posicion = data.getExtras().getInt("POS");
                        Contacto contacto = data.getExtras().getParcelable("CONTACTO");
                        if (contacto == null) {
                            contactos.remove(posicion);
                        } else {
                            contactos.set(posicion, contacto);
                        }
                        contactosAdapter.notifyDataSetChanged();
                    }
                    break;
            }
            // Escribimos en las SharedPreferences
            /*
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String listaCodificada = new Gson().toJson(contactos);
            editor.putString(Configuracion.L_contactos, listaCodificada);
            editor.apply();
             */

            // ESCRIBIR EN FIREBASE
            refContactos.setValue(contactos);
        }
    }

    private int ordenaArray(Contacto contacto) {
        // Ordenar alfabéticamente
        int posicion;
        for (posicion = 0; posicion < contactos.size() ; posicion++) {
            if (contactos.get(posicion).getNombre().compareToIgnoreCase(contacto.getNombre()) > 0){
                return posicion;
            }
        }
        return posicion;
    }
}