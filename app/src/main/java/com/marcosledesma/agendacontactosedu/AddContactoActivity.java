package com.marcosledesma.agendacontactosedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.marcosledesma.agendacontactosedu.modelos.Contacto;
import com.marcosledesma.agendacontactosedu.modelos.Direccion;
import com.marcosledesma.agendacontactosedu.modelos.Telefono;

public class AddContactoActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtEmpresa;
    private EditText txtCalle, txtNumero, txtCiudad, txtNombreDir;
    private EditText txtNumTelefono, txtNombreTel;
    private Spinner spTipoDireccion, spTipoTelefono;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacto);
        inicializaInterfaz();

        spTipoDireccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //
                int ultimoIndice = parent.getCount() - 1;
                if (position == ultimoIndice){
                    txtNombreDir.setVisibility(View.VISIBLE);
                }else{
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
                if (position == ultimoIndice){
                    txtNombreTel.setVisibility(View.VISIBLE);
                }else{
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
                if(!txtNombre.getText().toString().isEmpty() &&
                   !txtApellidos.getText().toString().isEmpty() &&
                   !txtCalle.getText().toString().isEmpty() &&
                   !txtNumTelefono.getText().toString().isEmpty() &&
                   spTipoDireccion.getSelectedItemPosition() != 0 &&
                   spTipoTelefono.getSelectedItemPosition() != 0){

                    if(txtNumero.getText().toString().isEmpty()){
                        txtNumero.setText("0");
                    }

                    String tipoTelefono;
                    if(txtNombreTel.getVisibility() == View.VISIBLE){
                        tipoTelefono = txtNombreTel.getText().toString();
                    }else{
                        tipoTelefono = (String)spTipoTelefono.getSelectedItem();
                    }
                    Telefono telefono = new Telefono(tipoTelefono, txtNumTelefono.getText().toString());

                    String tipoDireccion;
                    if (txtNombreDir.getVisibility() == View.VISIBLE){
                        tipoDireccion = txtNombreDir.getText().toString();
                    }else{
                        tipoDireccion = (String)spTipoDireccion.getSelectedItem();
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
                }
                else{
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