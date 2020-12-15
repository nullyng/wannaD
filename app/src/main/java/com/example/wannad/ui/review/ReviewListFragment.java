package com.example.wannad.ui.review;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wannad.R;
import com.example.wannad.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewListFragment extends Fragment {
    TextView cName, dName;
    String ckey, dkey, context;
    RatingBar ratingBar, ratingBarAvg;
    ArrayList<Review> reviewArray;
    TextView reviewCnt;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Review");

    int cnt;
    float sum, avg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reviewlist, container, false);
        sum =0; avg = 0;
        recyclerView = root.findViewById(R.id.reviewList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        reviewArray = new ArrayList<>();

        cName = root.findViewById(R.id.review_cafe);
        dName = root.findViewById(R.id.review_drink);
        ckey = getArguments().getString("cname");
        ckey = getArguments().getString("cname");
        dkey = getArguments().getString("dname");
        ratingBar = root.findViewById(R.id.reviewRating);
        reviewCnt = root.findViewById(R.id.reviewCnt);
        ratingBarAvg = root.findViewById(R.id.reviewRatingAvg);

        cName.setText(ckey);
        dName.setText(dkey);

        review_read();

        adapter = new ReviewAdapter(getActivity(), reviewArray);
        recyclerView.setAdapter(adapter);

        return root;
    }

    class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CustomViewHolder> {
        Context context;
        ArrayList<Review> review;

        public ReviewAdapter(Context context, ArrayList<Review> review) {
            this.context = context;
            this.review = review;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent,false);
            CustomViewHolder holder = new CustomViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            holder.rv_name.setText(reviewArray.get(position).getNickname());
            holder.rv_context.setText(reviewArray.get(position).getContext());
            holder.star.setRating(reviewArray.get(position).getStar());
            holder.rv_time.setText(reviewArray.get(position).getTime());
            Glide.with(holder.itemView).load(reviewArray.get(position).getImg()).into(holder.review_img);
        }

        @Override
        public int getItemCount() {
            return (review != null ? review.size():0);
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder{
            TextView rv_name;
            TextView rv_context;
            TextView rv_time;
            RatingBar star;
            ImageView review_img;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                this.rv_context = itemView.findViewById(R.id.review_context);
                this.rv_time = itemView.findViewById(R.id.review_time);
                this.rv_name = itemView.findViewById(R.id.review_name);
                this.star = itemView.findViewById(R.id.reviewRating);
                this.review_img = itemView.findViewById(R.id.review_image);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //review_read();
    }

    public void review_read(){
        DatabaseReference childreference = mDatabase.child(ckey).child(dkey);

        childreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewArray.clear();
                for(DataSnapshot dataSnapshot :  snapshot.getChildren()){
                    Review temp = dataSnapshot.getValue(Review.class);
                    reviewArray.add(temp);
                    cnt++;
                    ratingAvg(temp.getStar());
                }
                adapter.notifyDataSetChanged();
                reviewCnt.setText(Integer.toString(cnt));
                ratingBarAvg.setRating(avg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public float ratingAvg(float cur){
        sum += cur;
        avg = sum/cnt;

        return avg;
    }
}