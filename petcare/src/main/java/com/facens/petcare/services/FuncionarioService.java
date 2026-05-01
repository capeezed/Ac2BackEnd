package com.facens.petcare.services;

import com.facens.petcare.exceptions.BusinessException;
import com.facens.petcare.exceptions.ResourceNotFoundException;
import com.facens.petcare.models.Funcionario;
import com.facens.petcare.models.Setor;
import com.facens.petcare.repositories.FuncionarioRepository;
import com.facens.petcare.repositories.SetorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, SetorRepository setorRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
    }

    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }

    public Funcionario cadastrar(Funcionario funcionario, Long setorId) {
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new BusinessException("Nome do funcionario e obrigatorio");
        }
        if (funcionario.getEmail() == null || funcionario.getEmail().isBlank()) {
            throw new BusinessException("Email do funcionario e obrigatorio");
        }
        if (funcionarioRepository.existsByEmail(funcionario.getEmail())) {
            throw new BusinessException("Ja existe funcionario com este email");
        }
        if (setorId != null) {
            Setor setor = setorRepository.findById(setorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Setor nao encontrado"));
            funcionario.setSetor(setor);
        }
        return funcionarioRepository.save(funcionario);
    }
}
