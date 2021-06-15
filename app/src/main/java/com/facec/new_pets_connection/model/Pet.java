package com.facec.new_pets_connection.model;

import com.facec.new_pets_connection.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pet implements Serializable {

    private  String id;
    private String nome;
    private String idade;
    private String sexo;
    private String raca;
    private String vacina;
    private String endereco;
    private List<String> fotos = new ArrayList<>();

    public Pet() {
        DatabaseReference petRef = FirebaseConfig.getFirebase()
                .child("meus_pets");
        setId(petRef.push().getKey());
    }

    public void salvar() {

        String idUsuario = FirebaseConfig.getIdUsuario();

        DatabaseReference petRef = FirebaseConfig.getFirebase()
                .child("meus_pets");

        petRef.child(idUsuario)
                .child(getId())
                .setValue(this);

        salvarPetPublico();
    }

    public void salvarPetPublico() {

        DatabaseReference petRef = FirebaseConfig.getFirebase()
                .child("pets");

        petRef.child(getId())
                .setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getVacina() {
        return vacina;
    }

    public void setVacina(String vacina) {
        this.vacina = vacina;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
