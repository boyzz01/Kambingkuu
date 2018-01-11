package app.kambingku;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Win 10 on 1/9/2018.
 */

public class prosesadapter extends RecyclerView.Adapter<prosesadapter.MessageViewHolder> {
    private List<proseskambing> mMessageList;
    SharedPreferences sp;
    int total = 0;
    private TextView sum;
    public boolean kosong = false;

    public prosesadapter(List<proseskambing> mMessageList) {


        this.mMessageList = mMessageList;

    }

    @Override
    public void onViewRecycled(prosesadapter.MessageViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public prosesadapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perkembangan, parent, false);


        return new prosesadapter.MessageViewHolder(v);


    }


    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView nama;
        public TextView harga;
        public TextView id_a;
        public ANImageView gambar;
        public TextView usia;
        public TextView sisa;
        public TextView berat;


        public MessageViewHolder(final View view) {
            super(view);


            nama = view.findViewById(R.id.namaa);
            id_a = view.findViewById(R.id.id);
            harga = view.findViewById(R.id.harga);
            gambar = view.findViewById(R.id.gambar);
            usia = view.findViewById(R.id.usia);
            sisa = view.findViewById(R.id.waktu);
            berat = view.findViewById(R.id.berat);


        }
    }

    @Override
    public void onBindViewHolder(final prosesadapter.MessageViewHolder viewHolder, final int i) {


        final proseskambing c = mMessageList.get(i);

        viewHolder.nama.setText(c.gender+" Terhebat di Kelasnya");
        viewHolder.id_a.setText("#"+c.getId());
        String test=""+ NumberFormat.getNumberInstance(Locale.US).format(c.getHarga());
        test=test.replaceAll(",",".");
        viewHolder.harga.setText("Rp."+test);

        final String foto1=between(c.getFoto(),"","|");
        final String foto2=between(c.getFoto(),"|","");


        String url="https://idtronik.com/kambing/assets/images/kambing/"+foto1;
        viewHolder.gambar.setImageUrl(url);

        viewHolder.usia.setText(c.getDob());
        viewHolder.sisa.setText(c.getSisa());
        viewHolder.berat.setText(""+c.getBerat()+"Kg");


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
