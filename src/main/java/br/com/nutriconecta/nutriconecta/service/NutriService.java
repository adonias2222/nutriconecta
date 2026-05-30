package br.com.nutriconecta.nutriconecta.service;

import br.com.nutriconecta.nutriconecta.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NutriService {
    @PersistenceContext
    private EntityManager em;

    public <T> T find(Class<T> clazz, Long id) {
        if (id == null) {
            return null;
        }
        return em.find(clazz, id);
    }

    public <T> List<T> list(String jpql, Class<T> clazz) {
        return em.createQuery(jpql, clazz).getResultList();
    }

    public void save(Object entity) {
        em.merge(entity);
    }

    public void delete(Object entity) {
        if (entity == null) {
            return;
        }
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public List<Usuario> usuarios() {
        return list("from Usuario order by id desc", Usuario.class);
    }

    public List<Alimento> alimentos() {
        return list("from Alimento order by nome", Alimento.class);
    }

    public List<Doacao> doacoes() {
        return list("from Doacao order by id desc", Doacao.class);
    }

    public List<Solicitacao> solicitacoes() {
        return list("from Solicitacao order by id desc", Solicitacao.class);
    }

    public List<Retirada> retiradas() {
        return list("from Retirada order by id desc", Retirada.class);
    }

    public List<Usuario> doadores() {
        return em.createQuery("from Usuario where tipo = :tipo order by nome", Usuario.class)
                .setParameter("tipo", TipoUsuario.DOADOR)
                .getResultList();
    }

    public List<Usuario> instituicoes() {
        return em.createQuery("from Usuario where tipo = :tipo order by nome", Usuario.class)
                .setParameter("tipo", TipoUsuario.INSTITUICAO)
                .getResultList();
    }

    public List<Endereco> enderecos(Long usuarioId) {
        return em.createQuery("from Endereco where usuario.id = :usuarioId order by id desc", Endereco.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    public List<ItemDoacao> itens(Long doacaoId) {
        return em.createQuery("from ItemDoacao where doacao.id = :doacaoId order by id desc", ItemDoacao.class)
                .setParameter("doacaoId", doacaoId)
                .getResultList();
    }

    public Alimento alimentoPorNomeOuNovo(String nome, String categoria, String unidadeMedida) {
        List<Alimento> encontrados = em.createQuery("from Alimento where lower(nome) = lower(:nome)", Alimento.class)
                .setParameter("nome", nome == null ? "" : nome.trim())
                .getResultList();

        if (!encontrados.isEmpty()) {
            return encontrados.get(0);
        }

        Alimento alimento = new Alimento();
        alimento.setNome(nome == null || nome.isBlank() ? "Alimento sem nome" : nome.trim());
        alimento.setCategoria(categoria);
        alimento.setUnidadeMedida(unidadeMedida);
        em.persist(alimento);
        return alimento;
    }

    public Long count(String entity) {
        return em.createQuery("select count(x) from " + entity + " x", Long.class).getSingleResult();
    }

    public BigDecimal totalDistribuido() {
        BigDecimal valor = em.createQuery("select coalesce(sum(i.quantidade), 0) from ItemDoacao i", BigDecimal.class)
                .getSingleResult();
        return valor == null ? BigDecimal.ZERO : valor;
    }

    public void criarDadosIniciais() {
        if (count("Usuario") > 0) {
            return;
        }

        Usuario doador = new Usuario();
        doador.setNome("Mercado Solidário");
        doador.setEmail("doador@nutriconecta.com");
        doador.setSenha("123");
        doador.setTipo(TipoUsuario.DOADOR);
        doador.setTelefone("(98) 99999-0000");
        em.persist(doador);

        Usuario instituicao = new Usuario();
        instituicao.setNome("Cozinha Comunitária Esperança");
        instituicao.setEmail("instituicao@nutriconecta.com");
        instituicao.setSenha("123");
        instituicao.setTipo(TipoUsuario.INSTITUICAO);
        instituicao.setTelefone("(98) 98888-0000");
        em.persist(instituicao);

        Usuario admin = new Usuario();
        admin.setNome("Administrador NutriConecta");
        admin.setEmail("admin@nutriconecta.com");
        admin.setSenha("123");
        admin.setTipo(TipoUsuario.ADMIN);
        admin.setTelefone("(98) 97777-0000");
        em.persist(admin);

        Alimento arroz = alimentoPorNomeOuNovo("Arroz", "Não perecível", "kg");

        Doacao doacao = new Doacao();
        doacao.setTitulo("Cesta básica solidária");
        doacao.setDescricao("Alimentos disponíveis para retirada por instituição cadastrada.");
        doacao.setDoador(doador);
        doacao.setStatus(StatusDoacao.ABERTA);
        doacao.setDataExpiracao(LocalDate.now().plusDays(7));
        em.persist(doacao);

        ItemDoacao item = new ItemDoacao();
        item.setDoacao(doacao);
        item.setAlimento(arroz);
        item.setQuantidade(new BigDecimal("10"));
        item.setValidade(LocalDate.now().plusMonths(3));
        em.persist(item);
    }
}
