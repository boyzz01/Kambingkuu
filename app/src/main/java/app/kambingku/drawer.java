package app.kambingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;

public class drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;

    private SharedPreferences sp;
    MySQLClient data;
    ImageView cart;
    SwipeRefreshLayout swipeRefreshLayout;

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
                notif.setText("" + sp.getInt("notif", 0));
            }
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ImageView icon_drawer = (ImageView) findViewById(R.id.drawer);
        //setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);


        View headerview = navigationView.getHeaderView(0);

        Button login = (Button) headerview.findViewById(R.id.nav_login);

        sp = getSharedPreferences("login",MODE_PRIVATE);
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
            else {
                notif.setVisibility(View.VISIBLE);
                notif.setText("" + sp.getInt("notif", 0));
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(drawer.this, login.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                drawer.this.startActivity(mainIntent);

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keranjang= new Intent(drawer.this,keranjang_belanja.class);
                startActivity(keranjang);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data= new MySQLClient(drawer.this);
                data.retrieve("http://idtronik.com/kambing/ajax/list_products");
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        data= new MySQLClient(this);
        data.retrieve("http://idtronik.com/kambing/ajax/list_products");




        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        icon_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


    }

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
      //  getMenuInflater().inflate(R.menu.drawer, menu);
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


        if (id==R.id.bantuan){
            Intent bant = new Intent(drawer.this,welcome.class);
            startActivity(bant);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
