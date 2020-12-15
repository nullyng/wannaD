package com.example.wannad.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


import com.example.wannad.BottomNavigation;
import com.example.wannad.R;
import com.example.wannad.ui.review.ReviewListFragment;

public class HomeDrinks extends Fragment {
    ListView list;
    ImageView img;
    TextView title;
    String cName;
    String[] drinks;
    public static HomeDrinks newinstance(){
        return new HomeDrinks();
    }
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_homedrinks, container, false);

        cName = getArguments().getString("name");
        if(cName.equals("STARBUCKS"))
            drinks = getResources().getStringArray(R.array.starbucks);
        if(cName.equals("EDIYA"))
            drinks = getResources().getStringArray(R.array.ediya);
        if(cName.equals("A TWOSOME PLACE"))
            drinks = getResources().getStringArray(R.array.twosomeplace);
        if(cName.equals("HOLLYS COFFEE"))
            drinks = getResources().getStringArray(R.array.hollys);
        if(cName.equals("COFFEE BEAN"))
            drinks = getResources().getStringArray(R.array.coffeebean);
        if(cName.equals("PASCUCCI"))
            drinks = getResources().getStringArray(R.array.pascucci);
        if(cName.equals("Tom N Toms"))
            drinks = getResources().getStringArray(R.array.tomntoms);

        title = (TextView)root.findViewById(R.id.cafe_title);
        list = (ListView)root.findViewById(R.id.cafe_drinks);
        img = (ImageView)root.findViewById(R.id.title_cafeImg);
        img.setImageResource(getArguments().getInt("image"));

        title.setText(cName);
        DrinkAdapter adapter = new DrinkAdapter(inflater.getContext(),R.layout.drinklist,drinks);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment =new ReviewListFragment();
                Bundle bundle = new Bundle(2);
                bundle.putString("cname", cName);
                bundle.putString("dname",drinks[i]);
                fragment.setArguments(bundle);
                ((BottomNavigation)getActivity()).replaceFragment(fragment);
            }
        });

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