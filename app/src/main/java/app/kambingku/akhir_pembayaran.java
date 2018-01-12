package app.kambingku;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;


import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import okhttp3.internal.http2.Settings;

public class akhir_pembayaran extends AppCompatActivity {

    EditText namabank,atasnama,jumlah;
    TextView upload;
    RadioButton pribadi,pinjaman;
    RadioGroup radioGroup;
    int sumber=0;
    Button lanjut;
    Boolean centang=false;
    Boolean up=false;
    public File file;
    Uri imageUri=null;
    ImageView tes;
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    String url="https://idtronik.com/kambing/ajax/konfirmasi_pembayaran";
    Context c;
    ProgressDialog pd;
    SharedPreferences transaksi,sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akhir_pembayaran);

        namabank=findViewById(R.id.namabank);
        atasnama=findViewById(R.id.atasnama);
        jumlah=findViewById(R.id.jumlah);
        upload=findViewById(R.id.upload);
        pribadi=findViewById(R.id.pribadi);
        radioGroup=findViewById(R.id.radiosumber);
        pinjaman=findViewById(R.id.pinjaman);
        lanjut=findViewById(R.id.lanjut);
        c=this;



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.pribadi)
                {

                   sumber=1;
                }
                if (i==R.id.pinjaman)
                {
                    sumber=2;

                }
            }
        });




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStorageDirectory()
                        .getPath(), "Kambing Ummat");
                String uriSting = (file.getAbsolutePath() + "/"
                        + System.currentTimeMillis() + ".jpg");
                CharSequence options[] = new CharSequence[]{"Ambil Gambar", "Pilih Gambar dari Galeri"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(akhir_pembayaran.this);

                builder.setTitle("Select Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {

                        //Click Event for each item.


                        if(i == 0){
                            try {
                                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                Uri uri  = Uri.parse("file:///sdcard/photo.jpg");
                                photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);



                                startActivityForResult(photo,REQUEST_CAMERA);
                            }
                            catch (android.content.ActivityNotFoundException ex)
                            {
                                Toast.makeText(akhir_pembayaran.this,"Gagal Membuka Kamera",Toast.LENGTH_SHORT).show();
                            }


                        }

                        if(i == 1){

/*
                            Intent galleryIntent = new Intent();
                            galleryIntent.setType("image/*");
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),GALLERY_PICK);*/
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            try {
                                startActivityForResult(pickPhoto, SELECT_FILE);//
                            }
                            catch (android.content.ActivityNotFoundException ex)
                            {
                                Toast.makeText(akhir_pembayaran.this,"Gagal Membuka Galeri",Toast.LENGTH_SHORT).show();
                            }
                        }



                    }
                });

                builder.show();


            }
        });



        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (namabank.getText().toString().equals("") || atasnama.getText().toString().equals("") || jumlah.getText().toString().equals(""))
                    {
                        Toast.makeText(akhir_pembayaran.this,"Tidak Boleh Ada Data Yang Kosong",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        if (imageUri==null)
                        {
                            Toast.makeText(akhir_pembayaran.this,"Anda Belum Mengupload Bukti Pembayaran",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            konek data=new konek();
                            data.go();
                        }
                    }
            }
        });
    }

    class konek extends AsyncTask<String,Void,String>
    {





        public void go()
        {
            transaksi=akhir_pembayaran.this.getSharedPreferences("transaksi", akhir_pembayaran.this.MODE_PRIVATE);
            String id_transaksi=transaksi.getString("id"," ");

            Log.d("idyrans",id_transaksi);
            sp=akhir_pembayaran.this.getSharedPreferences("login", akhir_pembayaran.this.MODE_PRIVATE);
            String user=sp.getString("id"," ");

            pd=new ProgressDialog(c);
            pd.setTitle("Loading");
            pd.setMessage("Loading....Please wait");
            pd.show();
            pd.setCancelable(false);
            AndroidNetworking.upload(url)
                    .addMultipartFile("bukti",file)
                    .addMultipartParameter("id_transaksi",""+id_transaksi)
                    .addMultipartParameter("jumlah_transfer",jumlah.getText().toString())
                    .addMultipartParameter("option_sumberdana0",""+sumber)
                    .addMultipartParameter("nama_bank",namabank.getText().toString())
                    .addMultipartParameter("atas_nama",atasnama.getText().toString())
                    .addMultipartParameter("id_user",user)
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            Log.d("uploaded",bytesUploaded+" total :" +totalBytes);
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("berhasil",response.toString());
                            Toast.makeText(akhir_pembayaran.this,"Upload Berhasil, Mohon tunggu verifikasi kami",Toast.LENGTH_LONG).show();
                            transaksi=akhir_pembayaran.this.getSharedPreferences("transaksi", akhir_pembayaran.this.MODE_PRIVATE);
                            SharedPreferences.Editor editor=transaksi.edit();
                            editor.putInt("kode_unik",0);
                            editor.putInt("total",0);
                            editor.commit();
                            Intent kontrak= new Intent(akhir_pembayaran.this,suratkontrak.class);
                            startActivity(kontrak);
                            pd.dismiss();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(akhir_pembayaran.this,anError.toString()+"  "+anError.getMessage(),Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
        }






        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode)
        {
            case 1: {
                if (resultCode == RESULT_OK) {
                   file = new File(Environment.getExternalStorageDirectory().getPath(), "photo.jpg");

                   imageUri = Uri.fromFile(file);

                    Log.d("imageuri",imageUri.toString());

                    String displayname;


                    //  Bitmap bmp = BitmapFactory.decodeFile();


                    if (imageUri != null) {
                        String uristring=imageUri.toString();
                        File myFile = new File(uristring);
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = akhir_pembayaran.this.getContentResolver().query(imageUri,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    upload.setText(displayname);

                                }
                            }
                            finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            upload.setText(displayname);

                        }

                    } else {
                        Toast.makeText(akhir_pembayaran.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
            case 2: {
                if (resultCode == RESULT_OK) {

                    imageUri = data.getData();
                    //push(imageUri);
                    String displayname;
                    Log.d("imageuri",imageUri.toString());



                    //  Bitmap bmp = BitmapFactory.decodeFile();



                    if (imageUri != null) {
                        Uri temp;
                        String uristring=imageUri.toString();
                        File myFile = new File(uristring);
                        Log.d("uristring",uristring);
                        file=myFile;




                        Log.d("file",file.toString());
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = akhir_pembayaran.this.getContentResolver().query(imageUri,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    String filepath=cursor.getString(0);
                                    Log.d("tess",filepath);
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    upload.setText(displayname+" content");

                                }

                            } finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            upload.setText(displayname+" file");

                        }

                    } else {
                        Toast.makeText(akhir_pembayaran.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
