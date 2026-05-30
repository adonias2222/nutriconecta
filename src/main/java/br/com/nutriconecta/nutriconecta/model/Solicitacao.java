package br.com.nutriconecta.nutriconecta.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacoes")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "id_doacao", nullable = false)
    private Doacao doacao;

    @ManyToOne
    @JoinColumn(name = "id_instituicao", nullable = false)
    private Usuario instituicao;

    public Solicitacao() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    public StatusSolicitacao getStatus() { return status; }
    public void setStatus(StatusSolicitacao status) { this.status = status; }
    public Doacao getDoacao() { return doacao; }
    public void setDoacao(Doacao doacao) { this.doacao = doacao; }
    public Usuario getInstituicao() { return instituicao; }
    public void setInstituicao(Usuario instituicao) { this.instituicao = instituicao; }
}
