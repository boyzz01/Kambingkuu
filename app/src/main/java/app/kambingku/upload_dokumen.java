package app.kambingku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class upload_dokumen extends AppCompatActivity {

    TextView tktp,tkk,tpp;
    File ktp,kk,pp=null;
    Uri uktp,ukk,upp=null;
    Button lanjut;

    private static final int REQUEST_CAMERA_ktp = 1;
    private static final int SELECT_FILE_ktp = 2;
    private static final int REQUEST_CAMERA_kk = 3;
    private static final int SELECT_FILE_kk = 4;
    private static final int REQUEST_CAMERA_pp = 5;
    private static final int SELECT_FILE_pp = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_dokumen);

        tktp=findViewById(R.id.ktp);
        tkk=findViewById(R.id.kk);
        tpp=findViewById(R.id.profil);

        lanjut=findViewById(R.id.daftar);


        tktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bukaktp();
            }
        });


        tkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bukakk();
            }
        });

        tpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bukapp();
            }
        });


        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ktp!=null || pp!=null)
                {
                    Intent go = new Intent(upload_dokumen.this,isi_bank.class);
                    go.putExtra("ktp",ktp);
                    go.putExtra("kk",kk);
                    go.putExtra("pp",pp);
                    startActivity(go);
                }

            }
        });




    }

    public void bukaktp()
    {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "Kambing Ummat");
        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpg");
        CharSequence options[] = new CharSequence[]{"Ambil Gambar", "Pilih Gambar dari Galeri"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(upload_dokumen.this);

        builder.setTitle("Select Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {

                //Click Event for each item.


                if(i == 0){
                    try {
                        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri  = Uri.parse("file:///sdcard/KTP.jpg");
                        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);



                        startActivityForResult(photo,REQUEST_CAMERA_ktp);
                    }
                    catch (android.content.ActivityNotFoundException ex)
                    {
                        Toast.makeText(upload_dokumen.this,"Gagal Membuka Kamera",Toast.LENGTH_SHORT).show();
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
                        startActivityForResult(pickPhoto, SELECT_FILE_ktp);//
                    }
                    catch (android.content.ActivityNotFoundException ex)
                    {
                        Toast.makeText(upload_dokumen.this,"Gagal Membuka Galeri",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        builder.show();


    }

    public void bukakk()
    {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "Kambing Ummat");
        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpg");
        CharSequence options[] = new CharSequence[]{"Ambil Gambar", "Pilih Gambar dari Galeri"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(upload_dokumen.this);

        builder.setTitle("Select Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {

                //Click Event for each item.


                if(i == 0){
                    try {
                        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri  = Uri.parse("file:///sdcard/KK.jpg");
                        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);



                        startActivityForResult(photo,REQUEST_CAMERA_kk);
                    }
                    catch (android.content.ActivityNotFoundException ex)
                    {
                        Toast.makeText(upload_dokumen.this,"Gagal Membuka Kamera",Toast.LENGTH_SHORT).show();
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
                        startActivityForResult(pickPhoto, SELECT_FILE_kk);//
                    }
                    catch (android.content.ActivityNotFoundException ex)
                    {
                        Toast.makeText(upload_dokumen.this,"Gagal Membuka Galeri",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        builder.show();


    }

    public void bukapp()
    {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "Kambing Ummat");
        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpg");
        CharSequence options[] = new CharSequence[]{"Ambil Gambar", "Pilih Gambar dari Galeri"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(upload_dokumen.this);

        builder.setTitle("Select Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {

                //Click Event for each item.


                if(i == 0){
                    try {
                        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri  = Uri.parse("file:///sdcard/FotoDiri.jpg");
                        photo.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);



                        startActivityForResult(photo,REQUEST_CAMERA_pp);
                    }
                    catch (android.content.ActivityNotFoundException ex)
                    {
                        Toast.makeText(upload_dokumen.this,"Gagal Membuka Kamera",Toast.LENGTH_SHORT).show();
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
                        startActivityForResult(pickPhoto, SELECT_FILE_pp);//
                    }
                    catch (android.content.ActivityNotFoundException ex)
                    {
                        Toast.makeText(upload_dokumen.this,"Gagal Membuka Galeri",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        builder.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 1: {
                if (resultCode == RESULT_OK) {
                    ktp = new File(Environment.getExternalStorageDirectory().getPath(), "KTP.jpg");

                    uktp = Uri.fromFile(ktp);

                    String displayname;


                    //  Bitmap bmp = BitmapFactory.decodeFile();


                    if (uktp != null) {
                        String uristring=uktp.toString();
                        File myFile = new File(uristring);
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = upload_dokumen.this.getContentResolver().query(uktp,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    tktp.setText(displayname);

                                }
                            }
                            finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            tktp.setText(displayname);

                        }

                    } else {
                        Toast.makeText(upload_dokumen.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
            case 2: {
                if (resultCode == RESULT_OK) {

                    uktp = data.getData();
                    //push(imageUri);
                    String displayname;


                    //  Bitmap bmp = BitmapFactory.decodeFile();


                    if (uktp != null) {
                        String uristring=uktp.toString();
                        File myFile = new File(uristring);
                        ktp=myFile;
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = upload_dokumen.this.getContentResolver().query(uktp,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    tktp.setText(displayname);

                                }
                            }
                            finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            tktp.setText(displayname);

                        }

                    } else {
                        Toast.makeText(upload_dokumen.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            break;
            case  3:
            {
                if (resultCode == RESULT_OK) {
                    kk = new File(Environment.getExternalStorageDirectory().getPath(), "KK.jpg");

                    ukk = Uri.fromFile(kk);

                    String displayname;


                    //  Bitmap bmp = BitmapFactory.decodeFile();


                    if (ukk != null) {
                        String uristring=ukk.toString();
                        File myFile = new File(uristring);
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = upload_dokumen.this.getContentResolver().query(ukk,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    tkk.setText(displayname);

                                }
                            }
                            finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            tkk.setText(displayname);

                        }

                    } else {
                        Toast.makeText(upload_dokumen.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
            case 4:
            {
                if (resultCode == RESULT_OK) {

                    ukk = data.getData();
                    //push(imageUri);
                    String displayname;


                    //  Bitmap bmp = BitmapFactory.decodeFile();


                    if (ukk != null) {
                        String uristring=ukk.toString();
                        File myFile = new File(uristring);
                        kk=myFile;
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = upload_dokumen.this.getContentResolver().query(ukk,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    tkk.setText(displayname);

                                }
                            }
                            finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            tkk.setText(displayname);

                        }

                    } else {
                        Toast.makeText(upload_dokumen.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case 5:
            {
                if (resultCode == RESULT_OK) {
                    pp = new File(Environment.getExternalStorageDirectory().getPath(), "FotoDiri.jpg");

                    upp = Uri.fromFile(pp);

                    String displayname;


                    //  Bitmap bmp = BitmapFactory.decodeFile();


                    if (upp != null) {
                        String uristring=upp.toString();
                        File myFile = new File(uristring);
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = upload_dokumen.this.getContentResolver().query(upp,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    tpp.setText(displayname);

                                }
                            }
                            finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            tpp.setText(displayname);

                        }

                    } else {
                        Toast.makeText(upload_dokumen.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
            case 6:
            {
                if (resultCode == RESULT_OK) {

                    upp = data.getData();
                    //push(imageUri);
                    String displayname;


                    //  Bitmap bmp = BitmapFactory.decodeFile();


                    if (upp != null) {
                        String uristring=upp.toString();
                        File myFile = new File(uristring);
                        pp=myFile;
                        if (uristring.startsWith("content://"))
                        {
                            Cursor cursor = null;
                            try {
                                cursor = upload_dokumen.this.getContentResolver().query(upp,null,null,null,null);
                                if (cursor!= null && cursor.moveToFirst())
                                {
                                    displayname = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    tpp.setText(displayname);

                                }
                            }
                            finally {
                                cursor.close();
                            }
                        }
                        else if (uristring.startsWith("file://"))
                        {
                            displayname= myFile.getName();
                            tpp.setText(displayname);

                        }

                    } else {
                        Toast.makeText(upload_dokumen.this, "berhasil", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

