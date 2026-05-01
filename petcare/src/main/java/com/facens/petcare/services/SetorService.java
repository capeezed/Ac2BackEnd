package com.facens.petcare.services;

import com.facens.petcare.exceptions.BusinessException;
import com.facens.petcare.exceptions.ResourceNotFoundException;
import com.facens.petcare.models.Setor;
import com.facens.petcare.repositories.SetorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SetorService {
    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public List<Setor> listarComFuncionarios() {
        return setorRepository.findAll();
    }

    public Setor cadastrar(Setor setor) {
        if (setor.getNome() == null || setor.getNome().isBlank()) {
            throw new BusinessException("Nome do setor e obrigatorio");
        }
        return setorRepository.save(setor);
    }

    public Setor buscar(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor nao encontrado"));
    }
}
