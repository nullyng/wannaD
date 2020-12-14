package com.example.wannad.ui.home;

import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wannad.BottomNavigation;
import com.example.wannad.R;
import com.example.wannad.ui.drinks.DrinkFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    ListView list;
    TextView title;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference starbucks = database.child("starbucks");
    DatabaseReference ediya = database.child("ediya");
    DatabaseReference twosome = database.child("twosome");
    DatabaseReference hollys = database.child("hollys");

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final String[] name = {
                "STARBUCKS",
                "EDIYA",
                "A TWOSOME PLACE",
                "HOLLYS COFFE",
                "Name",
                "Name2"
        };

        View root = inflater.inflate(R.layout.fragment_home, container, false);

       list = (ListView) root.findViewById(R.id.cafe_menu);
       //title = (TextView) root.findViewById(R.id.title);
        final ViewGroup tempcont = container;
/*
       title.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(getActivity(), "press", Toast.LENGTH_SHORT).show();
           }
       });

 */
       CafeAdapter adapter = new CafeAdapter
               (inflater.getContext(),R.layout.listitem1,name);
       list.setAdapter(adapter);

       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Toast.makeText(tempcont.getContext(), "press"+i, Toast.LENGTH_SHORT).show();
               Fragment fragment =HomeDrinks.newinstance();
               Bundle bundle = new Bundle(1);
               bundle.putString("name",name[i]);
               fragment.setArguments(bundle);
               ((BottomNavigation)getActivity()).replaceFragment(fragment);
           }
       });

       return root;
    }
}
class CafeAdapter extends BaseAdapter{
    Context context;
    int layout;
    String[] name;
    LayoutInflater inflater;

    public CafeAdapter(Context context, int layout,String[] name){
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

       ImageView iconImage = (ImageView) view.findViewById(R.id.cafe_icon);
       TextView cafeName = (TextView) view.findViewById(R.id.cafe_name);

       iconImage.setImageResource(R.drawable.starbucks);
       cafeName.setText(name[position]);


        return view;
    }
}