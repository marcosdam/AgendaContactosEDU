package com.marcosledesma.agendacontactosedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.marcosledesma.agendacontactosedu.modelos.Contacto;
import com.marcosledesma.agendacontactosedu.modelos.Direccion;
import com.marcosledesma.agendacontactosedu.modelos.Telefono;

import java.util.ArrayList;
import java.util.Arrays;

public class AddContactoActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtEmpresa;
    private EditText txtCalle, txtNumero, txtCiudad, txtNombreDir;
    private EditText txtNumTelefono, txtNombreTel;
    private Spinner spTipoDireccion, spTipoTelefono;
    private Button btnAgregar;

    ArrayAdapter<String> adapterDirecciones;
    ArrayList<String> tiposDireccion;
    FirebaseDatabase database;
    DatabaseReference refTiposDirecciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacto);

        inicializaInterfaz();
        database = FirebaseDatabase.getInstance();
        refTiposDirecciones = database.getReference("tiposDirecciones");

        tiposDireccion = new ArrayList<>();
        tiposDireccion.addAll(Arrays.asList(getResources().getStringArray(R.array.spinner_direcciones)));
        refTiposDirecciones.setValue(tiposDireccion);
        refTiposDirecciones.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<String>> gtiTiposDirecciones = new GenericTypeIndicator<ArrayList<String>>() {};
                    tiposDireccion = snapshot.getValue(gtiTiposDirecciones);
                    adapterDirecciones = new ArrayAdapter<String>(AddContactoActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, tiposDireccion);
                    spTipoDireccion.setAdapter(adapterDirecciones);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapterDirecciones = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, tiposDireccion);
        spTipoDireccion.setAdapter(adapterDirecciones);


        spTipoDireccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //
                int ultimoIndice = parent.getCount() - 1;
                if (position == ultimoIndice) {
                    txtNombreDir.setVisibility(View.VISIBLE);
                } else {
                    txtNombreDir.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTipoTelefono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lo mismo
                int ultimoIndice = parent.getCount() - 1;
                if (position == ultimoIndice) {
                    txtNombreTel.setVisibility(View.VISIBLE);
                } else {
                    txtNombreTel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtNombre.getText().toString().isEmpty() &&
                        !txtApellidos.getText().toString().isEmpty() &&
                        !txtCalle.getText().toString().isEmpty() &&
                        !txtNumTelefono.getText().toString().isEmpty() &&
                        spTipoDireccion.getSelectedItemPosition() != 0 &&
                        spTipoTelefono.getSelectedItemPosition() != 0) {

                    if (txtNumero.getText().toString().isEmpty()) {
                        txtNumero.setText("0");
                    }

                    String tipoTelefono;
                    if (txtNombreTel.getVisibility() == View.VISIBLE) {
                        tipoTelefono = txtNombreTel.getText().toString();
                    } else {
                        tipoTelefono = (String) spTipoTelefono.getSelectedItem();
                    }
                    Telefono telefono = new Telefono(tipoTelefono, txtNumTelefono.getText().toString());

                    String tipoDireccion;
                    if (txtNombreDir.getVisibility() == View.VISIBLE) {
                        tipoDireccion = txtNombreDir.getText().toString();
                    } else {
                        tipoDireccion = (String) spTipoDireccion.getSelectedItem();
                    }
                    Direccion direccion = new Direccion(tipoDireccion,
                            txtCalle.getText().toString(),
                            Integer.parseInt(txtNumero.getText().toString()),
                            txtCiudad.getText().toString());

                    Contacto contacto = new Contacto();
                    contacto.setNombre(txtNombre.getText().toString());
                    contacto.setApellidos(txtApellidos.getText().toString());
                    contacto.setEmpresa(txtEmpresa.getText().toString());

                    contacto.getDirecciones().add(direccion);
                    contacto.getTelefonos().add(telefono);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("CONTACTO", contacto);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddContactoActivity.this, "ALGO TE FALTA PENDEJO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializaInterfaz() {
        txtNombre = findViewById(R.id.txtNombreContactoAdd);
        txtApellidos = findViewById(R.id.txtApellidosContactoAdd);
        txtEmpresa = findViewById(R.id.txtEmpresaContactoAdd);
        txtCalle = findViewById(R.id.txtCalleAdd);
        txtNumero = findViewById(R.id.txtNumeroAdd);
        txtCiudad = findViewById(R.id.txtCiudadAdd);
        txtNombreDir = findViewById(R.id.txtTipoDireccionAdd);
        txtNombreTel = findViewById(R.id.txtTipoTelefonoAdd);
        txtNumTelefono = findViewById(R.id.txtTelefonoAdd);
        spTipoDireccion = findViewById(R.id.spTipoDireccionAdd);
        spTipoTelefono = findViewById(R.id.spTipoTelefonoAdd);
        btnAgregar = findViewById(R.id.btnAgregarContactoAdd);
    }
}