package com.apk.appinfocovid19.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apk.appinfocovid19.Model.Noticias;
import com.apk.appinfocovid19.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NoticiasActivity extends AppCompatActivity {


    Toolbar toolbar;


    private DatabaseReference reference;
    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recycler=findViewById(R.id.recyclernotticas);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        reference= FirebaseDatabase.getInstance().getReference("Noticias");

    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Noticias> recyclerOptions = new FirebaseRecyclerOptions.Builder<Noticias>()
                .setQuery(reference, Noticias.class).build();
        FirebaseRecyclerAdapter<Noticias,Items> adapter =new FirebaseRecyclerAdapter<Noticias, Items>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull final Items items, final int i, @NonNull Noticias tutores) {
                final String key = getRef(i).getKey();
                reference.child(key).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            final String ruta=dataSnapshot.child("ruta").getValue().toString();
                            final String descripcion=dataSnapshot.child("descripcion").getValue().toString();
                            items.tvdescripion.setText(descripcion);

                            Glide.with(getApplicationContext())
                                            .load(ruta)
                                            .placeholder(R.drawable.default_profile_image)
                                            .fitCenter()
                                            .into(items.imgfoto);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(NoticiasActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @NonNull
            @Override
            public Items onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticias, parent, false);
                return new Items(vista);

            }
        };
        recycler.setAdapter(adapter);
        adapter.startListening();
    }

    public  static class Items extends RecyclerView.ViewHolder{
        TextView tvdescripion;
        ImageView imgfoto;

        public Items(@NonNull View itemView) {
            super(itemView);
            tvdescripion=(TextView)itemView.findViewById(R.id.tvdescripcion);
            imgfoto=(ImageView)itemView.findViewById(R.id.imdboticia);

        }
    }
}
