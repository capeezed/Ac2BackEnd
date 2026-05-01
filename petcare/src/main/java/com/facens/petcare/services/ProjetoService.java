package com.facens.petcare.services;

import com.facens.petcare.exceptions.BusinessException;
import com.facens.petcare.exceptions.ResourceNotFoundException;
import com.facens.petcare.models.Funcionario;
import com.facens.petcare.models.Projeto;
import com.facens.petcare.repositories.FuncionarioRepository;
import com.facens.petcare.repositories.ProjetoRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {
    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ProjetoService(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Projeto> listar() {
        return projetoRepository.findAll();
    }

    public Projeto cadastrar(Projeto projeto) {
        validarProjeto(projeto);
        if (projeto.getStatus() == null || projeto.getStatus().isBlank()) {
            projeto.setStatus("PLANEJADO");
        }
        return projetoRepository.save(projeto);
    }

    public Projeto buscarComFuncionarios(Long id) {
        return projetoRepository.findWithFuncionariosById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto nao encontrado"));
    }

    @Transactional
    public Projeto vincularFuncionario(Long projetoId, Long funcionarioId) {
        Projeto projeto = buscarComFuncionarios(projetoId);
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario nao encontrado"));

        boolean jaVinculado = projeto.getFuncionarios().stream()
                .anyMatch(item -> item.getId().equals(funcionarioId));
        if (jaVinculado) {
            throw new BusinessException("Funcionario ja esta vinculado ao projeto");
        }

        projeto.getFuncionarios().add(funcionario);
        return projetoRepository.save(projeto);
    }

    public List<Projeto> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            throw new BusinessException("Periodo invalido");
        }
        return projetoRepository.buscarPorPeriodo(inicio, fim);
    }

    public List<Projeto> buscarPorFuncionario(Long funcionarioId) {
        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new ResourceNotFoundException("Funcionario nao encontrado");
        }
        return projetoRepository.buscarPorFuncionario(funcionarioId);
    }

    private void validarProjeto(Projeto projeto) {
        if (projeto.getNome() == null || projeto.getNome().isBlank()) {
            throw new BusinessException("Nome do projeto e obrigatorio");
        }
        if (projeto.getDataInicio() == null) {
            throw new BusinessException("Data de inicio e obrigatoria");
        }
        if (projeto.getDataFim() != null && projeto.getDataFim().isBefore(projeto.getDataInicio())) {
            throw new BusinessException("Data final nao pode ser anterior a data inicial");
        }
    }
}
