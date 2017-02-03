package es.covent.mytrackingapp.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Duanel Garrido on 05/11/2015.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(Usuario usuario){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("id", usuario.id);
        spEditor.putString("nombre", usuario.nombre);
        spEditor.putString("usuario", usuario.usuario);
        spEditor.putString("correo", usuario.correo);
        spEditor.putString("contrasena", usuario.contrasena);
        spEditor.commit();
    }

    public Usuario getLoggedInUser(){
        int id = userLocalDatabase.getInt("id", 0);
        String nombre = userLocalDatabase.getString("nombre", "");
        String usuario = userLocalDatabase.getString("usuario", "");
        String correo = userLocalDatabase.getString("correo", "");
        String contrasena = userLocalDatabase.getString("contrasena", "");

        Usuario storedUser = new Usuario(id, nombre, usuario, correo, contrasena);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn", false)== true){
            return true;
        } else {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
