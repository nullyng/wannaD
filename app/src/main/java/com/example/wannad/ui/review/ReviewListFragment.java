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

public class ReviewListFragment extends Fragment {
    ListView list;
    TextView cName, dName;
    String ckey, dkey;
    RatingBar ratingBar, ratingBarAvg;
    TextView reviewCnt;
    int cnt;
    float sum = 0, avg = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] review = {
                "맛있어요~~",
                "향긋한 향~~~최고!!",
                "맛없어서 깜짝 놀랐어요ㅠㅠ ",
                "굿굿 쫀맛"
        };

        View root = inflater.inflate(R.layout.fragment_reviewlist, container, false);
        list = root.findViewById(R.id.reviewList);
        cName = root.findViewById(R.id.review_cafe);
        dName = root.findViewById(R.id.review_drink);
        ckey = getArguments().getString("cname");
        dkey = getArguments().getString("dname");

        ratingBar = root.findViewById(R.id.reviewRating);
        reviewCnt = root.findViewById(R.id.reviewCnt);
        ratingBarAvg = root.findViewById(R.id.reviewRatingAvg);

        cName.setText(ckey);
        dName.setText(dkey);
        ReviewAdapter adapter = new ReviewAdapter(inflater.getContext(),R.layout.review_list,review);
        list.setAdapter(adapter);

        return root;
    }

    class ReviewAdapter extends BaseAdapter {
        Context context;
        int layout;
        String[] review;
        LayoutInflater inflater;

        public ReviewAdapter(Context context, int layout, String[] review) {
            this.context = context;
            this.layout = layout;
            this.review = review;
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

            iconImage.setImageResource(R.drawable.starbucks);
            review_context.setText(review[position]);


            return view;
        }
    }
}
