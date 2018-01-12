package app.kambingku;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Win 10 on 1/4/2018.
 */

public class MySQLClient extends AsyncTask<String,Void,String> {

    private final Context c;
    public RecyclerView.Adapter adapter,adapterkeranjang,adapterselesai,adapterproses;
    private List<product> products;
    private List<keranjang> keranjangs;
    private List<perkembangankambing> perkembangankambingList;
    private List<proseskambing> proseskambings;
    public static  List<proseskambing> tempat;


    ProgressDialog pd;
    SharedPreferences sp;
    RecyclerView recyclerView;
    TextView total;
    public MySQLClient(Context c) {
        this.c = c;
    }

    public int jumlah=0;

    public void retrieve(String url)
    {
        pd=new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Loading....Please wait");
        pd.show();
       products= new ArrayList<>();

        recyclerView = (RecyclerView) ((Activity)c).findViewById(R.id.recyclerView);

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                      // String data=between(response.toString(),"\"data\":","}]}")+"}]";
                        try {
                            JSONArray test=response.getJSONArray("data");
                            JSONObject jo;
                            product p;

                            for (int i=0;i<test.length();i++)
                            {
                                jo = test.getJSONObject(i);

                                int id=jo.getInt("id_ternak");
                                int harga=jo.getInt("harga");
                                String nama=jo.getString("gender");
                                String foto=jo.getString("foto");
                                String date = jo.getString("dob");
                                int staus=jo.getInt("status");

                                if (staus==2)
                                {

                                }
                                else
                                {
                                    p=new product();
                                    p.setId_ternak(id);
                                    p.setFoto(foto);
                                    p.setGender(nama);
                                    p.setHarga(harga);
                                    p.setDob(date);
                                    p.setStatus(staus);
                                    products.add(p);
                                }


                              //  Log.d("test",""+id);



                            }

                            adapter=new MessageAdapter(products);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(c,e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                       // TextView email= (TextView) ((Activity)c).findViewById(R.id.textView3);
                     //   email.setText(data);
                        pd.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Toast.makeText(c,anError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });





    }

    public void detail(String url)
    {
        pd=new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Loading....Please wait");
        pd.show();
        products= new ArrayList<>();

        recyclerView = (RecyclerView) ((Activity)c).findViewById(R.id.recyclerView);

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // String data=between(response.toString(),"\"data\":","}]}")+"}]";
                        try {
                            JSONArray test=response.getJSONArray("data");
                            JSONObject jo;
                            product p;

                            for (int i=0;i<test.length();i++)
                            {
                                jo = test.getJSONObject(i);

                                int id=jo.getInt("id_ternak");
                                int harga=jo.getInt("harga");
                                String nama=jo.getString("gender");
                                String foto=jo.getString("foto");




                                p=new product();
                                p.setId_ternak(id);
                                p.setFoto(foto);
                                p.setGender(nama);
                                p.setHarga(harga);


                                products.add(p);
                                //  Log.d("test",""+id);



                            }

                            adapter=new MessageAdapter(products);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(c,e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        // TextView email= (TextView) ((Activity)c).findViewById(R.id.textView3);
                        //   email.setText(data);
                        pd.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Toast.makeText(c,anError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void keranjang(String url,int notif)
    {
        sp = c.getSharedPreferences("login", c.MODE_PRIVATE);






        int jum=0;
        keranjangs= new ArrayList<>();

        recyclerView = (RecyclerView) ((Activity)c).findViewById(R.id.recyclerView);
       total = (TextView) ((Activity)c).findViewById(R.id.jumlah);

       keranjang p;

        for (int i=1;i<=notif;i++) {

            sp = c.getSharedPreferences("data"+i, c.MODE_PRIVATE);

            int id=sp.getInt("id",0);
            int harga=sp.getInt("harga",0);
            String nama=sp.getString("gender"," ");
            String foto=sp.getString("foto"," ");

            p=new keranjang();
            p.setId_ternak(id);
            p.setFoto(foto);
            p.setGender(nama);
            p.setHarga(harga);

            jum=jum+harga;
            keranjangs.add(p);




        }
        sp = c.getSharedPreferences("login", c.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt("jumlah",jum);
        e.commit();
        Log.d("jum",""+jum);

        String temp=""+ NumberFormat.getNumberInstance(Locale.US).format(jum);
        temp=temp.replaceAll(",",".");
        total.setText("Rp."+temp);
       adapterkeranjang=new keranjangadapter(keranjangs);


        recyclerView.setAdapter(adapterkeranjang);

    }

    public RecyclerView.Adapter selesai(String url)
    {
        pd=new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Loading....Please wait");
        pd.show();
        perkembangankambingList = new ArrayList<>();

        recyclerView = (RecyclerView) ((Activity)c).findViewById(R.id.recyclerView);

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // String data=between(response.toString(),"\"data\":","}]}")+"}]";
                        try {
                            JSONArray test=response.getJSONArray("data");
                            JSONObject jo;
                            perkembangankambing p;

                            for (int i=0;i<test.length();i++)
                            {
                                jo = test.getJSONObject(i);

                                String sisa=jo.getString("sisa");
                                String tanda=jo.getString("tanda");
                                int id=jo.getInt("id_ternak");
                                int harga=jo.getInt("harga");
                                String nama=jo.getString("gender");
                                String foto=jo.getString("foto");
                                String date = jo.getString("dob");
                                int staus=jo.getInt("status");
                                int berat=jo.getInt("berat");

                                if (tanda.equals("+")) {
                                    p = new perkembangankambing();
                                    p.setId(id);
                                    p.setFoto(foto);
                                    p.setGender(nama);
                                    p.setHarga(harga);
                                    p.setDob(date);
                                    p.setBerat(berat);
                                    p.setSisa(sisa);
                                    p.setTanda(tanda);
                                    perkembangankambingList.add(p);

                                }


                                //  Log.d("test",""+id);



                            }

                            adapterselesai=new perkembanganadapter(perkembangankambingList);

                            //recyclerView.setAdapter(adapterselesai);

                        } catch (JSONException e) {
                            Toast.makeText(c,e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        // TextView email= (TextView) ((Activity)c).findViewById(R.id.textView3);
                        //   email.setText(data);
                        pd.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Toast.makeText(c,anError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        return adapterselesai;

    }

    public void proses(String url)
    {
        pd=new ProgressDialog(c);
        pd.setTitle("Loading");
        pd.setMessage("Loading....Please wait");
        pd.show();
        proseskambings = new ArrayList<>();
        tempat = new ArrayList<>();

        recyclerView = (RecyclerView) ((Activity)c).findViewById(R.id.rview);

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // String data=between(response.toString(),"\"data\":","}]}")+"}]";
                        Log.d("data",response.toString());
                        try {
                            JSONArray test=response.getJSONArray("data");
                            JSONObject jo;
                            proseskambing p;

                            Log.d("data 2",test.toString());
                            for (int i=0;i<test.length();i++)
                            {
                                jo = test.getJSONObject(i);

                                String sisa=jo.getString("sisa");
                                String tanda=jo.getString("tanda");
                                int id=jo.getInt("id_ternak");
                                int harga=jo.getInt("harga");
                                String nama=jo.getString("gender");
                                String foto=jo.getString("foto");
                                String date = jo.getString("dob");
                                int staus=jo.getInt("status");
                                int berat=jo.getInt("berat");

                                if (tanda.equals("-")) {
                                    p = new proseskambing();
                                    p.setId(id);
                                    p.setFoto(foto);
                                    p.setGender(nama);
                                    p.setHarga(harga);
                                    p.setDob(date);
                                    p.setBerat(berat);
                                    p.setSisa(sisa);
                                    p.setTanda(tanda);
                                    proseskambings.add(p);
                                    Log.d("dalam",""+id);

                                }







                            }

                           //tempat=proseskambings;
                            adapterproses=new prosesadapter(proseskambings);
                            Log.d("jumlah",""+adapterproses.getItemCount());
//                            recyclerView.setAdapter(adapterproses);

                        } catch (JSONException e) {
                            Toast.makeText(c,e.getMessage()+"1",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        // TextView email= (TextView) ((Activity)c).findViewById(R.id.textView3);
                        //   email.setText(data);
                        pd.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        pd.dismiss();
                        Toast.makeText(c,anError.getMessage()+"2",Toast.LENGTH_LONG).show();
                    }
                });




    }

    public static String between(String value, String a, String b) {
        // Return a substring between the two strings.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        int posB = value.lastIndexOf(b);
        if (posB == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= posB) {
            return "";
        }
        return value.substring(adjustedPosA, posB);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
