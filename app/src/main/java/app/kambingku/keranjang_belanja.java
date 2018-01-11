package app.kambingku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class keranjang_belanja extends AppCompatActivity {
    SharedPreferences sp;
    MySQLClient data;
    RecyclerView recyclerView;
    TextView total;

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
                        Intent mainIntent = new Intent(keranjang_belanja.this, KonfirmasiPembayaran.class);
                        mainIntent.putExtra("total",total.getText().toString());


                       keranjang_belanja.this.startActivity(mainIntent);
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
}
