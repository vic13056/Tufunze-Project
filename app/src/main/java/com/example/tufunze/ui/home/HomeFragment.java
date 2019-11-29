package com.example.tufunze.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tufunze.MyAdapter;
import com.example.tufunze.R;
import com.example.tufunze.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Users> options;
    FirebaseRecyclerAdapter<Users, MyAdapter> adapter;
    ArrayList<Users> arrayList;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        arrayList = new ArrayList<>();

        options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(reference,Users.class).build();
        adapter = new FirebaseRecyclerAdapter<Users, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter holder, int position, @NonNull Users model) {

                holder.txtName1.setText(model.getName());
                holder.txteEmail1.setText(model.getEmail());
                holder.txtPhone1.setText(model.getPhone());
                Picasso.get().load(model.getImage()).into(holder.imgbackground);

            }

            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return new MyAdapter(LayoutInflater.from(getContext()).inflate(R.layout.row,parent,false));
            }
        };


        recyclerView.setAdapter(adapter);
        return root;
    }
}