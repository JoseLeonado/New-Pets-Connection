package com.facec.new_pets_connection.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facec.new_pets_connection.R;
import com.facec.new_pets_connection.config.Permissoes;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CadastrarPetActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img1, img2, img3;
    private EditText campoNome, campoIdade, campoSexo, campoRaca, campoVacina, campoEndereco;
    private Button botaoCadastrar;

    private String[] permissoes = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private List<String> listaFotosRecuperada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pet);

        //Validar permissões
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializarComponentes();
    }

    public void validarDadosPet(View view) {

        String nome = campoNome.getText().toString();
        String idade = campoIdade.getText().toString();
        String sexo = campoSexo.getText().toString();
        String raca = campoRaca.getText().toString();
        String vacina = campoVacina.getText().toString();
        String endereco = campoEndereco.getText().toString();

        if (listaFotosRecuperada.size() != 0) {
            if (!nome.isEmpty()) {
                if (!idade.isEmpty()) {
                    if (!sexo.isEmpty()) {
                        if (!raca.isEmpty()) {
                            if (!vacina.isEmpty()) {
                                if (!endereco.isEmpty()) {

                                } else {
                                    exibirMsgDeErro("Preencha o campo endereco");
                                }
                            } else {
                                exibirMsgDeErro("Preencha o campo vacina");
                            }
                        } else {
                            exibirMsgDeErro("Preencha o campo raca");
                        }
                    } else {
                        exibirMsgDeErro("Preencha o campo sexo");
                    }
                } else {
                    exibirMsgDeErro("Preencha o campo idade");
                }
            } else {
                exibirMsgDeErro("Preencha o campo nome");
            }
        } else {
            exibirMsgDeErro("Selecione ao menos uma foto!");
        }


    }

    private void exibirMsgDeErro(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void salvarPet() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgCadastro1 :
                escolherImg(1);
                break;
            case R.id.imgCadastro2 :
                escolherImg(2);
                break;
            case R.id.imgCadastro3 :
                escolherImg(3 );
                break;

        }

    }

    public void escolherImg(int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            //Recuperar Imagem
            Uri imgSelecionada = data.getData();
            String caminhoImg = imgSelecionada.toString();

            //Configurar imagem no ImageView
            if (requestCode == 1) {
                img1.setImageURI(imgSelecionada);
                listaFotosRecuperada.add(caminhoImg);
            } else if (requestCode == 2) {
                img2.setImageURI(imgSelecionada);
            } else if (requestCode == 3) {
                img3.setImageURI(imgSelecionada);
            }

            listaFotosRecuperada.add(caminhoImg);

        }

    }

    private void inicializarComponentes (){

        img1 = findViewById(R.id.imgCadastro1);
        img2 = findViewById(R.id.imgCadastro2);
        img3 = findViewById(R.id.imgCadastro3);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        campoNome = findViewById(R.id.cadastrarPetEditNome);
        campoIdade = findViewById(R.id.cadastrarPetEditIdade);
        campoSexo = findViewById(R.id.cadastrarPetEditSexo);
        campoRaca = findViewById(R.id.cadastrarPetEditRaca);
        campoVacina = findViewById(R.id.cadastrarPetEditVacina);
        campoEndereco = findViewById(R.id.cadastrarPetEditEndereco);
        botaoCadastrar = findViewById(R.id.cadastrarPetBtnCadastrar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults) {

           if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                aletarValidcaoPermissao();
           }

        }

    }

    private  void aletarValidcaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negada");
        builder.setMessage("Para utilizar o app, é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}