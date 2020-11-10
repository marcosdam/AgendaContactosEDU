package com.marcosledesma.agendacontactosedu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.marcosledesma.agendacontactosedu.adapters.DireccionesAdapter;
import com.marcosledesma.agendacontactosedu.adapters.TelefonosAdapter;
import com.marcosledesma.agendacontactosedu.modelos.Contacto;
import com.marcosledesma.agendacontactosedu.modelos.Direccion;
import com.marcosledesma.agendacontactosedu.modelos.Telefono;

public class EditContactoActivity extends AppCompatActivity {

    private Contacto contacto;
    // Listar Direcciones
    private ListView contenedorDirecciones;
    private int filaDirecciones;
    private DireccionesAdapter direccionesAdapter;
    // Listar Teléfonos
    private ListView contenedorTelefonos;
    private int filaTelefono;
    private TelefonosAdapter telefonosAdapter;

    // Vistas
    private ImageButton btnAgregarDireccion, btnAgregarTelefono;
    private TextView txtNombre, txtApellidos, txtEmpresa;
    private Button btnGuardar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacto);

        inicializaContacto();
        muestraDirecciones();
        muestraTelefonos();

        btnAgregarDireccion = findViewById(R.id.btnAddDireccion);
        btnAgregarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaDireccion().show();
            }
        });

        btnAgregarTelefono = findViewById(R.id.btnAddTelefono);
        btnAgregarTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaTelefono().show();
            }
        });

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacto.setNombre(txtNombre.getText().toString());
                contacto.setApellidos(txtApellidos.getText().toString());
                contacto.setEmpresa(txtEmpresa.getText().toString());

                Bundle bundle = getIntent().getExtras();
                bundle.putParcelable("CONTACTO", contacto);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("POS", getIntent().getExtras().getInt("POS"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void muestraTelefonos() {
        filaTelefono = android.R.layout.simple_list_item_2;
        contenedorTelefonos = findViewById(R.id.contenedorTelefonos);
        telefonosAdapter = new TelefonosAdapter(this, filaTelefono, contacto.getTelefonos());
        contenedorTelefonos.setAdapter(telefonosAdapter);
        contenedorTelefonos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (contacto.getTelefonos().size() > 1){
                    contacto.getTelefonos().remove(position);
                    telefonosAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    private AlertDialog creaTelefono(){
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        View telefonoAlert = LayoutInflater.from(this).inflate(R.layout.telefono_alert, null);
        // Sacamos los elementos de telefonoAlert
        EditText txtNombreTel = telefonoAlert.findViewById(R.id.txtNombreTelAlert);
        EditText txtTelefono = telefonoAlert.findViewById(R.id.txtNumTelAlert);

        // Los AlertDialog 3 posibles botones (setPositiveButton, setNegativeButton, setNeutralButton)
        constructor.setTitle("Agregar Teléfono");  // Título del Alert
        constructor.setView(telefonoAlert);    // Asociar la View
        constructor.setNegativeButton("Cancelar", null);
        constructor.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Telefono telefono = new Telefono();
                telefono.setNombre(txtNombreTel.getText().toString());
                telefono.setTelefono(txtTelefono.getText().toString());

                contacto.getTelefonos().add(telefono);
                telefonosAdapter.notifyDataSetChanged();
            }
        });
        return constructor.create();
    }

    private AlertDialog creaDireccion(){
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        View direccionAlert = LayoutInflater.from(this).inflate(R.layout.direccion_alert, null);
        // Sacamos los elementos de direccionAlert
        EditText txtNombre = direccionAlert.findViewById(R.id.txtNombreAlertDir);
        EditText txtCalle = direccionAlert.findViewById(R.id.txtCalleAlertDir);
        EditText txtNumero = direccionAlert.findViewById(R.id.txtNumAlertDir);
        EditText txtCiudad = direccionAlert.findViewById(R.id.txtCiudadAlertDirt);

        // Los AlertDialog 3 posibles botones (setPositiveButton, setNegativeButton, setNeutralButton)
        constructor.setTitle("Agregar Dirección");  // Título del Alert
        constructor.setView(direccionAlert);    // Asociar la View
        constructor.setNegativeButton("Cancelar", null);
        constructor.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txtNumero.getText().toString().isEmpty()){
                    txtNumero.setText("0");
                }
                Direccion direccion = new Direccion();
                direccion.setNombre(txtNombre.getText().toString());
                direccion.setCalle(txtCalle.getText().toString());
                direccion.setNumero(Integer.parseInt(txtNumero.getText().toString()));
                direccion.setCiudad(txtCiudad.getText().toString());
                contacto.getDirecciones().add(direccion);
                direccionesAdapter.notifyDataSetChanged();
            }
        });
        return constructor.create();
    }

    private void muestraDirecciones() {
        contenedorDirecciones = findViewById(R.id.contenedorDirecciones);
        filaDirecciones = R.layout.direccion_fila;
        direccionesAdapter = new DireccionesAdapter(this, filaDirecciones, contacto.getDirecciones());
        contenedorDirecciones.setAdapter(direccionesAdapter);
        // Eliminar dirección del contacto al mantener pulsado
        contenedorDirecciones.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Al menos tiene que haber uno
                if (contacto.getDirecciones().size() > 1){
                    contacto.getDirecciones().remove(position);
                    direccionesAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    private void inicializaContacto() {
        // Recogemos el contacto del intent
        if (getIntent().getExtras() != null){
            Contacto contacto = getIntent().getExtras().getParcelable("CONTACTO");
            if (contacto != null){
                this.contacto = contacto;
            }else{
                this.contacto = new Contacto();
            }
        }else{
            this.contacto = new Contacto();
        }
        txtNombre = findViewById(R.id.txtNombreEdit);
        txtApellidos = findViewById(R.id.txtApellidosEdit);
        txtEmpresa = findViewById(R.id.txtEmpresaEdit);

        txtNombre.setText(contacto.getNombre());
        txtApellidos.setText(contacto.getApellidos());
        txtEmpresa.setText(contacto.getEmpresa());
    }
}