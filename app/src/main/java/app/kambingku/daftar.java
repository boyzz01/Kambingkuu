package app.kambingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class daftar extends AppCompatActivity {

    TextView loginedit;
    EditText uname,email,nohp,pass,kpass;
    Button daftarbtn;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        sp = daftar.this.getSharedPreferences("daftar", daftar.this.MODE_PRIVATE);


        loginedit=findViewById(R.id.login);
        uname=findViewById(R.id.ktp);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        nohp=findViewById(R.id.nohp);
        kpass=findViewById(R.id.kpass);
        daftarbtn=findViewById(R.id.daftar);





        daftarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uname.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("") || nohp.getText().toString().equals("") || kpass.getText().toString().equals(""))
                {
                    Toast.makeText(daftar.this,"Tidak Boleh ada data yang kosong",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!pass.getText().toString().equals(kpass.getText().toString()))
                    {
                        Toast.makeText(daftar.this,"Password dan Konfirmasi Password anda Berbeda",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final String sname,semail,spass,snohp,skpass;

                        sname=uname.getText().toString();
                        semail=email.getText().toString();
                        spass=pass.getText().toString();
                        skpass=kpass.getText().toString();
                        snohp=nohp.getText().toString();
                        SharedPreferences.Editor e = sp.edit();
                        e.putString("uname",sname);
                        e.putString("pass",spass);
                        e.putString("nohp",snohp);
                        e.putString("email",semail);
                        e.commit();

                        Intent lanjut=new Intent(daftar.this,isi_identitas.class);
                        startActivity(lanjut);
                    }
                }
            }
        });



        loginedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent balik=new Intent(daftar.this, login.class);
                startActivity(balik);
            }
        });
    }
}
