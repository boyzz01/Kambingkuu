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
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class KonfirmasiPembayaran extends AppCompatActivity {

    SharedPreferences sp,sdata,transaksi;
    TextView jumlah;
    Button lanjut;
    TextView textView,uniktxt,semuaa;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembayaran);
        Context c=this;

        sp = c.getSharedPreferences("login", c.MODE_PRIVATE);
        transaksi=KonfirmasiPembayaran.this.getSharedPreferences("transaksi", KonfirmasiPembayaran.this.MODE_PRIVATE);

        jumlah=findViewById(R.id.total);

        textView=findViewById(R.id.namaa);

        String nama=sp.getString("namalengkap"," ");

        uniktxt=findViewById(R.id.kodeunik);
        semuaa=findViewById(R.id.sum);

        textView.setText("Terima Kasih "+nama+" telah membeli Kambing kami, untuk menyelesaikan transaksi ini, silahkan melakukan transfer biaya pada rekening Berikut :");

        lanjut=findViewById(R.id.lanjut);
        Intent intent = getIntent();
        int temp=transaksi.getInt("total",0);
        int unik=transaksi.getInt("kode_unik",0);
        int semua=unik+temp;


        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent go = new Intent(KonfirmasiPembayaran.this,akhir_pembayaran.class);
                startActivity(go);
                gettransaksi data=new gettransaksi();
              //  data.ambil("https://idtronik.com/kambing/ajax/pembayaran");
            }
        });


       /* int sum=sp.getInt("jumlah",0);
        String temp=""+ NumberFormat.getNumberInstance(Locale.US).format(sum);
        temp=temp.replaceAll(",",".");*/
        String temp1=""+ NumberFormat.getNumberInstance(Locale.US).format(temp);
        temp1=temp1.replaceAll(",",".");
        jumlah.setText("Rp."+temp1);


        String temp2=""+ NumberFormat.getNumberInstance(Locale.US).format(semua);
        temp2=temp2.replaceAll(",",".");
        semuaa.setText("Rp."+temp2);

        uniktxt.setText("Rp."+unik);


    }


    class gettransaksi  extends AsyncTask<String,Void,String>
    {
        int jumlah=sp.getInt("notif",0);

/*
        public void ambil(String url)
        {
            String id_user=sp.getString("id"," ");
            pd=new ProgressDialog(KonfirmasiPembayaran.this);
            pd.setTitle("Loading");
            pd.setMessage("Loading....Please wait");
            pd.show();
            url = "https://idtronik.com/kambing/ajax/pembayaran_details";


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
                            try {
                                id_transaksi = response.getString("id_transaksi");
                                transaksi=KonfirmasiPembayaran.this.getSharedPreferences("transaksi", KonfirmasiPembayaran.this.MODE_PRIVATE);
                                SharedPreferences.Editor e=transaksi.edit();

                                e.putString("id",id_transaksi);
                                e.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("idtrans",id_transaksi);



                                for (int i=1;i<=jumlah;i++)
                                {
                                    sdata = KonfirmasiPembayaran.this.getSharedPreferences("data"+i, KonfirmasiPembayaran.this.MODE_PRIVATE);




                                    int id_ternak=sdata.getInt("id",0);
                                    int harga=sdata.getInt("harga",0);

                                    //  Bundle a = new Bundle()
/*
                                    Log.d("id_ternak",""+id_ternak);
                                    Log.d("harga",""+harga);
                                    Log.d("id_transaksi",""+id_transaksi);

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

                                                    Log.d("hasil",response.toString());


                                                    pd.dismiss();
                                                }

                                                @Override
                                                public void onError(ANError anError) {

                                                    pd.dismiss();

                                                    Log.d("nomor","1");
                                                    Toast.makeText(KonfirmasiPembayaran.this,anError.getMessage(),Toast.LENGTH_LONG).show();
                                                }
                                            });


                                }








                            pd.dismiss();
                        }

                        @Override
                        public void onError(ANError anError) {
                           Log.d("dalam",anError.getMessage());
                            pd.dismiss();
                            Toast.makeText(KonfirmasiPembayaran.this,anError.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });






        }
 */
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
