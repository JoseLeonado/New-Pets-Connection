package com.facec.new_pets_connection.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.facec.new_pets_connection.R;
import com.facec.new_pets_connection.adpater.AdpaterPets;
import com.facec.new_pets_connection.config.FirebaseConfig;
import com.facec.new_pets_connection.config.RecyclerItemClickListener;
import com.facec.new_pets_connection.model.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class PetsActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewPetsPublicos;
    private AdpaterPets adpaterPets;
    private List<Pet> listaDePets = new ArrayList<>();
    private DatabaseReference petsPublicoRef;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        inicializarComponentes();

        autenticacao = FirebaseConfig.getFirebaseAutenticacao();
        petsPublicoRef = FirebaseConfig.getFirebase()
                .child("pets");

        recyclerViewPetsPublicos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPetsPublicos.setHasFixedSize(true);

        adpaterPets = new AdpaterPets(listaDePets, this);
        recyclerViewPetsPublicos.setAdapter(adpaterPets);

        recuperarPetsPublicos();

        recyclerViewPetsPublicos.addOnItemTouchListener(
            new RecyclerItemClickListener(
                    this,
                    recyclerViewPetsPublicos,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            Pet petSelecionado = listaDePets.get(position);
                            Intent i = new Intent(PetsActivity.this, DetalhesPetActivity.class);
                            i.putExtra("petSelecionado", petSelecionado);
                            startActivity(i);

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    }
            )

        );

    }

    public void recuperarPetsPublicos() {

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Listando Pets...")
                .setCancelable(false)
                .build();
        dialog.show();

        listaDePets.clear();
        petsPublicoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot pets : snapshot.getChildren()) {
                    Pet pet = pets.getValue(Pet.class);
                    listaDePets.add(pet);
                }

                Collections.reverse(listaDePets);
                adpaterPets.notifyDataSetChanged();

                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (autenticacao.getCurrentUser() != null) {
            menu.setGroupVisible(R.id.group_logado, true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_pets:
                startActivity(new Intent(getApplicationContext(), MeusPetsActivity.class));
                break;
            case R.id.menu_sair:
                autenticacao.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void inicializarComponentes() {
        recyclerViewPetsPublicos = findViewById(R.id.recyclerPetsPublicos);
    }
}