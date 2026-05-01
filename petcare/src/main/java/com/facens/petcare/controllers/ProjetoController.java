package com.facens.petcare.controllers;

import com.facens.petcare.dto.ProjetoRequest;
import com.facens.petcare.models.Projeto;
import com.facens.petcare.services.ProjetoService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projetos")
@CrossOrigin
public class ProjetoController {
    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping
    public List<Projeto> listar() {
        return projetoService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Projeto cadastrar(@RequestBody ProjetoRequest request) {
        Projeto projeto = new Projeto();
        projeto.setNome(request.nome());
        projeto.setDescricao(request.descricao());
        projeto.setDataInicio(request.dataInicio());
        projeto.setDataFim(request.dataFim());
        projeto.setOrcamento(request.orcamento());
        projeto.setStatus(request.status());
        return projetoService.cadastrar(projeto);
    }

    @GetMapping("/{id}")
    public Projeto buscarComFuncionarios(@PathVariable Long id) {
        return projetoService.buscarComFuncionarios(id);
    }

    @PostMapping("/{projetoId}/funcionarios/{funcionarioId}")
    public Projeto vincularFuncionario(@PathVariable Long projetoId, @PathVariable Long funcionarioId) {
        return projetoService.vincularFuncionario(projetoId, funcionarioId);
    }

    @GetMapping("/periodo")
    public List<Projeto> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return projetoService.buscarPorPeriodo(inicio, fim);
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public List<Projeto> buscarPorFuncionario(@PathVariable Long funcionarioId) {
        return projetoService.buscarPorFuncionario(funcionarioId);
    }
}
