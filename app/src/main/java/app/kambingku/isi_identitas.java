package app.kambingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class isi_identitas extends AppCompatActivity {

    Button lanjut;
    EditText namaedit,email,nik,kewarganegaraan,tempatlahir,tanggallahir,pekerjaan,agama,alamat;
    RadioGroup jk;
    String sjk="";
    SharedPreferences sp;
    String snama,semail,snikm,skewarga,stempat,stanggal,spekerjaan,sagama,salamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_identitas);

        namaedit=findViewById(R.id.nama);

        nik=findViewById(R.id.nik);
        kewarganegaraan=findViewById(R.id.kewarganegaraan);
        tempatlahir=findViewById(R.id.tempatlahir);
        tanggallahir=findViewById(R.id.tanggallahir);
        pekerjaan=findViewById(R.id.pekerjaan);
        agama=findViewById(R.id.agama);
        alamat=findViewById(R.id.alamat);
        jk=findViewById(R.id.jeniskelamain);
        lanjut=findViewById(R.id.lanjut);
        sp = isi_identitas.this.getSharedPreferences("daftar",isi_identitas.this.MODE_PRIVATE);







        jk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.wanita)
                {
                    Log.d("wanita","wan");
                    sjk="wanita";
                }
                if (i==R.id.pria)
                {
                    sjk="pria";
                }
            }
        });
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snama=namaedit.getText().toString();
                snikm=nik.getText().toString();
                skewarga=kewarganegaraan.getText().toString();
                stempat=tempatlahir.getText().toString();
                stanggal=tanggallahir.getText().toString();
                spekerjaan=pekerjaan.getText().toString();
                sagama=agama.getText().toString();
                salamat=alamat.getText().toString();
                if(snama.equals("")  || snikm.equals("") || skewarga.equals("") || stempat.equals("") || stanggal.equals("") || spekerjaan.equals("") || sagama.equals("") ||salamat.equals("") || sjk.equals(""))
                {
                    Toast.makeText(isi_identitas.this,"Tidak Boleh ada data yang kosong",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    SharedPreferences.Editor e = sp.edit();
                    e.putString("nama",snama);

                    e.putString("nik",snikm);
                    e.putString("warga",skewarga);
                    e.putString("tempatl",stempat);
                    e.putString("tanggal",stanggal);
                    e.putString("pekerjaan",spekerjaan);
                    e.putString("agama",sagama);
                    e.putString("alamat",salamat);
                    e.putString("jk",sjk);

                    e.commit();
                    Intent go = new Intent(isi_identitas.this,upload_dokumen.class);
                    startActivity(go);
                }
            }
        });




    }
}
