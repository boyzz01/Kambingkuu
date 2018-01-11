package app.kambingku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.io.File;

public class isi_bank extends AppCompatActivity {
    EditText namabank,atasnama,norek;
    Button selesai;
    SharedPreferences sp;
    String nb,an,rek;
    File ktp,kk,pp=null;
    String sbank,snamab,snorel;
    String snama,semail,snikm,skewarga,stempat,stanggal,spekerjaan,sagama,salamat,sjk;
    String uname,nohp,pass;
    String url="https://idtronik.com/kambing/ajax/register";
    private ProgressDialog pd;
    Context c=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_bank);

        sp = isi_bank.this.getSharedPreferences("daftar", isi_bank.this.MODE_PRIVATE);


        namabank=findViewById(R.id.namabank);
        atasnama=findViewById(R.id.atasnama);
        norek=findViewById(R.id.norek);
        selesai=findViewById(R.id.selesai);

        Intent intent=getIntent();

        ktp=(File)getIntent().getExtras().get("ktp");
        kk=(File)getIntent().getExtras().get("kk");
        pp=(File)getIntent().getExtras().get("pp");




       snama=sp.getString("nama"," ");
       semail=sp.getString("email"," ");
       snikm=sp.getString("nik"," ");
       skewarga=sp.getString("warga"," ");
       stempat=sp.getString("tempatl"," ");
       stanggal=sp.getString("tanggal"," ");
       spekerjaan=sp.getString("pekerjaan"," ");
       sagama=sp.getString("agama"," ");
       salamat=sp.getString("alamat"," ");
       uname=sp.getString("uname"," ");
       pass=sp.getString("pass"," ");
       nohp=sp.getString("nohp"," ");
       sjk=sp.getString("jk"," ");






        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbank=namabank.getText().toString();
                snamab=atasnama.getText().toString();
                snorel=norek.getText().toString();
                if(sbank.equals("")|| snamab.equals("") || snorel.equals(""))
                {
                    Toast.makeText(isi_bank.this,"Tidak Boleh ada data yang kosong",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    konek data=new konek();
                    data.ambil();
                }

            }
        });


    }

    public class konek extends AsyncTask<String,Void,String>
    {

        public void ambil()
        {
            pd=new ProgressDialog(c);
            pd.setTitle("Loading");
            pd.setMessage("Loading....Please wait");
            pd.show();
            AndroidNetworking.upload(url)
                    .addMultipartFile("ktp",ktp)
                    .addMultipartFile("kk",kk)
                    .addMultipartFile("diri",pp)
                    .addMultipartParameter("username",uname)
                    .addMultipartParameter("password",pass)
                    .addMultipartParameter("nama_lengkap",snama)
                    .addMultipartParameter("id_user",snikm)
                    .addMultipartParameter("tgl_lahir",stanggal)
                    .addMultipartParameter("tempat_lahir",stempat)
                    .addMultipartParameter("pekerjaan",spekerjaan)
                    .addMultipartParameter("kewarganegaraan",skewarga)
                    .addMultipartParameter("agama",sagama)
                    .addMultipartParameter("alamat",salamat)
                    .addMultipartParameter("gender",sjk)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("berhasil",response.toString());
                            pd.dismiss();
                        }

                        @Override
                        public void onError(ANError anError) {

                            Toast.makeText(isi_bank.this,anError.toString(),Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });


        }
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
