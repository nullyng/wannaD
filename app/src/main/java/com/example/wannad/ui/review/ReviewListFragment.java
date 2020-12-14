package com.example.wannad.ui.review;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannad.R;
import com.example.wannad.Review;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewListFragment extends Fragment {
    ListView list;
    TextView cName, dName;
    String ckey, dkey, context;
    RatingBar ratingBar, ratingBarAvg;
    TextView reviewCnt;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Review[] review;
    int cnt;
    float sum = 0, avg = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reviewlist, container, false);

        list = root.findViewById(R.id.reviewList);
        cName = root.findViewById(R.id.review_cafe);
        dName = root.findViewById(R.id.review_drink);
        ckey = getArguments().getString("cname");
        dkey = getArguments().getString("dname");
        review_read();
        ratingBar = root.findViewById(R.id.reviewRating);
        reviewCnt = root.findViewById(R.id.reviewCnt);
        ratingBarAvg = root.findViewById(R.id.reviewRatingAvg);

        cName.setText(ckey);
        dName.setText(dkey);
       // ReviewAdapter adapter = new ReviewAdapter(inflater.getContext(),R.layout.review_list,review);
        //list.setAdapter(adapter);

        return root;
    }

    class ReviewAdapter extends BaseAdapter {
        Context context;
        int layout;
        Review[] review;
        LayoutInflater inflater;

        public ReviewAdapter(Context context, int layout, Review[] review) {
            this.context = context;
            this.layout = layout;
            this.review = review;
            if(review == null){
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                return;
            }
            inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return review.length;
        }

        @Override
        public Object getItem(int i) {
            return review[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = inflater.inflate(layout, null);
            }

            ImageView iconImage = (ImageView) view.findViewById(R.id.review_image);
            TextView review_context = (TextView) view.findViewById(R.id.review_context);
            TextView review_name = view.findViewById(R.id.review_name);
            RatingBar ratingBar = view.findViewById(R.id.reviewRating);

            iconImage.setImageResource(R.drawable.starbucks);
            review_context.setText(review[position].getContext());
            review_name.setText(review[position].getName());
            ratingBar.setRating(review[position].getStar());

            return view;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //review_read();
    }

    public void review_read(){
        Toast.makeText(getActivity(), "db read", Toast.LENGTH_SHORT).show();
        DatabaseReference childreference = mDatabase.child(ckey).child(dkey);

        childreference.addChildEventListener(new ChildEventListener() {
            int i = 0;
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot rsnapshot : snapshot.getChildren()){
                    Review temp = new Review(rsnapshot.child("name").getValue().toString(),
                            rsnapshot.child("context").toString(),Float.valueOf(rsnapshot.child("star").getValue().toString()));

                    Toast.makeText(getActivity(), temp.getName(), Toast.LENGTH_SHORT).show();
                    review[i++] = temp;
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
