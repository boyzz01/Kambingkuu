package app.kambingku;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.widget.ANImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class login_drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private SharedPreferences sp;

    MySQLClient data;
    RecyclerView recyclerView;
    ImageView cart;

    public static TextView notif;


    @Override
    protected void onResume() {


        if (!sp.contains("notif") )
        {
            notif.setVisibility(View.INVISIBLE);
        }
        else
        {

            if (sp.getInt("notif",0)==0)
            {
                notif.setVisibility(View.INVISIBLE);
            }
            else {
                notif.setVisibility(View.VISIBLE);
             //   Log.d("masuk",""+sp.getInt("notif",0));
                notif.setText("" + sp.getInt("notif", 0));
            }
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_drawer);
        sp = getSharedPreferences("login",MODE_PRIVATE);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        notif=findViewById(R.id.notif);
        cart=findViewById(R.id.cart);



        if (!sp.contains("notif") )
        {
            notif.setVisibility(View.INVISIBLE);
        }
        else
        {
            if (sp.getInt("notif",0)==0)
            {
                notif.setVisibility(View.INVISIBLE);
            }
            else if (sp.getInt("notif",0)>=1){
                notif.setVisibility(View.VISIBLE);
                notif.setText("" + sp.getInt("notif", 0));
            }
        }




        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keranjang= new Intent(login_drawer.this,keranjang_belanja.class);
                startActivity(keranjang);


            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView icon_drawer = (ImageView) findViewById(R.id.drawer);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        icon_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        data= new MySQLClient(this);
        data.retrieve("http://idtronik.com/kambing/ajax/list_products");

      //  CardAdapter = new

        View headerview = navigationView.getHeaderView(0);
        TextView nama = headerview.findViewById(R.id.namalengkap);
        TextView emaill= headerview.findViewById(R.id.email);
        TextView hp = headerview.findViewById(R.id.nohp);
        ANImageView fotopp=headerview.findViewById(R.id.fotopp);
        String imageurl="https://idtronik.com/kambing/assets/images/user/"+sp.getString("fotopp"," ");

        fotopp.setDefaultImageResId(R.drawable.icon_dont_login);
        fotopp.setErrorImageResId(R.drawable.icon_dont_login);
        fotopp.setImageUrl(imageurl);

        nama.setText(sp.getString("namalengkap"," "));
        emaill.setText(sp.getString("email"," "));
        hp.setText(sp.getString("telepon"," "));


       // Button login = (Button) headerview.findViewById(R.id.nav_login);


    }






    //Overriden method to detect scrolling

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id==R.id.bantuan)
        {
            Intent bant = new Intent(login_drawer.this,welcome.class);
            startActivity(bant);
        }

        if (id==R.id.perkembangan_produk)
        {
            Intent kembang = new Intent(login_drawer.this,history.class);
            startActivity(kembang);
        }

        if (id==R.id.keranjang)
        {
            Intent keranjang = new Intent(login_drawer.this,keranjang_belanja.class);
            startActivity(keranjang);
        }

        if (id == R.id.logout) {

            SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor e=sp.edit();
            if (sp.contains("notif")) {
                for (int i=1;i<=sp.getInt("notif",1);i++) {
                    SharedPreferences data = getSharedPreferences("data"+i, MODE_PRIVATE);
                    SharedPreferences.Editor a=data.edit();
                    a.clear();
                    a.commit();
                }
            }



            e.clear();
            e.commit();
            Intent mainIntent = new Intent(login_drawer.this, login.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            splash.login=false;
            login_drawer.this.startActivity(mainIntent);

           // Toast.makeText(login.this,"Login Berhasil",Toast.LENGTH_SHORT).show();

            login_drawer.this.finish();
            // Handle the camera action
        }/* else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
