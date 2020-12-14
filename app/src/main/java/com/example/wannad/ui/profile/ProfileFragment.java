package com.example.wannad.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.wannad.BottomNavigation;
import com.example.wannad.MainActivity;
import com.example.wannad.R;
import com.example.wannad.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    public static String name = null, profile = null;
    public static String username;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User");

    public void read_profile(String name, String profile) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProfileFragment.this.name = (String) dataSnapshot.child(username).child("name").getValue();
                ProfileFragment.this.profile = (String) dataSnapshot.child(username).child("profile").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    //public void onStart() {
        //super.onStart();
        //read_profile();
    //}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.welcome);
        final ImageView ivProfile = root.findViewById(R.id.ivProfile);
        username = ((BottomNavigation)getActivity()).strNickname;

            read_profile(name, profile);

        Glide.with(this).load(profile).thumbnail(Glide.with(this).load(R.drawable.loading_profie)).into(ivProfile);

        textView.setText(name + "님, 환영합니다.");

        TextView logout_btn = root.findViewById(R.id.logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogout();
            }
        });

        return root;
    }
    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity();
            }
        });
    }
    private void redirectLoginActivity() {
        final Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        //finish();
    }
}