package com.facens.petcare.controllers;

import com.facens.petcare.dto.AnimalRequest;
import com.facens.petcare.dto.ConsultaRequest;
import com.facens.petcare.dto.ProntuarioRequest;
import com.facens.petcare.dto.TutorRequest;
import com.facens.petcare.dto.VacinacaoRequest;
import com.facens.petcare.dto.VeterinarioRequest;
import com.facens.petcare.models.Animal;
import com.facens.petcare.models.Consulta;
import com.facens.petcare.models.Prontuario;
import com.facens.petcare.models.Tutor;
import com.facens.petcare.models.Vacinacao;
import com.facens.petcare.models.Veterinario;
import com.facens.petcare.services.PetcareService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/petcare")
@CrossOrigin
public class PetcareController {
    private final PetcareService petcareService;

    public PetcareController(PetcareService petcareService) {
        this.petcareService = petcareService;
    }

    @GetMapping("/tutores")
    public List<Tutor> listarTutores() {
        return petcareService.listarTutores();
    }

    @PostMapping("/tutores")
    @ResponseStatus(HttpStatus.CREATED)
    public Tutor cadastrarTutor(@RequestBody TutorRequest request) {
        Tutor tutor = new Tutor();
        tutor.setNome(request.nome());
        tutor.setTelefone(request.telefone());
        tutor.setEmail(request.email());
        return petcareService.cadastrarTutor(tutor);
    }

    @GetMapping("/animais")
    public List<Animal> listarAnimais() {
        return petcareService.listarAnimais();
    }

    @PostMapping("/animais")
    @ResponseStatus(HttpStatus.CREATED)
    public Animal cadastrarAnimal(@RequestBody AnimalRequest request) {
        Animal animal = new Animal();
        animal.setNome(request.nome());
        animal.setEspecie(request.especie());
        animal.setRaca(request.raca());
        animal.setDataNascimento(request.dataNascimento());
        return petcareService.cadastrarAnimal(animal, request.tutorId());
    }

    @GetMapping("/animais/{animalId}/historico")
    public Animal historicoAnimal(@PathVariable Long animalId) {
        return petcareService.buscarAnimalComHistorico(animalId);
    }

    @GetMapping("/veterinarios")
    public List<Veterinario> listarVeterinarios() {
        return petcareService.listarVeterinarios();
    }

    @PostMapping("/veterinarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Veterinario cadastrarVeterinario(@RequestBody VeterinarioRequest request) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(request.nome());
        veterinario.setCrmv(request.crmv());
        veterinario.setEspecialidade(request.especialidade());
        return petcareService.cadastrarVeterinario(veterinario);
    }

    @GetMapping("/consultas")
    public List<Consulta> listarConsultas() {
        return petcareService.listarConsultas();
    }

    @PostMapping("/consultas")
    @ResponseStatus(HttpStatus.CREATED)
    public Consulta agendarConsulta(@RequestBody ConsultaRequest request) {
        return petcareService.agendarConsulta(
                request.animalId(),
                request.veterinarioId(),
                request.dataHora(),
                request.motivo()
        );
    }

    @PostMapping("/animais/{animalId}/prontuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Prontuario registrarProntuario(@PathVariable Long animalId, @RequestBody ProntuarioRequest request) {
        Prontuario prontuario = new Prontuario();
        prontuario.setDescricao(request.descricao());
        prontuario.setDiagnostico(request.diagnostico());
        prontuario.setTratamento(request.tratamento());
        return petcareService.registrarProntuario(animalId, prontuario);
    }

    @GetMapping("/animais/{animalId}/prontuarios")
    public List<Prontuario> prontuariosDoAnimal(@PathVariable Long animalId) {
        return petcareService.prontuariosDoAnimal(animalId);
    }

    @PostMapping("/animais/{animalId}/vacinacoes")
    @ResponseStatus(HttpStatus.CREATED)
    public Vacinacao registrarVacinacao(@PathVariable Long animalId, @RequestBody VacinacaoRequest request) {
        Vacinacao vacinacao = new Vacinacao();
        vacinacao.setVacina(request.vacina());
        vacinacao.setDataAplicacao(request.dataAplicacao());
        vacinacao.setProximaDose(request.proximaDose());
        vacinacao.setObservacoes(request.observacoes());
        return petcareService.registrarVacinacao(animalId, vacinacao);
    }

    @GetMapping("/animais/{animalId}/vacinacoes")
    public List<Vacinacao> vacinasDoAnimal(@PathVariable Long animalId) {
        return petcareService.vacinasDoAnimal(animalId);
    }

}
