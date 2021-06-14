package com.facec.new_pets_connection.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facec.new_pets_connection.R;
import com.facec.new_pets_connection.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;

public class PetsActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        autenticacao = FirebaseConfig.getFirebaseAutenticacao();
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
                startActivity(new Intent());
                break;
            case R.id.menu_sair:
                autenticacao.signOut();
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}