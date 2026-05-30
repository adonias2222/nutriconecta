package br.com.nutriconecta.nutriconecta.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "doacoes")
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDoacao status = StatusDoacao.ABERTA;

    private LocalDate dataExpiracao;

    @ManyToOne
    @JoinColumn(name = "id_doador", nullable = false)
    private Usuario doador = new Usuario();

    public Doacao() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public StatusDoacao getStatus() { return status; }
    public void setStatus(StatusDoacao status) { this.status = status; }
    public LocalDate getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(LocalDate dataExpiracao) { this.dataExpiracao = dataExpiracao; }
    public Usuario getDoador() { return doador; }
    public void setDoador(Usuario doador) { this.doador = doador; }
}
