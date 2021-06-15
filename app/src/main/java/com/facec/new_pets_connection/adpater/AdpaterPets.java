package com.facec.new_pets_connection.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facec.new_pets_connection.R;
import com.facec.new_pets_connection.model.Pet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdpaterPets extends RecyclerView.Adapter<AdpaterPets.MyViewHolder> {

    private List<Pet> pets = new ArrayList<>();
    private Context context;

    public AdpaterPets(List<Pet> pets, Context context) {
        this.pets = pets;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpater_pet, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Pet pet = pets.get(position);
        holder.nome.setText(pet.getNome());
        holder.raca.setText(pet.getRaca());
        holder.endereco.setText(pet.getEndereco());

        //Pega 1º img da lista
        List<String> urlFotos = pet.getFotos();
        String urlCapa = urlFotos.get(0);

        Picasso.get().load(urlCapa).into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView raca;
        TextView endereco;
        ImageView foto;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNome);
            raca  = itemView.findViewById(R.id.textRaca);
            endereco  = itemView.findViewById(R.id.textEndereco);
            foto   = itemView.findViewById(R.id.imgPet);
        }
    }
}
