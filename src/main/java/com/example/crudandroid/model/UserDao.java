package com.example.crudandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.crudandroid.entity.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UserDao {
    Context context;
    View view;
    private ManagerDataBase managerDataBase;
    //private User user;

    public UserDao(Context context, View view) {
        this.context = context;
        this.view = view;
        managerDataBase = new ManagerDataBase(this.context);
    }

    public void insertUser(User usuario){
        try{
            SQLiteDatabase db = managerDataBase.getWritableDatabase();

            if(db != null){
                ContentValues values = new ContentValues();
                values.put("use_document", usuario.getDocument());
                values.put("use_user", usuario.getUser());
                values.put("use_names", usuario.getNames());
                values.put("use_lastnames", usuario.getLastnames());
                values.put("use_password", usuario.getPassword());
                values.put("use_status", 1);

                long cod = db.insert("users", null,values);
                Snackbar.make(this.view, "Se ha registrado el usuario: " + usuario.getNames() + " " + usuario.getLastnames(), Snackbar.LENGTH_LONG).show();
            }
            else{
                Snackbar.make(this.view, "No se ha registrado el usuario", Snackbar.LENGTH_LONG).show();
            }
        }
        catch(SQLException ex){
            Log.i("BD",""+ ex);
        }
    }

    public ArrayList<User> getUserList(){
        ArrayList<User> listUsers = new ArrayList<>();

        try{
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "SELECT * FROM users WHERE use_status = 1";

            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do{
                    User user = new User();
                    user.setDocument(cursor.getString(0));
                    user.setUser(cursor.getString(1));
                    user.setNames(cursor.getString(2));
                    user.setLastnames(cursor.getString(3));
                    user.setPassword(cursor.getString(4));

                    listUsers.add(user);
                }while(cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }
        catch(SQLException ex){
            Log.i("BD","" + ex);
        }

        return listUsers;
    }

    public ArrayList<User> obtenerUsuInactivos(){
        ArrayList<User> usuInactivos = new ArrayList<>();

        try{
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "SELECT * FROM users WHERE use_status = 0";
            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){
                do{
                    User usuario = new User();

                    usuario.setDocument(cursor.getString(0));
                    usuario.setUser(cursor.getString(1));
                    usuario.setNames(cursor.getString(2));
                    usuario.setLastnames(cursor.getString(3));
                    usuario.setPassword(cursor.getString(4));

                    usuInactivos.add(usuario);
                }while(cursor.moveToNext());
            }

        }
        catch(SQLException ex){
            Log.i("BD", "" + ex);
        }

        return usuInactivos;
    }

    public User buscarUsuario(String documento){
        User usuario = new User();

        try{
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "SELECT * FROM users WHERE use_document = " + documento;
            Cursor cursor = db.rawQuery(query, null);

            if(cursor.moveToFirst()){
                usuario.setDocument(cursor.getString(0));
                usuario.setUser(cursor.getString(1));
                usuario.setNames(cursor.getString(2));
                usuario.setLastnames(cursor.getString(3));
                usuario.setPassword(cursor.getString(4));
            }

            cursor.close();
            db.close();
        }
        catch(SQLException ex){
            Log.i("BD", "" + ex);
        }

        return usuario;
    }

    public void editarUsuario(User usuario){
        try{
            SQLiteDatabase db = managerDataBase.getWritableDatabase();
            String where = "use_document = " + usuario.getDocument();

            ContentValues datos = new ContentValues();
            datos.put("use_user", usuario.getUser());
            datos.put("use_names", usuario.getNames());
            datos.put("use_lastnames", usuario.getLastnames());
            datos.put("use_password", usuario.getPassword());

            int filasActualizadas = db.update("users", datos, where, null);

            if(filasActualizadas > 0){
                Toast.makeText(context, "El registrado ha sido actualizado", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context, "El registro no se actualizo", Toast.LENGTH_LONG).show();
            }
        }
        catch(SQLException ex){
            Log.i("BD", "" + ex);
        }
    }

    public void inactivarUsuario(User usuario){
        try{
            SQLiteDatabase db =managerDataBase.getWritableDatabase();
            String where = "use_document = " + usuario.getDocument();

            ContentValues dato = new ContentValues();
            dato.put("use_status", 0);

            int filaActualizada = db.update("users", dato, where, null);

            if(filaActualizada > 0){
                Toast.makeText(context, "El usuario ha sido inactivado", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context, "El usuario no ha sido inactivado", Toast.LENGTH_LONG).show();
            }

        }
        catch(SQLException ex){
            Log.i("BD", "" + ex);
        }
    }
}
