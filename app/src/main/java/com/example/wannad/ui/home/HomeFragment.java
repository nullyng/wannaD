package com.example.wannad.ui.home;

import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wannad.BottomNavigation;
import com.example.wannad.R;

public class HomeFragment extends Fragment {
    ListView list;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

       list = (ListView) root.findViewById(R.id.cafe_menu);
       CafeAdapter adapter = new CafeAdapter(inflater.getContext(),R.layout.listitem1);
        list.setAdapter(adapter);
       return root;
    }
}
class CafeAdapter extends BaseAdapter{
    private Context mcontext;
    int layout;
    LayoutInflater inflater;
    String[] name = {
            "STARBUCKS",
            "EDIYA",
            "A TWOSOME PLACE",
            "HOLLYS COFFE"
    };

    public CafeAdapter(Context context, int layout){
        this.mcontext = context;
        this.layout = layout;
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
       Context context = viewGroup.getContext();

       if(view == null){
           view = inflater.inflate(layout,viewGroup,false);
       }

       ImageView iconImage = (ImageView) view.findViewById(R.id.cafe_icon);
       TextView cafeName = (TextView) view.findViewById(R.id.cafe_name);

       iconImage.setImageResource(R.drawable.starbucks);
       cafeName.setText(name[position]);


        return null;
    }
}