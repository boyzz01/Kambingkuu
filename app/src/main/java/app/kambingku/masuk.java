package app.kambingku;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Win 10 on 12/23/2017.
 */

public class masuk extends AsyncTask<String,Void,String > {
    Context context;
    AlertDialog alertDialog;
    ProgressDialog pd;
    SharedPreferences sp;
    public String username, password;

    public masuk(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            username = params[0];
            password = params[1];
            String login_url = "https://www.idtronik.com/kambing/ajax/login";
            URL url = new URL(login_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream ostream = con.getOutputStream();
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(ostream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                    + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            bf.write(post_data);
            bf.flush();
            bf.close();
            ostream.close();
            InputStream inputStream = con.getInputStream();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bfr.readLine()) != null) {
                result += line;
            }

            bfr.close();
            inputStream.close();
            con.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setTitle("Loading");
        pd.setMessage("Loading....Please wait");
        pd.show();
        sp = context.getSharedPreferences("login", context.MODE_PRIVATE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            Toast.makeText(context, "Koneksi Gagal, Periksa jaringan koneksi andaaa", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        } else {


            String s = result.trim();
            pd.dismiss();


           //  EditText email= (EditText) ((Activity)context).findViewById(R.id.email);
          //   email.setText(hasil);

//            login.email.setText(s);

            // s.replaceAll("","");

           Log.d("T",s);
            if (s.contains("\"result\":\"1\"") && s.contains("\"msg\":\"Login\"")) {
                Intent intent = new Intent(context,login_drawer.class);
                Toast.makeText(context, "Login Sukses", Toast.LENGTH_SHORT).show();
                String namalengkap=between(s,"nama_lengkap\":\"","\",\"email");
                String email=between(s,"email\":\"","\",\"telepon");
                String telepon=between(s,"telepon\":",",\"created_at\"");
                String id=between(s,"\"id_user\":\"","\",\"username\"");
                String foto=between(s,"\"profile\":\"","\"}}");

                if (telepon.equals("null"))
                {
                    telepon=" ";
                }
                SharedPreferences.Editor e = sp.edit();
                e.putString("username", username);
                e.putString("password", password);
                e.putString("email",email);
                e.putString("telepon",telepon);
                e.putString("namalengkap",namalengkap);
                e.putString("id",id);
                e.putString("fotopp",foto);
                e.commit();
                context.startActivity(intent);
                ((Activity) context).finish();
            } else if (s.contains("\"result\":\"0\"") && s.contains("\"msg\":\"Username or password wrong\"")) {
                Toast.makeText(context, "Invalid User Name or Password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Gagal Login, Periksa Koneksi Anda", Toast.LENGTH_SHORT).show();
            }

        }
        super.onPostExecute(result);
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
}


