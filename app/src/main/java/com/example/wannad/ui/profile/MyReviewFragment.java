package com.example.wannad.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wannad.Myreview;
import com.example.wannad.R;
import com.example.wannad.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class MyReviewFragment extends Fragment {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ArrayList<Myreview> myreviewArray;
    TextView review_cnt;
    private String username;
    private int cnt;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myreview,container,false);

        cnt = 0;
        recyclerView = root.findViewById(R.id.myreviewList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myreviewArray = new ArrayList<>();

        review_cnt = root.findViewById(R.id.myreviewCnt);
        username = getArguments().getString("name");
        myreview_read();

        adapter = new MyReviewAdapter(getActivity(), myreviewArray);
        recyclerView.setAdapter(adapter);

        return root;
    }

    class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.CustomViewHolder> {
        Context context;
        ArrayList<Myreview> review;

        public MyReviewAdapter(Context context, ArrayList<Myreview> review) {
            this.context = context;
            this.review = review;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myreview_list,parent,false);
            CustomViewHolder holder = new MyReviewAdapter.CustomViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            holder.rv_cname.setText(myreviewArray.get(position).getCname());
            holder.rv_dname.setText(myreviewArray.get(position).getDname());
            holder.time.setText(myreviewArray.get(position).getTime());
            holder.rv_context.setText(myreviewArray.get(position).getContext());
            holder.star.setRating(myreviewArray.get(position).getStar());
        }

        @Override
        public int getItemCount() {
            return (review != null ? review.size():0);
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder{
            TextView rv_cname;
            TextView rv_dname;
            TextView time;
            TextView rv_context;
            RatingBar star;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                this.rv_context = itemView.findViewById(R.id.review_context);
                this.rv_cname = itemView.findViewById(R.id.myreview_cname);
                this.rv_dname = itemView.findViewById(R.id.myreview_dname);
                this.time = itemView.findViewById(R.id.myreview_time);
                this.star = itemView.findViewById(R.id.reviewRating);
            }
        }
    }

    public void myreview_read(){
        DatabaseReference childreference = mDatabase.child("User_Review").child(username);

        childreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myreviewArray.clear();
                for(DataSnapshot dataSnapshot :  snapshot.getChildren()){
                    Myreview temp = dataSnapshot.getValue(Myreview.class);
                    myreviewArray.add(temp);
                    cnt++;
                }
                adapter.notifyDataSetChanged();
                review_cnt.setText(Integer.toString(cnt));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
