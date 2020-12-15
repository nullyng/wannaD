package com.example.wannad.ui.review;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.fragment.app.FragmentActivity;

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
    ImageView review_img;
    Review temp;
    User_Review temp2;
    long time;
    ArrayAdapter<CharSequence> adapterc, adapterd;
    String[] drinks;

    Button imagebtn;
    String image = "null";
    int REQUEST_IMAGE_CODE = 1001;
    int REQUEST_EXTERNAL_STORAGE_PERMISSIOM=1002;

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
        String[] init ={"카페선택 부탁"};

        ratingBar = (RatingBar) root.findViewById(R.id.ratingbar);
        send = root.findViewById(R.id.sendReview);
        review_write = root.findViewById(R.id.writeReview);
        imagebtn = root.findViewById(R.id.picturePick);
        review_img = root.findViewById(R.id.review_picture);
        review_img.setVisibility(View.GONE);

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

        //작성하기 버튼 눌렀을때때
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
                 SimpleDateFormat usimpleDate = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                 String rgetTime = rsimpleDate.format(mDate);
                 String ugetTime = usimpleDate.format(mDate);

                 //Review테이블에 값 저장
                 temp = new Review();
                 temp.setContext(context);
                 temp.setNickname(nickName);
                 temp.setStar(star);
                 temp.setTime(rgetTime);
                 temp.setImg(image);
                 Map<String, Object> postValues = temp.toMap();
                 Map<String, Object> childUpdates = new HashMap<>();
                 String random = getRandomString();
                 childUpdates.put("/Review/"+cname+"/"+dname+"/review"+random,postValues);
                 mDatabase.updateChildren(childUpdates);

                 //User테이블에 review 정보 저장
                 temp2 = new User_Review(cname, dname, context, star, rgetTime,image);
                 Map<String, Object> postValues2 = temp2.toMap();
                 Map<String, Object> childUpdates2 = new HashMap<>();
                 childUpdates2.put("/User_Review/" +username+"/review"+random,postValues2);
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

         //갤러리 권한 요청
        if (ContextCompat.checkSelfPermission(
                getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {

        }  else {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }
                    ,REQUEST_EXTERNAL_STORAGE_PERMISSIOM);
        }

         //사진추가 눌렀을때
        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in,REQUEST_IMAGE_CODE);
            }
        });

        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //갤러리 부를 시 필요한 함수
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CODE){
            imagebtn.setText("사진변경");
            review_img.setVisibility(View.VISIBLE);

            image = (data.getData()).toString();
            Glide.with(this).load(Uri.parse(image)).into(review_img);

        }
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
