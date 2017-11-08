package org.giot.applabisidrobeacon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FullscreenActivity extends AppCompatActivity {

    private Button buttonName;
    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        buttonName=findViewById(R.id.btn_ingresar);
        editTextName=findViewById(R.id.ed_name);

        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextName.getText().toString().equals("")){
                    SharedPreferences sharpref=getSharedPreferences("fnombre",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharpref.edit();
                    editor.putString("Nombre",editTextName.getText().toString());
                    editor.commit();
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Escriba su nombre",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
