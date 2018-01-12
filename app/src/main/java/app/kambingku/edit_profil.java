package app.kambingku;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

public class edit_profil extends AppCompatActivity {

    EditText bank,atasnamabank,norek,email,alamat,pekerjaan;
    Button ubah;
    SharedPreferences sp;
    private ProgressDialog pd;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        sp=getSharedPreferences("login",MODE_PRIVATE);

        bank=findViewById(R.id.bank);
        atasnamabank=findViewById(R.id.atasnamabank);
        norek=findViewById(R.id.nomorrekening);
        email=findViewById(R.id.email);
        alamat=findViewById(R.id.alamat);
        pekerjaan=findViewById(R.id.pekerjaan);
        ubah=findViewById(R.id.Ubah);

       id=sp.getString("id"," ");

        alamat.setText(sp.getString("alamat"," "));
        bank.setText(sp.getString("namabank"," "));
        atasnamabank.setText(sp.getString("atasnama"," "));
        norek.setText(sp.getString("norek"," "));
        email.setText(sp.getString("email"," "));
        alamat.setText(sp.getString("alamat"," "));
        pekerjaan.setText(sp.getString("pekerjaan"," "));



        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bank.getText().toString().equals("") || atasnamabank.getText().toString().equals("") || norek.getText().toString().equals("") || email.getText().toString().equals("") || alamat.getText().toString().equals("") || pekerjaan.getText().toString().equals(""))
                {
                    Toast.makeText(edit_profil.this,"Tidak Boleh Ada data yang kosong",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    konek data=new konek();
                    data.update();
                }


            }
        });
    }

    public class konek extends AsyncTask<String,Void,String>
    {

        public void update()
        {
            pd=new ProgressDialog(edit_profil.this);
            pd.setTitle("Loading");
            pd.setMessage("Loading....Please wait");
            pd.show();
            pd.setCancelable(false);

            AndroidNetworking.post("https://idtronik.com/kambing/Ajax/update_profile")
                    .addBodyParameter("id_user",id)
                    .addBodyParameter("email",email.getText().toString())
                    .addBodyParameter("pekerjaan",pekerjaan.getText().toString())
                    .addBodyParameter("alamat",alamat.getText().toString())
                    .addBodyParameter("atas_nama",atasnamabank.getText().toString())
                    .addBodyParameter("nama_bank",bank.getText().toString())
                    .addBodyParameter("no_rek",norek.getText().toString())
                    .addBodyParameter("pass","")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("berhasil",response.toString());


                            try {
                                String message=response.getString("msg");
                                if (message.equals("success"))
                                {

                                    SharedPreferences.Editor e=sp.edit();
                                    e.putString("pekerjaan",pekerjaan.getText().toString());
                                    e.putString("atasnama",atasnamabank.getText().toString());
                                    e.putString("namabank",bank.getText().toString());
                                    e.putString("norek",norek.getText().toString());
                                    e.putString("alamat",alamat.getText().toString());
                                    e.putString("email",email.getText().toString());
                                    e.commit();
                                    pd.dismiss();
                                    Toast.makeText(edit_profil.this,"Update Data Berhasil",Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                pd.dismiss();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("error",anError.getMessage());
                            Toast.makeText(edit_profil.this,"Error"+anError.getMessage(),Toast.LENGTH_SHORT).show();
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
