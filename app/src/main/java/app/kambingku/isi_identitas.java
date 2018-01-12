package app.kambingku;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class isi_identitas extends AppCompatActivity {

    Button lanjut;
    EditText namaedit,email,nik,kewarganegaraan,tempatlahir,pekerjaan,agama,alamat;
    TextView tanggallahir;
    RadioGroup jk;
    String sjk="";
    SharedPreferences sp;
    String snama,semail,snikm,stempat,stanggal,spekerjaan,sagama,salamat;
    ImageView bukakal;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_identitas);

        namaedit=findViewById(R.id.nama);

        nik=findViewById(R.id.nik);

        tempatlahir=findViewById(R.id.tempatlahir);
        tanggallahir=findViewById(R.id.tanggallahir);
        pekerjaan=findViewById(R.id.pekerjaan);
        agama=findViewById(R.id.agama);
        alamat=findViewById(R.id.alamat);
        jk=findViewById(R.id.jeniskelamain);
        lanjut=findViewById(R.id.lanjut);
        sp = isi_identitas.this.getSharedPreferences("daftar",isi_identitas.this.MODE_PRIVATE);

        bukakal=findViewById(R.id.bukakal);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        bukakal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate(year, month + 1, day);
                setDate(view);
            }
        });

        tanggallahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        jk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.wanita)
                {
                    sjk="wanita";
                }
                if (i==R.id.pria)
                {
                    sjk="pria";
                }
            }
        });
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snama=namaedit.getText().toString();
                snikm=nik.getText().toString();

                stempat=tempatlahir.getText().toString();
                stanggal=tanggallahir.getText().toString();
                spekerjaan=pekerjaan.getText().toString();
                sagama=agama.getText().toString();
                salamat=alamat.getText().toString();
                if(snama.equals("")  || snikm.equals("")  || stempat.equals("") || stanggal.equals("") || spekerjaan.equals("") || sagama.equals("") ||salamat.equals("") || sjk.equals(""))
                {
                    Toast.makeText(isi_identitas.this,"Tidak Boleh ada data yang kosong",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    SharedPreferences.Editor e = sp.edit();
                    e.putString("nama",snama);

                    e.putString("nik",snikm);
                    e.putString("tempatl",stempat);
                    e.putString("tanggal",stanggal);
                    e.putString("pekerjaan",spekerjaan);
                    e.putString("agama",sagama);
                    e.putString("alamat",salamat);
                    e.putString("jk",sjk);

                    e.commit();
                    Intent go = new Intent(isi_identitas.this,upload_dokumen.class);
                    startActivity(go);
                }
            }
        });




    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        //akan menampilkan teks ketika kalendar muncul setelah menekan tombol
        Toast.makeText(getApplicationContext(), "Pilih Tangal", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {

        tanggallahir.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
}
