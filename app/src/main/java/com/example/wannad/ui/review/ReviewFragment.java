package com.example.wannad.ui.review;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wannad.BottomNavigation;
import com.example.wannad.MainActivity;
import com.example.wannad.R;

public class ReviewFragment extends Fragment {
    Spinner spinner;
    private ReviewViewModel reviewViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reviewViewModel =
                ViewModelProviders.of(this).get(ReviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_review, container, false);
        final RatingBar ratingBar = (RatingBar) root.findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getActivity(),
                        "New Rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
        spinner = root.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.add("아메리카노");
        adapter.add("아이스티");
        adapter.add("아이스초코");
        return root;


    }


}
