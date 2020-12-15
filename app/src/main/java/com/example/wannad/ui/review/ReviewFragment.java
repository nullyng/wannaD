package com.example.wannad.ui.review;

import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wannad.BottomNavigation;
import com.example.wannad.R;
import com.example.wannad.Review;
import com.example.wannad.User;
import com.example.wannad.User_Review;
import com.example.wannad.ui.profile.ProfileFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReviewFragment extends Fragment {
    Spinner spinnerd, spinnerc;
    RatingBar ratingBar;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String cname, dname, context, username;
    public static String nickName;
    TextView review_write;
    Button send;
    Review temp;
    User_Review temp2;
    long time;
    public void read_nickname(String nickname) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nickName = (String) dataSnapshot.child("User_Nickname").child(username).child("nickname").getValue();
                Toast.makeText(getActivity(), nickName , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


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
                "STARBUCKS",
                "EDIYA",
                "A TWOSOME PLACE",
                "HOLLYS COFFE",
                "COFFEE BEAN",
                "PASCUCCI",
                "Tom N Toms"
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

        username = ((BottomNavigation)getActivity()).strNickname;
        read_nickname(nickName);
         send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(getActivity(), "작성이되었습니다", Toast.LENGTH_SHORT).show();
                 //스피너에서 선택된 값 받아오기
                 cname = spinnerc.getSelectedItem().toString();
                 dname = spinnerd.getSelectedItem().toString();

                 float star = Float.valueOf(ratingBar.getRating());
                 context = review_write.getText().toString();


                 //현재 시간 받아오기
                 time = System.currentTimeMillis();
                 Date mDate = new Date(time);
                 SimpleDateFormat rsimpleDate = new SimpleDateFormat("yyyy.MM.dd");
                 SimpleDateFormat usimpleDate = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                 String rgetTime = rsimpleDate.format(mDate);
                 String ugetTime = usimpleDate.format(mDate);

                 //Review테이블에 값 저장
                 temp = new Review();
                 temp.setContext(context);
                 temp.setNickname(nickName);
                 temp.setStar(star);
                 temp.setTime(rgetTime);
                 Map<String, Object> postValues = temp.toMap();
                 Map<String, Object> childUpdates = new HashMap<>();
                 String random = getRandomString();
                 childUpdates.put("/Review/"+cname+"/"+dname+"/review"+random,postValues);
                 mDatabase.updateChildren(childUpdates);

                 //User테이블에 review 정보 저장
                 temp2 = new User_Review(cname, dname, context, star, ugetTime);
                 Map<String, Object> postValues2 = temp2.toMap();
                 Map<String, Object> childUpdates2 = new HashMap<>();
                 childUpdates2.put("/User/" +username+"/"+"review"+"/review"+random,postValues2);
                 mDatabase.updateChildren(childUpdates2);

                review_write.setText("");
                spinnerc.setSelection(0);
                ratingBar.setRating(0);
                spinnerd.setSelection(0);
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
