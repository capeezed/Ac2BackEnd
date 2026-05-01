package com.facens.petcare.services;

import com.facens.petcare.exceptions.BusinessException;
import com.facens.petcare.exceptions.ResourceNotFoundException;
import com.facens.petcare.models.Animal;
import com.facens.petcare.models.Consulta;
import com.facens.petcare.models.Prontuario;
import com.facens.petcare.models.Tutor;
import com.facens.petcare.models.Vacinacao;
import com.facens.petcare.models.Veterinario;
import com.facens.petcare.repositories.AnimalRepository;
import com.facens.petcare.repositories.ConsultaRepository;
import com.facens.petcare.repositories.ProntuarioRepository;
import com.facens.petcare.repositories.TutorRepository;
import com.facens.petcare.repositories.VacinacaoRepository;
import com.facens.petcare.repositories.VeterinarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PetcareService {
    private final TutorRepository tutorRepository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final ConsultaRepository consultaRepository;
    private final ProntuarioRepository prontuarioRepository;
    private final VacinacaoRepository vacinacaoRepository;

    public PetcareService(
            TutorRepository tutorRepository,
            AnimalRepository animalRepository,
            VeterinarioRepository veterinarioRepository,
            ConsultaRepository consultaRepository,
            ProntuarioRepository prontuarioRepository,
            VacinacaoRepository vacinacaoRepository
    ) {
        this.tutorRepository = tutorRepository;
        this.animalRepository = animalRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.consultaRepository = consultaRepository;
        this.prontuarioRepository = prontuarioRepository;
        this.vacinacaoRepository = vacinacaoRepository;
    }

    public Tutor cadastrarTutor(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank()) {
            throw new BusinessException("Nome do tutor e obrigatorio");
        }
        return tutorRepository.save(tutor);
    }

    public List<Tutor> listarTutores() {
        return tutorRepository.findAll();
    }

    public Animal cadastrarAnimal(Animal animal, Long tutorId) {
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new BusinessException("Nome do animal e obrigatorio");
        }
        if (animal.getEspecie() == null || animal.getEspecie().isBlank()) {
            throw new BusinessException("Especie do animal e obrigatoria");
        }
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor nao encontrado"));
        animal.setTutor(tutor);
        return animalRepository.save(animal);
    }

    public List<Animal> listarAnimais() {
        return animalRepository.findAll();
    }

    public Animal buscarAnimalComHistorico(Long animalId) {
        return animalRepository.findWithTutorById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado"));
    }

    public Veterinario cadastrarVeterinario(Veterinario veterinario) {
        if (veterinario.getNome() == null || veterinario.getNome().isBlank()) {
            throw new BusinessException("Nome do veterinario e obrigatorio");
        }
        if (veterinario.getEspecialidade() == null || veterinario.getEspecialidade().isBlank()) {
            throw new BusinessException("Especialidade do veterinario e obrigatoria");
        }
        return veterinarioRepository.save(veterinario);
    }

    public List<Veterinario> listarVeterinarios() {
        return veterinarioRepository.findAll();
    }

    public Consulta agendarConsulta(Long animalId, Long veterinarioId, LocalDateTime dataHora, String motivo) {
        if (dataHora == null || dataHora.isBefore(LocalDateTime.now())) {
            throw new BusinessException("Data da consulta deve ser futura");
        }

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado"));
        Veterinario veterinario = veterinarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario nao encontrado"));

        if (!veterinario.getEspecialidade().equalsIgnoreCase(animal.getEspecie())) {
            throw new BusinessException("Veterinario atende apenas a especialidade " + veterinario.getEspecialidade());
        }
        if (consultaRepository.existsByVeterinarioIdAndDataHora(veterinarioId, dataHora)) {
            throw new BusinessException("Conflito de agenda para este veterinario");
        }

        Consulta consulta = new Consulta();
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);
        consulta.setDataHora(dataHora);
        consulta.setMotivo(motivo);
        consulta.setStatus("AGENDADA");
        return consultaRepository.save(consulta);
    }

    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    public Prontuario registrarProntuario(Long animalId, Prontuario prontuario) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado"));
        if (prontuario.getDescricao() == null || prontuario.getDescricao().isBlank()) {
            throw new BusinessException("Descricao do prontuario e obrigatoria");
        }
        prontuario.setAnimal(animal);
        prontuario.setDataRegistro(LocalDateTime.now());
        return prontuarioRepository.save(prontuario);
    }

    public Vacinacao registrarVacinacao(Long animalId, Vacinacao vacinacao) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal nao encontrado"));
        if (vacinacao.getVacina() == null || vacinacao.getVacina().isBlank()) {
            throw new BusinessException("Nome da vacina e obrigatorio");
        }
        vacinacao.setAnimal(animal);
        return vacinacaoRepository.save(vacinacao);
    }

    public List<Prontuario> prontuariosDoAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new ResourceNotFoundException("Animal nao encontrado");
        }
        return prontuarioRepository.findByAnimalIdOrderByDataRegistroDesc(animalId);
    }

    public List<Vacinacao> vacinasDoAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new ResourceNotFoundException("Animal nao encontrado");
        }
        return vacinacaoRepository.findByAnimalIdOrderByDataAplicacaoDesc(animalId);
    }
}
