package com.example.wannad.ui.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wannad.R;
import com.google.firebase.database.DatabaseReference;

public class ReviewFragment extends Fragment {
    Spinner spinnerd, spinnerc;
    DatabaseReference mDatabase;
    //DatabaseReference mDatabase;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);
        final RatingBar ratingBar = (RatingBar) root.findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(),
                        "감사합니다!", Toast.LENGTH_SHORT).show();
            }
        });
        String[] drinks = {
                "아메리카노",
                "카페라떼",
                "아인슈페너",
                "초코라떼",
                "딸기라떼",
                "피치우롱",
                "얼그레이"
        };
        String[] cafes = {
                "스타벅스",
                "이디야",
                "투썸플레이스",
                "할리스"
        };

        spinnerd = root.findViewById(R.id.spinnerdrink);
        ArrayAdapter<String> adapterd = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, drinks);
        adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerd.setAdapter(adapterd);

        spinnerc = root.findViewById(R.id.spinnercafe);
        ArrayAdapter<String> adapterc = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, cafes);
        adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerc.setAdapter(adapterc);

        return root;
    }

}
