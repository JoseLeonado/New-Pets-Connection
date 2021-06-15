package com.facec.new_pets_connection.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facec.new_pets_connection.R;
import com.facec.new_pets_connection.model.Pet;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class DetalhesPetActivity<Anuncio> extends AppCompatActivity {

    private CarouselView carouselView;
    private TextView nome;
    private TextView raca;
    private TextView endereco;
    private TextView idade;
    private TextView sexo;
    private TextView vacina;
    private Pet petSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pet);

        //Configurar toolbar
        getSupportActionBar().setTitle("Detalhe pet");

        //Incializar componentes de interface
        inicializarComponentes();

        //Recupera anúncio para exibicao
        petSelecionado = (Pet) getIntent().getSerializableExtra("petSelecionado");

        if( petSelecionado != null ){

            nome.setText( petSelecionado.getNome() );
            raca.setText( petSelecionado.getRaca() );
            endereco.setText( petSelecionado.getEndereco() );
            idade.setText( petSelecionado.getIdade());
            sexo.setText( petSelecionado.getSexo());
            vacina.setText( petSelecionado.getVacina());

            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    String urlString = petSelecionado.getFotos().get( position );
                    Picasso.get().load(urlString).into(imageView);
                }
            };

            carouselView.setPageCount( petSelecionado.getFotos().size() );
            carouselView.setImageListener( imageListener );

        }

    }

//    public void visualizarTelefone(View view){
//        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", petSelecionado.getTelefone(), null ));
//        startActivity( i );
//    }

    private void inicializarComponentes(){
        carouselView = findViewById(R.id.carouselView);
        nome = findViewById(R.id.textDetalhesNome);
        raca = findViewById(R.id.textDetalhesRaca);
        endereco = findViewById(R.id.textDetalhesEndereco);
        idade = findViewById(R.id.textDetalhesIdade);
        sexo = findViewById(R.id.textDetalhesSexo);
        vacina = findViewById(R.id.textDetalhesVacinas);
    }



}