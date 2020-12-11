package com.example.wannad.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.wannad.R;

public class HomeDrinks extends Fragment {
    ListView list;
    ImageView img;
    TextView title;

    public static HomeDrinks newinstance(){
        return new HomeDrinks();
    }
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        String[] drinks = {
                "아메리카노",
                "카페라떼",
                "아인슈페너",
                "초코라떼",
                "딸기라떼",
                "피치우롱",
                "얼그레이"
        };

        View root = inflater.inflate(R.layout.fragment_homedrinks, container, false);

        String key = getArguments().getString("name");
        title = (TextView)root.findViewById(R.id.cafe_title);
        list = (ListView)root.findViewById(R.id.cafe_drinks);
        img = (ImageView)root.findViewById(R.id.title_cafeImg);
        img.setImageResource(R.drawable.starbucks);

        title.setText(key);
        DrinkAdapter adapter = new DrinkAdapter(inflater.getContext(),R.layout.drinklist,drinks);
        list.setAdapter(adapter);

        return root;
    }
}

class DrinkAdapter extends BaseAdapter {
    Context context;
    int layout;
    String[] name;
    LayoutInflater inflater;

    public DrinkAdapter(Context context, int layout,String[] name){
        this.context = context;
        this.layout = layout;
        this.name = name;
        inflater =(LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return name[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null){
            view = inflater.inflate(layout,null);
        }

        TextView drinkName = (TextView) view.findViewById(R.id.drinkname);

        drinkName.setText(name[position]);


        return view;
    }
}