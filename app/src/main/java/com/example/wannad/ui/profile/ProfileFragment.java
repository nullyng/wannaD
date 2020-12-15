package com.example.wannad.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wannad.BottomNavigation;
import com.example.wannad.MainActivity;
import com.example.wannad.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;


public class ProfileFragment extends Fragment {
    public static String name = null, profile = null;
    public static String username;
    EditText edittext;
    public static String nickname = "";
    TextView nick_btn;
    TextView nick_space;
    TextView textView;

    ImageView ivProfile;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
    DatabaseReference nickDatabase = FirebaseDatabase.getInstance().getReference().child("User_Nickname");
    private DatabaseReference databaseReference;

    public void read_profile(String name, String profile) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProfileFragment.this.name = (String) dataSnapshot.child(username).child("name").getValue();
                ProfileFragment.this.profile = (String) dataSnapshot.child(username).child("profile").getValue();
                textView.setText(ProfileFragment.this.name + "님, 환영합니다.");
                Glide.with(getActivity()).load(ProfileFragment.this.profile).thumbnail(Glide.with(getActivity()).load(R.drawable.loading_profie)).into(ivProfile);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void read_nickname() {
        nickDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProfileFragment.this.nickname = (String) dataSnapshot.child(username).child("nickname").getValue();
                if(nickname == null)
                {
                    nickname = username;
                    nickDatabase.child(name).child("nickname").setValue(nickname);
                }
                nick_space.setText("닉네임 : " + nickname);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /*private ValueEventListener checkRegister = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //Toast.makeText(getActivity().getApplicationContext(), "닉네임 검사", Toast.LENGTH_LONG).show();
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            while (child.hasNext()) {
                if (edittext.getText().toString().equals(child.next().getKey())) {
                    Toast.makeText(getActivity().getApplicationContext(), "존재하는 닉네임 입니다.", Toast.LENGTH_LONG).show();
                    databaseReference.removeEventListener(this);
                    return;
                }
            }
            //onClickNickname();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        textView = root.findViewById(R.id.welcome);
        ivProfile = root.findViewById(R.id.ivProfile);
        username = ((BottomNavigation)getActivity()).strNickname;
        read_profile(name, profile);
        read_nickname();
        Glide.with(this).load(profile).thumbnail(Glide.with(this).load(R.drawable.loading_profie)).into(ivProfile);

        textView.setText(name + "님, 환영합니다.");


        //닉네임 변경
        nick_btn = root.findViewById(R.id.nickname_edit_btn);
        nick_space = root.findViewById(R.id.nickname);
        nick_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClickNickname();
            }
        });


        //로그아웃 버튼
        TextView logout_btn = root.findViewById(R.id.logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogout();
            }
        });


        return root;
    }
    private void onClickNickname(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView= inflater.inflate(R.layout.dialog_member, null);
        edittext = (EditText)dialogView.findViewById(R.id.nickname_edit);
        builder.setTitle("닉네임 변경");
        builder.setMessage("변경할 닉네임을 입력해주세요");
        builder.setView(dialogView)
                .setPositiveButton("변경",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity().getApplicationContext(),edittext.getText().toString() ,Toast.LENGTH_LONG).show();
                        nickname = edittext.getText().toString();

                        //닉네임 중복 검사하는 코드
                        //databaseReference.addListenerForSingleValueEvent(checkRegister);

                        nick_space.setText("닉네임 : " + nickname);
                        nickDatabase.child(username).child("nickname").setValue(nickname);
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }
    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity();
            }
        });
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }
    private void redirectLoginActivity() {
        final Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        //finish();
    }
}