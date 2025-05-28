package com.example.acordasocial;

public class Vaga {
    private String nomeOng;
    private String descricao;
    private String local;

    public Vaga(String nomeOng, String descricao, String local) {
        this.nomeOng = nomeOng;
        this.descricao = descricao;
        this.local = local;
    }

    public String getNomeOng() {
        return nomeOng;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLocal() {
        return local;
    }
}
