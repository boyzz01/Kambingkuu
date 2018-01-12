package app.kambingku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class detail_deskripsi extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    MySQLClient ambil;
    SliderLayout  mDemoSlider;
    TextView nama;
    TextView harga;
    TextView umur;
    TextView id;
    TextView hargapemeliharaan;
    ConstraintLayout add;
    private SharedPreferences sp,data;
    ImageView beli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deskripsi);
        sp = this.getSharedPreferences("login", this.MODE_PRIVATE);
        nama=findViewById(R.id.namap);
        harga=findViewById(R.id.hargapr);
        umur=findViewById(R.id.umur);
        id=findViewById(R.id.txt_id_product);
        hargapemeliharaan=findViewById(R.id.hargapemeliharaan);
        beli=findViewById(R.id.beli);


        add=findViewById(R.id.add);

        Intent intent = getIntent();
        final int sid = intent.getExtras().getInt("id");
        final String snama=intent.getExtras().getString("nama");
        final int sharga=intent.getExtras().getInt("harga");
        final String foto1=intent.getExtras().getString("foto1");
        String fotoo=intent.getExtras().getString("foto2");
        String sumur=intent.getExtras().getString("umur")+" Tahun";
        final String status=intent.getExtras().getString("status");
        final String txtharga=intent.getExtras().getString("sharga");


        Log.d("foto2",fotoo);
        Log.d("foto1",foto1);

        nama.setText(snama+" Terbaik Dikelasnya");
        id.setText("#"+sid);
        harga.setText(txtharga);
        hargapemeliharaan.setText(txtharga);
        umur.setText(sumur);

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("1")) {
                    Toast.makeText(detail_deskripsi.this, "Produk Ini Sedang Tidak Tersedia", Toast.LENGTH_SHORT).show();
                } else {


                    //

                    sp =detail_deskripsi.this.getSharedPreferences("login", detail_deskripsi.this.MODE_PRIVATE);



                    //  TextView txtView = (TextView) ((Activity) view.getContext()).findViewById(R.id.notif);
                    // txtView.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor e = sp.edit();
                    int temp;
                    if (sp.contains("notif") ) {

                        Boolean masuk=false;
                        if (sp.getInt("notif",0)<=4)
                        {

                            for (int i=1;i<=sp.getInt("notif",0);i++)
                            {
                                data = detail_deskripsi.this.getSharedPreferences("data" + i, detail_deskripsi.this.MODE_PRIVATE);
                                SharedPreferences.Editor a = data.edit();
                                if (data.getInt("id",0)==sid)
                                {
                                    masuk=true;
                                    Toast.makeText(detail_deskripsi.this, "Produk Ini Sudah ada di keranjang anda", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                }
                            }

                            if (!masuk) {
                                int test = sid;
                                temp = sp.getInt("notif", 0) + 1;
                                e.putInt("notif", temp);
                                e.commit();
                                data = detail_deskripsi.this.getSharedPreferences("data" + temp, detail_deskripsi.this.MODE_PRIVATE);
                                SharedPreferences.Editor a = data.edit();
                                a.putInt("id", sid);
                                a.putString("gender", snama);
                                a.putString("foto", foto1);
                                a.putInt("harga", sharga);

                                a.commit();
                                if(sp.contains("username") && sp.contains("password")){
                                    Intent mainIntent = new Intent(detail_deskripsi.this, keranjang_belanja.class);

                                    splash.login=true;
                                    detail_deskripsi.this.startActivity(mainIntent);
                                }
                                else
                                {
                                    Intent mainIntent = new Intent(detail_deskripsi.this, daftar.class);

                                    splash.login=true;
                                    detail_deskripsi.this.startActivity(mainIntent);
                                }


                                //   txtView.setText("" + temp);
                            }
                        }
                        else
                        {
                            Toast.makeText(detail_deskripsi.this,"Maksimal Pembelian adalah 5 Produk",Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        e.putInt("notif", 1);
                        e.putInt("data" + 1, sid);
                        e.commit();
                        data = detail_deskripsi.this.getSharedPreferences("data" + 1, detail_deskripsi.this.MODE_PRIVATE);
                        SharedPreferences.Editor a = data.edit();
                        a.putInt("id", sid);
                        a.putString("gender", snama);
                        a.putString("foto", foto1);
                        a.putInt("harga", sharga);
                        a.commit();
                        //txtView.setText("" + 1);
                        if(sp.contains("username") && sp.contains("password")){
                            Intent mainIntent = new Intent(detail_deskripsi.this, keranjang_belanja.class);

                            splash.login=true;
                            detail_deskripsi.this.startActivity(mainIntent);
                        }
                        else
                        {
                            Intent mainIntent = new Intent(detail_deskripsi.this, daftar.class);

                            splash.login=true;
                            detail_deskripsi.this.startActivity(mainIntent);
                        }
                    }
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("1")) {
                    Toast.makeText(detail_deskripsi.this, "Produk Ini Sedang Tidak Tersedia", Toast.LENGTH_SHORT).show();
                } else {


                    //

                    sp =detail_deskripsi.this.getSharedPreferences("login", detail_deskripsi.this.MODE_PRIVATE);



                  //  TextView txtView = (TextView) ((Activity) view.getContext()).findViewById(R.id.notif);
                   // txtView.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor e = sp.edit();
                    int temp;
                    if (sp.contains("notif") ) {

                        Boolean masuk=false;
                        if (sp.getInt("notif",0)<=4)
                        {

                            for (int i=1;i<=sp.getInt("notif",0);i++)
                            {
                                data = detail_deskripsi.this.getSharedPreferences("data" + i, detail_deskripsi.this.MODE_PRIVATE);
                                SharedPreferences.Editor a = data.edit();
                                if (data.getInt("id",0)==sid)
                                {
                                    masuk=true;
                                    Toast.makeText(detail_deskripsi.this, "Produk Ini Sudah ada di keranjang anda", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                }
                            }

                            if (!masuk) {
                                int test = sid;
                                temp = sp.getInt("notif", 0) + 1;
                                e.putInt("notif", temp);
                                e.commit();
                                data = detail_deskripsi.this.getSharedPreferences("data" + temp, detail_deskripsi.this.MODE_PRIVATE);
                                SharedPreferences.Editor a = data.edit();
                                a.putInt("id", sid);
                                a.putString("gender", snama);
                                a.putString("foto", foto1);
                                a.putInt("harga", sharga);

                                a.commit();
                                Toast.makeText(detail_deskripsi.this,"Produk berhasil ditambahkan di cart",Toast.LENGTH_SHORT).show();


                             //   txtView.setText("" + temp);
                            }
                        }
                        else
                        {
                            Toast.makeText(detail_deskripsi.this,"Maksimal Pembelian adalah 5 Produk",Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        e.putInt("notif", 1);
                        e.putInt("data" + 1, sid);
                        e.commit();
                        data = detail_deskripsi.this.getSharedPreferences("data" + 1, detail_deskripsi.this.MODE_PRIVATE);
                        SharedPreferences.Editor a = data.edit();
                        a.putInt("id", sid);
                        a.putString("gender", snama);
                        a.putString("foto", foto1);
                        a.putInt("harga", sid);
                        a.commit();
                        //txtView.setText("" + 1);
                        Toast.makeText(detail_deskripsi.this,"Produk berhasil ditambahkan di cart",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mDemoSlider=(SliderLayout) findViewById(R.id.gambarslide);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put(" ","https://idtronik.com/kambing/assets/images/kambing/"+foto1);
        url_maps.put("  ", "https://idtronik.com/kambing/assets/images/kambing/"+fotoo);

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

       // mDemoSlider.setDuration(4000);
      //  mDemoSlider.addOnPageChangeListener(detail_deskripsi.this)

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

    }
}
