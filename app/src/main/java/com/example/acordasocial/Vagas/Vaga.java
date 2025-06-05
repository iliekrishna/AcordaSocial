package com.example.acordasocial.Vagas;

public class Vaga {
    private String id;
    private String nomeOng;  // MELHOR deixar como nomeOng direto
    private String descricao;
    private String local;
    private String horario;

    private String data;

    public Vaga() {
    }

    public Vaga(String id, String nomeOng, String descricao, String local, String horario, String data) {
        this.id = id;
        this.nomeOng = nomeOng;
        this.descricao = descricao;
        this.local = local;
        this.horario = horario;
        this.data = data;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNomeOng() { return nomeOng; }
    public void setNomeOng(String nomeOng) { this.nomeOng = nomeOng; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getData() { return data;}
    public void setData(String data) {this.data = data;}
}
