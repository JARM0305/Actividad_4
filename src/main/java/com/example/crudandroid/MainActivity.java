package com.example.crudandroid;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crudandroid.entity.User;
import com.example.crudandroid.model.UserDao;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Validate";
    private Context context;
    private EditText etDocumento;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etUsuario;
    private EditText etContra;
    private ListView listUsers;
    String documento;
    String usuario;
    String nombres;
    String apellidos;
    String contra;
    SQLiteDatabase baseDatos;
    private Button btnSave;
    private Button btnListUsers;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.LinearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = this;
        initObject();
        btnSave.setOnClickListener(this::createUser);
        btnListUsers.setOnClickListener(this::listUserShow);
        btnBuscar.setOnClickListener(this::gestionUsu);
    }

    private void initObject(){
        btnSave = findViewById(R.id.btnRegistrar);
        btnListUsers = findViewById(R.id.btnListar);
        btnBuscar = findViewById(R.id.btnBuscar);
        etNombres = findViewById(R.id.etNombres2);
        etApellidos = findViewById(R.id.etApellidos2);
        etDocumento = findViewById(R.id.etDocumento2);
        etUsuario = findViewById(R.id.etUsuario2);
        etContra = findViewById(R.id.etContra2);
        listUsers = findViewById(R.id.lvList);
    }

    private void getData(){
        documento = etDocumento.getText().toString();
        usuario = etUsuario.getText().toString();
        nombres = etNombres.getText().toString();
        apellidos = etApellidos.getText().toString();
        contra = etContra.getText().toString();
    }

    private void createUser(View view){
        getData();

        if(documento.isEmpty() && usuario.isEmpty() && nombres.isEmpty() && apellidos.isEmpty() && contra.isEmpty()){
            Toast.makeText(context, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
        }
        else{
            User usuarioNuevo = new User(documento, nombres, apellidos, usuario, contra);
            UserDao userDao = new UserDao(context, view);
            userDao.insertUser(usuarioNuevo);
            limpiarFormulario(view);
            listUserShow(view);
        }
    }

    private void listUserShow(View view){
        UserDao dao = new UserDao(context, findViewById(R.id.lvList));
        ArrayList<User> users = dao.getUserList();

        if(!users.isEmpty()){
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            listUsers.setAdapter(adapter);
        }
        else{
            Toast.makeText(context, "No se encontraron usuarios activos", Toast.LENGTH_LONG).show();
        }
    }

    private void gestionUsu(View view){
        Intent ir = new Intent(this, GestionUsuarios.class);
        startActivity(ir);
    }

    private void limpiarFormulario(View view){
        etDocumento.setText("");
        etUsuario.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etContra.setText("");
    }
}