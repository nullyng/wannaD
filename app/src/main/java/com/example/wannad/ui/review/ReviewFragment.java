package com.example.wannad.ui.review;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.example.wannad.BottomNavigation;
import com.example.wannad.R;
import com.example.wannad.Review;
import com.example.wannad.User_Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReviewFragment extends Fragment {
    Spinner spinnerd, spinnerc;
    RatingBar ratingBar;
    String cname, dname, context, username;
    public static String nickName;
    TextView review_write;
    Button send;
    Review temp;
    User_Review temp2;
    long time;
    ArrayAdapter<CharSequence> adapterc, adapterd;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    public void read_nickname(String nickname) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nickName = (String) dataSnapshot.child("User_Nickname").child(username).child("nickname").getValue();
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

        String[] cafes = {
                "STARBUCKS",
                "EDIYA",
                "A TWOSOME PLACE",
                "HOLLYS COFFEE",
                "COFFEE BEAN",
                "PASCUCCI",
                "Tom N Toms"
        };

        ratingBar = (RatingBar) root.findViewById(R.id.ratingbar);
        send = root.findViewById(R.id.sendReview);
        review_write = root.findViewById(R.id.writeReview);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#feee7d"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.parseColor("#feee7d"), PorterDuff.Mode.SRC_ATOP);

        spinnerd = root.findViewById(R.id.spinnerdrink);
        //adapterd = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, init);
        //adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerd.setAdapter(adapterd);

        spinnerc = root.findViewById(R.id.spinnercafe);
        adapterc = new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_spinner_item, cafes);
        adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerc.setAdapter(adapterc);

        username = ((BottomNavigation)getActivity()).strNickname;
        read_nickname(nickName);

        //카페 이름 스피너 선택 이벤트리스너
        spinnerc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cname = adapterc.getItem(i).toString();
                if(cname.equals("STARBUCKS"))
                {
                    adapterd = ArrayAdapter.createFromResource(getActivity(), R.array.starbucks, android.R.layout.simple_spinner_dropdown_item);
                    adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerd.setAdapter(adapterd);

                    spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dname = adapterd.getItem(i).toString();
                        }
                        @Override

                            public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                if(cname.equals("EDIYA"))
                {
                    adapterd = ArrayAdapter.createFromResource(getActivity(), R.array.ediya, android.R.layout.simple_spinner_dropdown_item);
                    adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerd.setAdapter(adapterd);

                    spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dname = adapterd.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                if(cname.equals("A TWOSOME PLACE"))
                {
                    adapterd = ArrayAdapter.createFromResource(getActivity(), R.array.twosomeplace, android.R.layout.simple_spinner_dropdown_item);
                    adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerd.setAdapter(adapterd);

                    spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dname = adapterd.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                if(cname.equals("HOLLYS COFFEE"))
                {
                    adapterd = ArrayAdapter.createFromResource(getActivity(), R.array.hollys, android.R.layout.simple_spinner_dropdown_item);
                    adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerd.setAdapter(adapterd);

                    spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dname = adapterd.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                if(cname.equals("COFFEE BEAN"))
                {
                    adapterd = ArrayAdapter.createFromResource(getActivity(), R.array.coffeebean, android.R.layout.simple_spinner_dropdown_item);
                    adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerd.setAdapter(adapterd);

                    spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dname = adapterd.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                if(cname.equals("PASCUCCI"))
                {
                    adapterd = ArrayAdapter.createFromResource(getActivity(), R.array.pascucci, android.R.layout.simple_spinner_dropdown_item);
                    adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerd.setAdapter(adapterd);

                    spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dname = adapterd.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                if(cname.equals("Tom N Toms"))
                {
                    adapterd = ArrayAdapter.createFromResource(getActivity(), R.array.tomntoms, android.R.layout.simple_spinner_dropdown_item);
                    adapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerd.setAdapter(adapterd);


                    spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dname = adapterd.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //작성하기 버튼 눌렀을때
         send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(getActivity(), "작성 되었습니다", Toast.LENGTH_SHORT).show();
                 //스피너에서 선택된 값 받아오기
                 cname = spinnerc.getSelectedItem().toString();
                 dname = spinnerd.getSelectedItem().toString();

                 float star = Float.valueOf(ratingBar.getRating());
                 context = review_write.getText().toString();

                 //현재 시간 받아오기
                 time = System.currentTimeMillis();
                 Date mDate = new Date(time);
                 SimpleDateFormat rsimpleDate = new SimpleDateFormat("yyyy.MM.dd");
                 SimpleDateFormat usimpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
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
                 childUpdates.put("/Review/"+cname+"/"+dname+"/review"+ugetTime,postValues);
                 mDatabase.updateChildren(childUpdates);

                 //User테이블에 review 정보 저장
                 temp2 = new User_Review(cname, dname, context, star, rgetTime);
                 Map<String, Object> postValues2 = temp2.toMap();
                 Map<String, Object> childUpdates2 = new HashMap<>();
                 childUpdates2.put("/User_Review/" +username+"/review"+ugetTime,postValues2);
                 mDatabase.updateChildren(childUpdates2);

                review_write.setText("");
                spinnerc.setSelection(0);
                ratingBar.setRating(0);
                spinnerd.setSelection(0);

                //후기 작성하면 내가 작성한 리뷰페이지로 넘어감
                Fragment fragment = new ReviewListFragment();
                Bundle bundle = new Bundle(2);
                bundle.putString("cname",cname);
                bundle.putString("dname",dname);
                fragment.setArguments(bundle);
                ((BottomNavigation)getActivity()).replaceFragment(fragment);
            }
        });

        return root;
    }


}