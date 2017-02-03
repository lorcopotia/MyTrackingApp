package es.covent.mytrackingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.covent.mytrackingapp.data.UserLocalStore;
import es.covent.mytrackingapp.data.*;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://decuba.info/duny/";
    private int EXIT_COUNTER = 0;

    EditText edUser;
    EditText edPass;
    Button btnEnter;
    TextView tvRegister;
    ProgressBar pb;

    UserLocalStore userLocalStore;

    List<Usuario> usList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUser = (EditText) findViewById(R.id.edUser);
        edPass = (EditText) findViewById(R.id.edPass);

        btnEnter = (Button) findViewById(R.id.btnEnter);

        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                edUser.setText("");
                edPass.setText("");
            }
        });

        pb = (ProgressBar) findViewById(R.id.pBar);
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {

        if(EXIT_COUNTER == 1) {
            super.onBackPressed();
            this.finish();
        }
        else {
            EXIT_COUNTER++;
            Toast.makeText(this, R.string.msg_close_app, Toast.LENGTH_LONG).show();
        }
    }

    public boolean isDataEmpty(){
        return (edUser.getText().toString().isEmpty() || edPass.getText().toString().isEmpty());
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void accesApp(View v) {
        String uri = SERVER_ADDRESS + "nexus_websrv.php";

        if (!isOnline()){
            Toast.makeText(this, "No hay conexion a internet", Toast.LENGTH_LONG).show();
        }
        if (isDataEmpty()){
            Toast.makeText(this, "Introduzca todos los datos", Toast.LENGTH_LONG).show();
        }
        else{
            requestData(uri, edUser.getText().toString(), edPass.getText().toString());
        }
    }

    private void requestData(String uri, String user, String pass){
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri(uri);
        p.setParam("username", user);
        p.setParam("password", pass);

        MyTask task = new MyTask();
        task.execute(p);
    }

    protected void updateDisplay(){
        if (usList != null){
            for (Usuario us : usList){
                Toast.makeText(this, "Bienvenido " + us.getNombre(), Toast.LENGTH_LONG).show();
                us.setUsuario(edUser.getText().toString());
                us.setContrasena(edPass.getText().toString());
                userLocalStore.setUserLoggedIn(true);
                userLocalStore.storeUserData(us);
                startActivity(new Intent(this, DashboardActivity.class));
            }
        } else {
            Toast.makeText(MainActivity.this, "Error de autenticacion " + edUser.getText().toString(), Toast.LENGTH_LONG).show();
            edPass.setText("");
        }
    }

    private class MyTask extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(RequestPackage... params) {

            return HttpManager.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            usList = UserXMLParser.parseFeed(result);
            updateDisplay();

            pb.setVisibility(View.INVISIBLE);

        }
    }

}
