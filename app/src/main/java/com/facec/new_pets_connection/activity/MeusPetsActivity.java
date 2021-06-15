package com.facec.new_pets_connection.activity;

import android.content.Intent;
import android.os.Bundle;

import com.facec.new_pets_connection.adpater.AdpaterPets;
import com.facec.new_pets_connection.config.FirebaseConfig;
import com.facec.new_pets_connection.model.Pet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Adapter;

import com.facec.new_pets_connection.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MeusPetsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPets;
    private List<Pet> pets = new ArrayList<>();
    private AdpaterPets adpaterPets;
    private DatabaseReference petUsuarioRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pets);

        petUsuarioRef = FirebaseConfig.getFirebase()
                .child("meus_pets")
                .child(FirebaseConfig.getIdUsuario());

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarPetActivity.class));
            }
        });

        recyclerViewPets.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPets.setHasFixedSize(true);

        adpaterPets = new AdpaterPets(pets, this);
        recyclerViewPets.setAdapter(adpaterPets);

        recuperarPets();

        getSupportActionBar().setTitle("Meus pets cadastrados");

    }

    public void recuperarPets() {
        petUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pets.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    pets.add(ds.getValue(Pet.class));
                }

                Collections.reverse(pets);
                adpaterPets.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void inicializarComponentes() {
        recyclerViewPets = findViewById(R.id.recyclerPets);
    }
}