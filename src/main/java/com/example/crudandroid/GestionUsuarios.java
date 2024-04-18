package com.example.crudandroid;

import android.content.Context;
import android.content.Intent;
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

public class GestionUsuarios extends AppCompatActivity {
    private Context context;
    private EditText etDocumento2;
    private  EditText etUsuario2;
    private EditText etNombres2;
    private EditText etApellidos2;
    private EditText etContra2;
    private Button btnBuscarUsu;
    private Button btnEditarUsu;
    private Button btnInactivar;
    private Button btnVolver;
    private ListView lvListInactivos;
    String documento2;
    String usuario2;
    String nombres2;
    String apellidos2;
    String contra2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestion_usuarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = this;
        InitObjects();
        btnBuscarUsu.setOnClickListener(this::buscarUsu);
        btnEditarUsu.setOnClickListener(this::editarUsu);
        btnInactivar.setOnClickListener(this::inactivos);
        btnVolver.setOnClickListener(this::volver);
    }

    private void InitObjects(){
        etDocumento2 = findViewById(R.id.etDocumento2);
        etUsuario2 = findViewById(R.id.etUsuario2);
        etNombres2 = findViewById(R.id.etNombres2);
        etApellidos2 = findViewById(R.id.etApellidos2);
        etContra2 = findViewById(R.id.etContra2);
        btnBuscarUsu = findViewById(R.id.btnBuscarUsu);
        btnEditarUsu = findViewById(R.id.btnEditarUsu);
        btnInactivar = findViewById(R.id.btnInactivar);
        btnVolver = findViewById(R.id.btnVolver);
        lvListInactivos = findViewById(R.id.lvListInactivos);
    }

    private void getData(){
        documento2 = etDocumento2.getText().toString();
        usuario2 = etUsuario2.getText().toString();
        nombres2 = etNombres2.getText().toString();
        apellidos2 = etApellidos2.getText().toString();
        contra2 = etContra2.getText().toString();
    }

    private void buscarUsu(View view){
        getData();
        UserDao dao = new UserDao(context, view);

        if(!documento2.isEmpty()){
            User usuario = dao.buscarUsuario(documento2);
            etDocumento2.setText(usuario.getDocument());
            etUsuario2.setText(usuario.getUser());
            etNombres2.setText(usuario.getNames());
            etApellidos2.setText(usuario.getLastnames());
            etContra2.setText(usuario.getPassword());
        }
        else{
            Toast.makeText(context, "Debe ingresar un número de documento para realizar la busqueda", Toast.LENGTH_SHORT).show();
        }
    }

    private void editarUsu(View view){
        getData();
        UserDao dao = new UserDao(context, view);
        Intent volver = new Intent(context, MainActivity.class);

        if(!documento2.isEmpty()){
            User usuario = new User(documento2, nombres2, apellidos2, usuario2, contra2);
            dao.editarUsuario(usuario);
            startActivity(volver);
        }
        else{
            Toast.makeText(context, "Primero busque un usuario al cual actualizar", Toast.LENGTH_LONG).show();
        }
    }

    private void inactivos(View view){
        getData();
        UserDao dao = new UserDao(context, view);

        if(!documento2.isEmpty()){
            User usuario = new User(documento2, nombres2, apellidos2, usuario2, contra2);
            dao.inactivarUsuario(usuario);
            listInactivos(view);
        }
        else{
            listInactivos(view);
        }
    }

    private void listInactivos(View view){
        UserDao dao = new UserDao(context, findViewById(R.id.lvList));
        ArrayList<User> usersInativos = dao.obtenerUsuInactivos();

        if(!usersInativos.isEmpty()){
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersInativos);
            lvListInactivos.setAdapter(adapter);
        }
        else{
            Toast.makeText(context, "No se encontraron usuarios inactivos", Toast.LENGTH_LONG).show();
        }
    }

    private void volver(View view){
        Intent volver = new Intent(context, MainActivity.class);
        startActivity(volver);
    }
}