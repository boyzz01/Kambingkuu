package app.kambingku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.widget.ANImageView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Win 10 on 1/4/2018.
 */

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<product> mMessageList;
    SharedPreferences sp,data;

    public MessageAdapter(List<product> mMessageList) {


        this.mMessageList = mMessageList;

    }

    @Override
    public void onViewRecycled(MessageViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_produk,parent, false);
        return new MessageViewHolder(v);


    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView nama;
        public TextView harga;
        public TextView id_a;
        public ANImageView gambar;
        public ImageView cart;



        public MessageViewHolder(final View view) {
            super(view);


            nama=view.findViewById(R.id.nama);
            id_a=view.findViewById(R.id.id_ternak);
            harga=view.findViewById(R.id.harga);
            gambar=view.findViewById(R.id.gambarp);
            cart=view.findViewById(R.id.cart);





        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder,int i)
    {


        final product c = mMessageList.get(i);
        viewHolder.nama.setText(c.getGender()+" Terhebat di Kelasnya");
        viewHolder.id_a.setText("#"+c.getId_ternak());
        String test=""+NumberFormat.getNumberInstance(Locale.US).format(c.getHarga());
        test=test.replaceAll(",",".");
        viewHolder.harga.setText("Rp."+test);

        final String foto1=between(c.getFoto(),"","|");
        final String foto2=between(c.getFoto(),"|","");


        String url="https://idtronik.com/kambing/assets/images/kambing/"+foto1;
        viewHolder.gambar.setImageUrl(url);

        sp = viewHolder.cart.getContext().getSharedPreferences("login", viewHolder.cart.getContext().MODE_PRIVATE);






        viewHolder.cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getStatus() == 1) {
                        Toast.makeText(viewHolder.nama.getContext(), "Produk Ini Sedang Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    } else {


                       //

                            sp = viewHolder.cart.getContext().getSharedPreferences("login", viewHolder.cart.getContext().MODE_PRIVATE);



                            TextView txtView = (TextView) ((Activity) view.getContext()).findViewById(R.id.notif);
                            txtView.setVisibility(View.VISIBLE);
                            SharedPreferences.Editor e = sp.edit();
                            int temp;
                            if (sp.contains("notif") ) {

                                Boolean masuk=false;
                                if (sp.getInt("notif",0)<=4)
                                {

                                    for (int i=1;i<=sp.getInt("notif",0);i++)
                                    {
                                        data = viewHolder.cart.getContext().getSharedPreferences("data" + i, viewHolder.cart.getContext().MODE_PRIVATE);
                                        SharedPreferences.Editor a = data.edit();
                                        if (data.getInt("id",0)==c.getId_ternak())
                                        {
                                           masuk=true;
                                            Toast.makeText(viewHolder.nama.getContext(), "Produk Ini Sudah ada di keranjang anda", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {

                                        }
                                    }

                                    if (!masuk) {
                                        int test = c.getId_ternak();
                                        temp = sp.getInt("notif", 0) + 1;
                                        e.putInt("notif", temp);
                                        e.commit();
                                        data = viewHolder.cart.getContext().getSharedPreferences("data" + temp, viewHolder.cart.getContext().MODE_PRIVATE);
                                        SharedPreferences.Editor a = data.edit();
                                        a.putInt("id", c.getId_ternak());
                                        a.putString("gender", c.getGender());
                                        a.putString("foto", foto1);
                                        a.putInt("harga", c.getHarga());

                                        a.commit();
                                        c.setCart(true);

                                        txtView.setText("" + temp);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(viewHolder.nama.getContext(),"Maksimal Pembelian adalah 5 Produk",Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                c.setCart(true);
                                e.putInt("notif", 1);
                                e.putInt("data" + 1, c.getId_ternak());
                                e.commit();
                                data = viewHolder.cart.getContext().getSharedPreferences("data" + 1, viewHolder.cart.getContext().MODE_PRIVATE);
                                SharedPreferences.Editor a = data.edit();
                                a.putInt("id", c.getId_ternak());
                                a.putString("gender", c.getGender());
                                a.putString("foto", foto1);
                                a.putInt("harga", c.getHarga());
                                a.commit();
                                txtView.setText("" + 1);
                                ;
                            }
                        }

                        // Toast.makeText(viewHolder.nama.getContext(),"Produk Telah Masu",Toast.LENGTH_SHORT).show();

                    }



            });


        //
        //
        //
        // LocalDate birth= new LocalDate(tahun,bulan,tanggal);

        viewHolder.gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c.getStatus() == 1) {
                    Toast.makeText(viewHolder.nama.getContext(), "Produk Ini Sedang Tidak Tersedia", Toast.LENGTH_SHORT).show();
                }
                else {
                    int umur = 0;

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar dob = Calendar.getInstance();
                    try {
                        dob.setTime(sdf.parse(c.getDob()));
                        umur = getAge(dob);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent detail = new Intent(view.getContext(), detail_deskripsi.class);

                    detail.putExtra("id", c.getId_ternak());
                    detail.putExtra("nama", c.getGender());
                    detail.putExtra("harga", c.harga);
                    detail.putExtra("sharga", viewHolder.harga.getText());
                    detail.putExtra("foto1", foto1);
                    detail.putExtra("foto2", foto2);
                    detail.putExtra("umur", "" + umur);
                    detail.putExtra("status", "" + c.getStatus());

                    // Log.d("umur",""+umur);


                    view.getContext().startActivity(detail);
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
