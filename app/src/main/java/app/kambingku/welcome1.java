package app.kambingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Win 10 on 12/15/2017.
 */

public class welcome1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle s) {
        View view = inflater.inflate(R.layout.welcome1,container,false);


        Button login = (Button) view.findViewById(R.id.login);
        TextView skip = (TextView) view.findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                // Set onboarding_complete to true
                preferences.edit().putBoolean("onboarding_complete",true).apply();
// Launch the main Activity, called MainActivity
                Intent main = new Intent(view.getContext(),login.class);
                startActivity(main);// Close the OnboardingActivity
                getActivity().finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                // Set onboarding_complete to true
                preferences.edit().putBoolean("onboarding_complete",true).apply();
// Launch the main Activity, called MainActivity
                Intent main = new Intent(view.getContext(), login.class);
                startActivity(main);// Close the OnboardingActivity
                getActivity().finish();
            }
        });


        return view;
    }

    public void finishOnboarding() {
        // Get the shared preferences

    }
}
