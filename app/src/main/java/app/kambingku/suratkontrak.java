package app.kambingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class suratkontrak extends AppCompatActivity {

    TextView namaa,nik,ttl,alamat;
    Button siap;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suratkontrak);

        namaa=findViewById(R.id.nama);
        nik=findViewById(R.id.ktp);
        ttl=findViewById(R.id.ttl);
        alamat=findViewById(R.id.alamat);
        siap=findViewById(R.id.terima);

        sp=getSharedPreferences("login",MODE_PRIVATE);

        namaa.setText(sp.getString("namalengkap",""));
        nik.setText(sp.getString("id"," "));
        ttl.setText(sp.getString("tempat"," ")+","+sp.getString("tanggal"," "));
        alamat.setText(sp.getString("alamat"," "));



        siap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(suratkontrak.this,login_drawer.class);
                back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
            }
        });



    }
}
