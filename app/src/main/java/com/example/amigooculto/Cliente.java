package com.example.amigooculto;

public class Cliente {

    private int id;
    private int idSorteio;
    private String nome;
    private String amigoOculto;
    private String dicaPresente;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdSorteio() {
        return idSorteio;
    }

    public void setIdSorteio(int idSorteio) {
        this.idSorteio = idSorteio;
    }

    public String getAmigoOculto() {
        return amigoOculto;
    }

    public void setAmigoOculto(String amigoOculto) {
        this.amigoOculto = amigoOculto;
    }

    public String getDicaPresente() {
        return dicaPresente;
    }

    public void setDicaPresente(String dicaPresente) {
        this.dicaPresente = dicaPresente;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }




    public Cliente() {

    }

    public Cliente(int id, int id_sorteio,String nome, String amigoOculto, String dicaPresente, String email) {
        this.id = id;
        this.idSorteio = id_sorteio;
        this.nome = nome;
        this.amigoOculto = amigoOculto;
        this.dicaPresente = dicaPresente;
        this.email = email;

    }

    public Cliente(int id_sorteio, String nome, String amigoOculto, String dicaPresente, String email) {
        this.idSorteio = id_sorteio;
        this.nome = nome;
        this.amigoOculto = amigoOculto;
        this.dicaPresente = dicaPresente;
        this.email = email;


    }





    @Override
    public String toString(){
        return  nome + " dica de presente " + dicaPresente;
    }


}
