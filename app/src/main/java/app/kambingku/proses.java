package app.kambingku;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class proses extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private List<proseskambing> proseskambings;
    MySQLClient data;
    SharedPreferences sp;
    RecyclerView rv;
    RecyclerView.Adapter adapter;
    ProgressDialog pd;
    TextView belum;
    Context c;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







    }

    @Override
    public void onStart() {


        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_proses, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rview);
        belum=view.findViewById(R.id.belum);
        rv.setHasFixedSize(true);
        c=this.getActivity();
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        sp = this.getContext().getSharedPreferences("login", this.getContext().MODE_PRIVATE);
        String id=sp.getString("id"," ");

       // data= new MySQLClient(this.getActivity());
        String url="http://idtronik.com/kambing/Ajax/list_history?iduser="+id;
        test data=new test();
        data.proses(url);
        return  view;

    }

    class test extends AsyncTask<String,Void,String>
    {

        public void proses(String url)
        {
            pd=new ProgressDialog(c);
            pd.setTitle("Loading");
            pd.setMessage("Loading....Please wait");
            pd.show();
            proseskambings = new ArrayList<>();


            AndroidNetworking.get(url)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // String data=between(response.toString(),"\"data\":","}]}")+"}]";
                            String result= null;
                            try {
                                result = response.getString("result");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (result.equals("1")) {
                                try {

                                    belum.setVisibility(View.GONE);
                                    JSONArray test = response.getJSONArray("data");
                                    JSONObject jo;
                                    proseskambing p;


                                    for (int i = 0; i < test.length(); i++) {
                                        jo = test.getJSONObject(i);

                                        String sisa = jo.getString("sisa");
                                        String tanda = jo.getString("tanda");
                                        int id = jo.getInt("id_ternak");
                                        int harga = jo.getInt("harga");
                                        String nama = jo.getString("gender");
                                        String foto = jo.getString("foto");
                                        String date = jo.getString("dob");
                                        int staus = jo.getInt("status");
                                        int berat = jo.getInt("berat");

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


                                        }


                                    }


                                    adapter = new prosesadapter(proseskambings);
                                    if (adapter.getItemCount()==0)
                                    {
                                        belum.setVisibility(View.VISIBLE);
                                    }
                                    rv.setAdapter(adapter);

//

                                } catch (JSONException e) {
                                    Toast.makeText(c, e.getMessage() + "1", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }


                                // TextView email= (TextView) ((Activity)c).findViewById(R.id.textView3);
                                //   email.setText(data);
                                pd.dismiss();
                            }
                            else
                            {

                                pd.dismiss();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            pd.dismiss();
                            Toast.makeText(c,anError.getMessage()+"2",Toast.LENGTH_LONG).show();
                        }
                    });




        }
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }



}
