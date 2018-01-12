package app.kambingku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class keranjang_belanja extends AppCompatActivity {
    SharedPreferences sp,sdata,transaksi;
    MySQLClient data;
    RecyclerView recyclerView;
    TextView total;
    ProgressDialog pd;
    int kode_unik;
    int sum;
    TextView textViewkode,textViewsum;

    public static Boolean kosong=false;


    Button lanjut;

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        if (!sp.contains("notif"))
        {
            kosong=true;
            Toast.makeText(this,"Troli Anda Kosong",Toast.LENGTH_SHORT).show();
        }
        else if (sp.getInt("notif",0)==0)
        {
            kosong=true;
            Toast.makeText(this,"Troli Anda Kosong",Toast.LENGTH_SHORT).show();
        }
        else
        {
            kosong=false;
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_belanja);



        total=findViewById(R.id.jumlah);


        textViewkode=findViewById(R.id.kodeunik);
        textViewsum=findViewById(R.id.sum);

        lanjut=findViewById(R.id.lanjut);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data=new MySQLClient(this);
        sp = this.getSharedPreferences("login", this.MODE_PRIVATE);

        int temp=sp.getInt("notif",0);
        Log.d("notif",""+temp);







        String url="https://idtronik.com/kambing/ajax/product_details/?id=";
        data.keranjang(url,temp);


        if (!sp.contains("notif"))
        {
            kosong=true;
            Toast.makeText(this,"Troli Anda Kosong",Toast.LENGTH_SHORT).show();
        }
        else if (sp.getInt("notif",0)==0)
        {
            kosong=true;
            Toast.makeText(this,"Troli Anda Kosong",Toast.LENGTH_SHORT).show();
        }
        else
        {
            kosong=false;
        }



        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sp.contains("notif"))
                {
                    kosong=true;
                    Log.d("notif","notif");

                }
                else if (sp.getInt("notif",0)==0)
                {
                    kosong=true;

                    Log.d("0","0");
                }
                else
                {
                    kosong=false;
                }
                if (kosong==true)
                {
                    Toast.makeText(keranjang_belanja.this,"Troli Anda Kosong",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(sp.contains("username") && sp.contains("password")){
                        konek kone=new konek();
                        kone.getkode();

                    }
                    else
                    {
                        Intent mainIntent = new Intent(keranjang_belanja.this, daftar.class);

                        keranjang_belanja.this.startActivity(mainIntent);

                    }
                }
            }
        });
    }

    public class konek extends AsyncTask<String,Void,String>
    {
        public  void getkode()
        {
            final int jumlah=sp.getInt("notif",0);



            String id_user=sp.getString("id"," ");
            pd=new ProgressDialog(keranjang_belanja.this);
            pd.setTitle("Loading");
            pd.setMessage("Loading....Please wait");
            pd.show();
            pd.setCancelable(false);
           // url = "https://idtronik.com/kambing/ajax/pembayaran_details";


            AndroidNetworking.post("https://idtronik.com/kambing/ajax/pembayaran")
                    .addBodyParameter("id_user",id_user)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //String data=between(response.toString(),"\"data\":","}]}")+"}]";

                            Log.d("response",response.toString());


                            String id_transaksi= null;
                             kode_unik=0;
                            try {
                                id_transaksi = response.getString("id_transaksi");
                                kode_unik=response.getInt("kode_unik");

                                transaksi=keranjang_belanja.this.getSharedPreferences("transaksi", keranjang_belanja.this.MODE_PRIVATE);
                                SharedPreferences.Editor e=transaksi.edit();

                                e.putString("id",id_transaksi);
                                e.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("idtrans",id_transaksi);



                            for (int i=1;i<=jumlah;i++)
                            {
                                sdata = keranjang_belanja.this.getSharedPreferences("data"+i, keranjang_belanja.this.MODE_PRIVATE);




                                int id_ternak=sdata.getInt("id",0);
                                int harga=sdata.getInt("harga",0);
                                Log.d("harga",""+harga);

                                //  Bundle a = new Bundle()
/*
                                    Log.d("id_ternak",""+id_ternak);
                                    Log.d("harga",""+harga);
                                    Log.d("id_transaksi",""+id_transaksi);
                                    */
                                //
                                // SharedPreferences.Editor e = sdata.edit();

                                AndroidNetworking.post("https://idtronik.com/kambing/ajax/pembayaran_details")
                                        .addBodyParameter("id",""+id_ternak)
                                        .addBodyParameter("id_transaksi",""+id_transaksi)
                                        .addBodyParameter("price",""+harga)
                                        .setPriority(Priority.HIGH)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                // String data=between(response.toString(),"\"data\":","}]}")+"}]";

                                                Log.d("berhasil",response.toString());

                                                int temptotal=sp.getInt("jumlah",0);
                                                Intent mainIntent = new Intent(keranjang_belanja.this, KonfirmasiPembayaran.class);

                                                transaksi=keranjang_belanja.this.getSharedPreferences("transaksi", keranjang_belanja.this.MODE_PRIVATE);
                                                SharedPreferences.Editor e=transaksi.edit();
                                                e.putInt("kode_unik",kode_unik);
                                                e.putInt("total",temptotal);
                                                e.commit();

                                                SharedPreferences.Editor a=sp.edit();
                                                a.putInt("notif",0);
                                              a.commit();

                                                keranjang_belanja.this.startActivity(mainIntent);
                                                finish();

                                                pd.dismiss();
                                            }

                                            @Override
                                            public void onError(ANError anError) {

                                                pd.dismiss();

                                                Log.d("error",anError.getMessage());
                                                Toast.makeText(keranjang_belanja.this,anError.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        });


                            }








                            pd.dismiss();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("dalam",anError.getMessage());
                            pd.dismiss();
                            Toast.makeText(keranjang_belanja.this,anError.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });







        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
