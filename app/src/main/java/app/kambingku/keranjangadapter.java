package app.kambingku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.widget.ANImageView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Win 10 on 1/7/2018.
 */

public class keranjangadapter  extends RecyclerView.Adapter<keranjangadapter.MessageViewHolder> {
    private List<keranjang> mMessageList;
    SharedPreferences sp;
    int total=0;
    private TextView sum;
    public boolean kosong=false;

    public keranjangadapter(List<keranjang> mMessageList) {


        this.mMessageList = mMessageList;

    }

    @Override
    public void onViewRecycled(keranjangadapter.MessageViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public keranjangadapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.keranjang,parent, false);



        return new keranjangadapter.MessageViewHolder(v);


    }



    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView nama;
        public TextView harga;
        public TextView id_a;
        public ANImageView gambar;
        public ImageView buang;
        public ConstraintLayout linearLayout;




        public MessageViewHolder(final View view) {
            super(view);


            nama=view.findViewById(R.id.nama);
            id_a=view.findViewById(R.id.id_ternak);
            harga=view.findViewById(R.id.harga);
            gambar=view.findViewById(R.id.gambar);
            buang=view.findViewById(R.id.buang);

            linearLayout=view.findViewById(R.id.linearLayout2);




        }
    }

    @Override
    public void onBindViewHolder(final keranjangadapter.MessageViewHolder viewHolder, final int i)
    {


        final keranjang c = mMessageList.get(i);
        viewHolder.nama.setText(c.getGender()+" Terhebat di Kelasnya");
        viewHolder.id_a.setText("#"+c.getId_ternak());
        String test=""+ NumberFormat.getNumberInstance(Locale.US).format(c.getHarga());
        test=test.replaceAll(",",".");
        viewHolder.harga.setText("Rp."+test);






        String url="https://idtronik.com/kambing/assets/images/kambing/"+c.getFoto();
        Log.d("foto",c.getFoto());
        viewHolder.gambar.setImageUrl(url);



        viewHolder.buang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sum = (TextView) ((Activity)viewHolder.buang.getContext()).findViewById(R.id.jumlah);
                int jum=0;



          //     Toast.makeText(viewHolder.gambar.getContext(),""+i+" "+mMessageList.size(),Toast.LENGTH_SHORT).show();
               SharedPreferences sp = viewHolder.buang.getContext().getSharedPreferences("login", viewHolder.buang.getContext().MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();


                mMessageList.remove(i);
               notifyItemRemoved(i);
                int temp = sp.getInt("notif", 0) - 1;
                e.putInt("notif", temp);
                e.commit();
                SharedPreferences data = viewHolder.buang.getContext().getSharedPreferences("data"+i+1, viewHolder.buang.getContext().MODE_PRIVATE);
                SharedPreferences.Editor a=data.edit();
                a.clear();
                a.commit();
                notifyDataSetChanged();

                for (int b=0;b<=mMessageList.size()-1;b++)
                {


                    jum=jum+mMessageList.get(b).getHarga();

                }

                e.putInt("jumlah",jum);
                e.commit();
                String tempa=""+ NumberFormat.getNumberInstance(Locale.US).format(jum);
                tempa=tempa.replaceAll(",",".");
                sum.setText("Rp."+tempa);

                if (jum==0)
                {
                    viewHolder.linearLayout.setVisibility(View.GONE);
                    keranjang_belanja.kosong=true;
                }



            }
        });




    }

    public static int getAge(Calendar dob) throws Exception {
        Calendar today = Calendar.getInstance();
        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);

        int age = curYear - dobYear;

        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) {
            age--;

        } else if (dobMonth == curMonth) { // same month? check for day

            int curDay = today.get(Calendar.DAY_OF_MONTH);

            int dobDay = dob.get(Calendar.DAY_OF_MONTH);

            if (dobDay > curDay) { // this year can't be counted!

                age--;

            }

        }
        return age;

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



    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
