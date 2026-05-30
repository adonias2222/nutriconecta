package br.com.nutriconecta.nutriconecta.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "retiradas")
public class Retirada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataRetirada;

    @Column(nullable = false)
    private String responsavel;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @OneToOne
    @JoinColumn(name = "id_solicitacao", nullable = false)
    private Solicitacao solicitacao;

    public Retirada() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataRetirada() { return dataRetirada; }
    public void setDataRetirada(LocalDateTime dataRetirada) { this.dataRetirada = dataRetirada; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public Solicitacao getSolicitacao() { return solicitacao; }
    public void setSolicitacao(Solicitacao solicitacao) { this.solicitacao = solicitacao; }
}
