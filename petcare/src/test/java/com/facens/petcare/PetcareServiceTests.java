package com.facens.petcare;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.facens.petcare.exceptions.BusinessException;
import com.facens.petcare.models.Animal;
import com.facens.petcare.models.Tutor;
import com.facens.petcare.models.Veterinario;
import com.facens.petcare.services.PetcareService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetcareServiceTests {
    @Autowired
    private PetcareService petcareService;

    @Test
    void naoPermiteDuasConsultasNoMesmoHorarioParaMesmoVeterinario() {
        Tutor tutor = criarTutor("Tutor Agenda");
        Animal animal = criarAnimal("Luna", "Gato", tutor.getId());
        Veterinario veterinario = criarVeterinario("Dra. Agenda", "Gato");
        LocalDateTime horario = LocalDateTime.now().plusDays(10).withSecond(0).withNano(0);

        petcareService.agendarConsulta(animal.getId(), veterinario.getId(), horario, "Primeira consulta");

        assertThrows(BusinessException.class, () ->
                petcareService.agendarConsulta(animal.getId(), veterinario.getId(), horario, "Consulta repetida"));
    }

    @Test
    void veterinarioAtendeSomenteSuaEspecialidade() {
        Tutor tutor = criarTutor("Tutor Especialidade");
        Animal animal = criarAnimal("Thor", "Cachorro", tutor.getId());
        Veterinario veterinario = criarVeterinario("Dr. Gatos", "Gato");
        LocalDateTime horario = LocalDateTime.now().plusDays(12).withSecond(0).withNano(0);

        assertThrows(BusinessException.class, () ->
                petcareService.agendarConsulta(animal.getId(), veterinario.getId(), horario, "Consulta"));
    }

    private Tutor criarTutor(String nome) {
        Tutor tutor = new Tutor();
        tutor.setNome(nome);
        tutor.setTelefone("15999990000");
        tutor.setEmail(nome.toLowerCase().replace(" ", ".") + "@email.com");
        return petcareService.cadastrarTutor(tutor);
    }

    private Animal criarAnimal(String nome, String especie, Long tutorId) {
        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setEspecie(especie);
        animal.setRaca("SRD");
        animal.setDataNascimento(LocalDate.of(2022, 1, 1));
        return petcareService.cadastrarAnimal(animal, tutorId);
    }

    private Veterinario criarVeterinario(String nome, String especialidade) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(nome);
        veterinario.setCrmv("CRMV TESTE");
        veterinario.setEspecialidade(especialidade);
        return petcareService.cadastrarVeterinario(veterinario);
    }
}
