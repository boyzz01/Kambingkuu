package app.kambingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    Button login;
    TextView home,forgot;
    EditText email,password;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final Button login = (Button) findViewById(R.id.btn_login);
        TextView home = (TextView) findViewById(R.id.home_txt);
        TextView forgot = (TextView) findViewById(R.id.forgot);
        final EditText email= (EditText) findViewById(R.id.email) ;
        final EditText password = (EditText) findViewById(R.id.password);
        sp=getSharedPreferences("login",MODE_PRIVATE);


        if(sp.contains("username") && sp.contains("password")){
            Intent mainIntent = new Intent(login.this, login_drawer.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            splash.login=true;
            login.this.startActivity(mainIntent);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if((email.getText().toString().equals("admin@gmail.com")) && (password.getText().toString().equals("admin")))
                {

                    Intent mainIntent = new Intent(login.this, login_drawer.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    splash.login=true;
                    login.this.startActivity(mainIntent);

                    Toast.makeText(login.this,"Login Berhasil",Toast.LENGTH_SHORT).show();

                    login.this.finish();
                }
                else
                {
                    Toast.makeText(login.this,"Email atau Password Salah",Toast.LENGTH_SHORT).show();
                }*/

                String usermail=email.getText().toString();
                String pass = password.getText().toString();

                masuk masuk= new masuk(login.this);
                masuk.execute(usermail,pass);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(login.this, drawer.class);
                login.this.startActivity(mainIntent);

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login.this,"Untuk sementara fungsi ini belum dapat bekerja",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}
