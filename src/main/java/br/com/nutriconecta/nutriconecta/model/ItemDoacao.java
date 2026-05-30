package br.com.nutriconecta.nutriconecta.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "itens_doacao")
public class ItemDoacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidade;

    private LocalDate validade;

    @ManyToOne
    @JoinColumn(name = "id_doacao", nullable = false)
    private Doacao doacao;

    @ManyToOne
    @JoinColumn(name = "id_alimento", nullable = false)
    private Alimento alimento;

    public ItemDoacao() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }
    public Doacao getDoacao() { return doacao; }
    public void setDoacao(Doacao doacao) { this.doacao = doacao; }
    public Alimento getAlimento() { return alimento; }
    public void setAlimento(Alimento alimento) { this.alimento = alimento; }
}
