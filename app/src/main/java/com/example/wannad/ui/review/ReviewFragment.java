package com.example.wannad.ui.review;

import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wannad.BottomNavigation;
import com.example.wannad.R;
import com.example.wannad.Review;
import com.example.wannad.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReviewFragment extends Fragment {
    Spinner spinnerd, spinnerc;
    RatingBar ratingBar;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String cname, dname, context, username;
    TextView review_write;
    Button send;
    Review temp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);

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

        ratingBar = (RatingBar) root.findViewById(R.id.ratingbar);
        send = root.findViewById(R.id.sendReview);
        review_write = root.findViewById(R.id.writeReview);

        spinnerd = root.findViewById(R.id.spinnerdrink);
        ArrayAdapter<String> adapterd = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, drinks);
        adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerd.setAdapter(adapterd);

        spinnerc = root.findViewById(R.id.spinnercafe);
        ArrayAdapter<String> adapterc = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, cafes);
        adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerc.setAdapter(adapterc);

         send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(getActivity(), "작성이되었습니다", Toast.LENGTH_SHORT).show();
                 cname = spinnerc.getSelectedItem().toString();
                 dname = spinnerd.getSelectedItem().toString();
                 float star = Float.valueOf(ratingBar.getRating());
                 context = review_write.getText().toString();
                 username = ((BottomNavigation)getActivity()).strNickname;

                 temp = new Review(username,context, star);
                 Map<String, Object> postValues = temp.toMap();
                 Map<String, Object> childUpdates = new HashMap<>();
                 String random = getRandomString();
                 childUpdates.put("/Review/"+cname+"/"+dname+"/review"+random,postValues);
                 mDatabase.updateChildren(childUpdates);
             }
         });

        return root;
    }

    private static String getRandomString() {
        int length = 10;
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();

        String chars[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "1","2","3","4","5","6","7","8","9","0"};

        for (int i = 0; i < length; i++) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }


}
