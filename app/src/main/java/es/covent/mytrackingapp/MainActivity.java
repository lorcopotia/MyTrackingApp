package es.covent.mytrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.covent.mytrackingapp.data.UserLocalStore;

public class MainActivity extends AppCompatActivity {

    EditText edUser;
    EditText edPass;
    Button btnEnter;
    TextView tvRegister;

    UserLocalStore userLocalStore;

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
            }
        });
    }

    public void showMsg(View view){
        if (!isDataFilled()){
            Toast.makeText(this, "Campos llenos", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isDataFilled(){
        return (edUser.getText().toString().isEmpty() || edPass.getText().toString().isEmpty());
    }

}
