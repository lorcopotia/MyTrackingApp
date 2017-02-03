package es.covent.mytrackingapp.data;


/**
 * Created by Duanel Garrido on 19/10/2015.
 */
public class Usuario {

    protected int id;
    protected String nombre;
    protected String usuario;
    protected String correo;
    protected String contrasena;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Usuario(int id, String nombre, String usuario, String correo, String contrasena){
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasena = contrasena;
    }
}
