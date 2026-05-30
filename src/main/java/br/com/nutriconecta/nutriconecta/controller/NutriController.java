package br.com.nutriconecta.nutriconecta.controller;

import br.com.nutriconecta.nutriconecta.model.*;
import br.com.nutriconecta.nutriconecta.service.NutriService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class NutriController {
    private final NutriService service;

    public NutriController(NutriService service) {
        this.service = service;
    }

    @ModelAttribute("tipos")
    TipoUsuario[] tipos() {
        return TipoUsuario.values();
    }

    @ModelAttribute("statusDoacao")
    StatusDoacao[] statusDoacao() {
        return StatusDoacao.values();
    }

    @ModelAttribute("statusSolicitacao")
    StatusSolicitacao[] statusSolicitacao() {
        return StatusSolicitacao.values();
    }

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("totalDoacoes", service.count("Doacao"));
        model.addAttribute("totalInstituicoes", service.instituicoes().size());
        model.addAttribute("totalRetiradas", service.count("Retirada"));
        model.addAttribute("totalDistribuido", service.totalDistribuido());
        return "index";
    }

    @GetMapping("/usuarios/listar")
    String usuarios(Model model) {
        model.addAttribute("usuarios", service.usuarios());
        return "usuarios";
    }

    @GetMapping("/usuarios/novo")
    String usuarioNovo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario-form";
    }

    @GetMapping("/usuarios/editar/{id}")
    String usuarioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", service.find(Usuario.class, id));
        return "usuario-form";
    }

    @PostMapping("/usuarios/salvar")
    String usuarioSalvar(@ModelAttribute Usuario usuario, RedirectAttributes redirect) {
        service.save(usuario);
        redirect.addFlashAttribute("mensagem", "Usuário salvo com sucesso.");
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/usuarios/excluir/{id}")
    String usuarioExcluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.delete(service.find(Usuario.class, id));
        redirect.addFlashAttribute("mensagem", "Usuário excluído com sucesso.");
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/enderecos/{usuarioId}")
    String enderecos(@PathVariable Long usuarioId, Model model) {
        model.addAttribute("usuario", service.find(Usuario.class, usuarioId));
        model.addAttribute("enderecos", service.enderecos(usuarioId));
        return "enderecos";
    }

    @GetMapping("/enderecos/novo/{usuarioId}")
    String enderecoNovo(@PathVariable Long usuarioId, Model model) {
        Endereco endereco = new Endereco();
        endereco.setUsuario(service.find(Usuario.class, usuarioId));
        model.addAttribute("endereco", endereco);
        return "endereco-form";
    }

    @PostMapping("/enderecos/salvar")
    String enderecoSalvar(@ModelAttribute Endereco endereco, RedirectAttributes redirect) {
        Long usuarioId = endereco.getUsuario().getId();
        endereco.setUsuario(service.find(Usuario.class, usuarioId));
        service.save(endereco);
        redirect.addFlashAttribute("mensagem", "Endereço salvo com sucesso.");
        return "redirect:/enderecos/" + usuarioId;
    }

    @GetMapping("/alimentos/listar")
    String alimentos(Model model) {
        model.addAttribute("alimentos", service.alimentos());
        return "alimentos";
    }

    @GetMapping("/alimentos/novo")
    String alimentoNovo(Model model) {
        model.addAttribute("alimento", new Alimento());
        return "alimento-form";
    }

    @PostMapping("/alimentos/salvar")
    String alimentoSalvar(@ModelAttribute Alimento alimento, RedirectAttributes redirect) {
        service.save(alimento);
        redirect.addFlashAttribute("mensagem", "Alimento salvo com sucesso.");
        return "redirect:/alimentos/listar";
    }

    @GetMapping("/doacoes/listar")
    String doacoes(Model model) {
        model.addAttribute("doacoes", service.doacoes());
        return "doacoes";
    }

    @GetMapping("/doacoes/nova")
    String doacaoNova(Model model) {
        model.addAttribute("doacao", new Doacao());
        model.addAttribute("doadores", service.doadores());
        return "doacao-form";
    }

    @PostMapping("/doacoes/salvar")
    String doacaoSalvar(@ModelAttribute Doacao doacao, RedirectAttributes redirect) {
        doacao.setDoador(service.find(Usuario.class, doacao.getDoador().getId()));
        if (doacao.getStatus() == null) {
            doacao.setStatus(StatusDoacao.ABERTA);
        }
        service.save(doacao);
        redirect.addFlashAttribute("mensagem", "Doação salva com sucesso.");
        return "redirect:/doacoes/listar";
    }

    @GetMapping("/doacoes/detalhes/{id}")
    String detalhesDoacao(@PathVariable Long id, Model model) {
        model.addAttribute("doacao", service.find(Doacao.class, id));
        model.addAttribute("itens", service.itens(id));
        return "doacao-detalhes";
    }

    @PostMapping("/doacoes/status")
    String alterarStatusDoacao(@RequestParam Long id, @RequestParam StatusDoacao status, RedirectAttributes redirect) {
        Doacao doacao = service.find(Doacao.class, id);
        doacao.setStatus(status);
        service.save(doacao);
        redirect.addFlashAttribute("mensagem", "Status da doação atualizado.");
        return "redirect:/doacoes/listar";
    }

    @GetMapping("/itens/{doacaoId}")
    String itens(@PathVariable Long doacaoId, Model model) {
        model.addAttribute("doacao", service.find(Doacao.class, doacaoId));
        model.addAttribute("itens", service.itens(doacaoId));
        return "itens";
    }

    @GetMapping("/itens/novo/{doacaoId}")
    String itemNovo(@PathVariable Long doacaoId, Model model) {
        model.addAttribute("doacao", service.find(Doacao.class, doacaoId));
        return "item-form";
    }

    @PostMapping("/itens/salvar")
    String itemSalvar(
            @RequestParam Long doacaoId,
            @RequestParam String nomeAlimento,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String unidadeMedida,
            @RequestParam BigDecimal quantidade,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validade,
            RedirectAttributes redirect
    ) {
        ItemDoacao item = new ItemDoacao();
        item.setDoacao(service.find(Doacao.class, doacaoId));
        item.setAlimento(service.alimentoPorNomeOuNovo(nomeAlimento, categoria, unidadeMedida));
        item.setQuantidade(quantidade);
        item.setValidade(validade);
        service.save(item);
        redirect.addFlashAttribute("mensagem", "Item salvo com sucesso.");
        return "redirect:/itens/" + doacaoId;
    }

    @GetMapping("/solicitacoes/listar")
    String solicitacoes(Model model) {
        model.addAttribute("solicitacoes", service.solicitacoes());
        return "solicitacoes";
    }

    @GetMapping("/solicitacoes/nova")
    String solicitacaoNova(Model model) {
        model.addAttribute("doacoes", service.doacoes());
        model.addAttribute("instituicoes", service.instituicoes());
        return "solicitacao-form";
    }

    @PostMapping("/solicitacoes/salvar")
    String solicitacaoSalvar(@RequestParam Long doacaoId, @RequestParam Long instituicaoId, RedirectAttributes redirect) {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setDoacao(service.find(Doacao.class, doacaoId));
        solicitacao.setInstituicao(service.find(Usuario.class, instituicaoId));
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        service.save(solicitacao);
        redirect.addFlashAttribute("mensagem", "Solicitação criada com sucesso.");
        return "redirect:/solicitacoes/listar";
    }

    @PostMapping("/solicitacoes/status")
    String alterarStatusSolicitacao(@RequestParam Long id, @RequestParam StatusSolicitacao status, RedirectAttributes redirect) {
        Solicitacao solicitacao = service.find(Solicitacao.class, id);
        solicitacao.setStatus(status);

        if (status == StatusSolicitacao.APROVADA) {
            solicitacao.getDoacao().setStatus(StatusDoacao.RESERVADA);
        }

        service.save(solicitacao);
        redirect.addFlashAttribute("mensagem", "Status da solicitação atualizado.");
        return "redirect:/solicitacoes/listar";
    }

    @GetMapping("/retiradas/listar")
    String retiradas(Model model) {
        model.addAttribute("retiradas", service.retiradas());
        return "retiradas";
    }

    @GetMapping("/retiradas/nova/{solicitacaoId}")
    String retiradaNova(@PathVariable Long solicitacaoId, Model model) {
        model.addAttribute("solicitacao", service.find(Solicitacao.class, solicitacaoId));
        return "retirada-form";
    }

    @PostMapping("/retiradas/salvar")
    String retiradaSalvar(
            @RequestParam Long solicitacaoId,
            @RequestParam String responsavel,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataRetirada,
            @RequestParam(required = false) String observacoes,
            RedirectAttributes redirect
    ) {
        Solicitacao solicitacao = service.find(Solicitacao.class, solicitacaoId);
        Retirada retirada = new Retirada();
        retirada.setSolicitacao(solicitacao);
        retirada.setResponsavel(responsavel);
        retirada.setDataRetirada(dataRetirada);
        retirada.setObservacoes(observacoes);
        service.save(retirada);

        solicitacao.getDoacao().setStatus(StatusDoacao.CONCLUIDA);
        service.save(solicitacao);

        redirect.addFlashAttribute("mensagem", "Retirada registrada com sucesso.");
        return "redirect:/retiradas/listar";
    }
}
