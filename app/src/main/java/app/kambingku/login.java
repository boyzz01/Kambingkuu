package app.kambingku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    Button login;
    TextView home,forgot;
    EditText email,password;
    SharedPreferences sp;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final Button login = (Button) findViewById(R.id.btn_login);
        TextView home = (TextView) findViewById(R.id.home_txt);
        TextView forgot = (TextView) findViewById(R.id.forgot);
        final EditText email= (EditText) findViewById(R.id.email) ;
        final EditText password = (EditText) findViewById(R.id.pass);
        sp=getSharedPreferences("login",MODE_PRIVATE);


        if(sp.contains("username") && sp.contains("password")){
            Intent mainIntent = new Intent(login.this, login_drawer.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            splash.login=true;
            login.this.startActivity(mainIntent);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if((email.getText().toString().equals("admin@gmail.com")) && (password.getText().toString().equals("admin")))
                {

                    Intent mainIntent = new Intent(login.this, login_drawer.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    splash.login=true;
                    login.this.startActivity(mainIntent);

                    Toast.makeText(login.this,"Login Berhasil",Toast.LENGTH_SHORT).show();

                    login.this.finish();
                }
                else
                {
                    Toast.makeText(login.this,"Email atau Password Salah",Toast.LENGTH_SHORT).show();
                }*/

                String usermail=email.getText().toString();
                String pass = password.getText().toString();

                in masuk= new in();
                masuk.mas(usermail,pass);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(login.this, drawer.class);
                login.this.startActivity(mainIntent);

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login.this,"Untuk sementara fungsi ini belum dapat bekerja",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class in extends AsyncTask<String,Void,String>
    {
        String login_url = "https://www.idtronik.com/kambing/ajax/login";


        public void mas(final String usermail, final String pass)
        {
            pd=new ProgressDialog(app.kambingku.login.this);
            pd.setTitle("Loading");
            pd.setMessage("Loading....Please wait");
            pd.show();
            AndroidNetworking.post(login_url)
                    .addBodyParameter("username",usermail)
                    .addBodyParameter("password",pass)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response",response.toString());
                            try {
                                String msg=response.getString("msg");
                                if (msg.equals("Username or password wrong"))
                                {
                                    Toast.makeText(app.kambingku.login.this,"Username atau Password Salah",Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                                else
                                {
                                    try {
                                        JSONObject jo=response.getJSONObject("data");



                                        // Intent intent = new Intent(context,login_drawer.class);
                                        // Toast.makeText(context, "Login Sukses", Toast.LENGTH_SHORT).show();
                                        String namalengkap=jo.getString("nama_lengkap");
                                        String email=jo.getString("email");
                                        String telepon=jo.getString("telepon");
                                        String id=jo.getString("id_user");
                                        String foto=jo.getString("profile");
                                        String tempat=jo.getString("tempat_lahir");
                                        String tanggal=jo.getString("tgl_lahir");
                                        String alamat=jo.getString("alamat");
                                        String atasnama=jo.getString("atas_nama");
                                        String namabank=jo.getString("nama_bank");
                                        String norek=jo.getString("no_rek");
                                        String pekerjaan=jo.getString("pekerjaan");

                                        if (telepon.equals("null"))
                                        {
                                            telepon=" ";
                                        }
                                        SharedPreferences.Editor e = sp.edit();
                                        e.putString("pekerjaan",pekerjaan);
                                        e.putString("atasnama",atasnama);
                                        e.putString("namabank",namabank);
                                        e.putString("norek",norek);
                                        e.putString("username", usermail);
                                        e.putString("password", pass);
                                        e.putString("email",email);
                                        e.putString("telepon",telepon);
                                        e.putString("namalengkap",namalengkap);
                                        e.putString("id",id);
                                        e.putString("fotopp",foto);
                                        e.putString("tempat",tempat);
                                        e.putString("tanggal",tanggal);
                                        e.putString("alamat",alamat);
                                        e.commit();

                                        Intent intent = new Intent(app.kambingku.login.this,login_drawer.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        Toast.makeText(app.kambingku.login.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        startActivity(intent);
                                        finish();


                                    } catch (JSONException e) {
                                        Toast.makeText(app.kambingku.login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                        Log.d("error",e.getMessage());
                                        e.printStackTrace();
                                        pd.dismiss();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(app.kambingku.login.this,anError.toString(),Toast.LENGTH_SHORT).show();
                            Log.d("error",anError.toString());
                            pd.dismiss();

                        }
                    });
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}
