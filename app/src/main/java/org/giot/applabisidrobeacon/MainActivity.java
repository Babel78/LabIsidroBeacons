package org.giot.applabisidrobeacon;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.estimote.coresdk.cloud.model.BeaconInfo;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.recognition.packets.Eddystone;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.recognition.utils.EstimoteBeacons;
import com.estimote.coresdk.service.BeaconManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.giot.applabisidrobeacon.Model.Calificacion;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();


        EstimoteSDK.initialize(getApplicationContext(),"beacon1-bix","257d621c9f372a11b7a6b8c630e10a31");


        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startEddystoneDiscovery();
            }
        });

        beaconManager.setEddystoneListener(new BeaconManager.EddystoneListener() {
            @Override
            public void onEddystonesFound(List<Eddystone> eddystones) {
                for(int i=0;i<eddystones.size();i++){
                    if(eddystones.get(i).instance.equals("1013cf850dde")){
                        ShowAlertDialog();
                    }
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.stopEddystoneDiscovery();
        beaconManager.disconnect();

    }

    boolean showAlert=false;
    public void ShowAlertDialog(){
        if(showAlert){
            return;
        }
        else{
            AlertDialog.Builder mBuilder=new AlertDialog.Builder(this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_calificacion,null);
            final EditText editText= mView.findViewById(R.id.editText);
            final RatingBar ratingBar=mView.findViewById(R.id.ratingBar);
            Button buttonEnviar=mView.findViewById(R.id.btn_enviar);

            mBuilder.setView(mView);
            final AlertDialog dialog=mBuilder.create();
            dialog.show();

            buttonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharpref=getSharedPreferences("fnombre",Context.MODE_PRIVATE);
                    String nombre=sharpref.getString("Nombre","Anonimo");
                    Calificacion calificacion=new Calificacion(nombre,editText.getText().toString(),ratingBar.getRating());
                    reference.push().setValue(calificacion);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Mensaje enviado",Toast.LENGTH_LONG).show();
                }
            });
            showAlert=true;
        }

    }

}
