package com.facens.petcare;

import com.facens.petcare.models.Animal;
import com.facens.petcare.models.Funcionario;
import com.facens.petcare.models.Projeto;
import com.facens.petcare.models.Setor;
import com.facens.petcare.models.Tutor;
import com.facens.petcare.models.Veterinario;
import com.facens.petcare.services.FuncionarioService;
import com.facens.petcare.services.PetcareService;
import com.facens.petcare.services.ProjetoService;
import com.facens.petcare.services.SetorService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final SetorService setorService;
    private final FuncionarioService funcionarioService;
    private final ProjetoService projetoService;
    private final PetcareService petcareService;

    public DataLoader(
            SetorService setorService,
            FuncionarioService funcionarioService,
            ProjetoService projetoService,
            PetcareService petcareService
    ) {
        this.setorService = setorService;
        this.funcionarioService = funcionarioService;
        this.projetoService = projetoService;
        this.petcareService = petcareService;
    }

    @Override
    public void run(String... args) {
        Setor tecnologia = new Setor();
        tecnologia.setNome("Tecnologia");
        tecnologia.setDescricao("Desenvolvimento de sistemas");
        tecnologia = setorService.cadastrar(tecnologia);

        Funcionario ana = new Funcionario();
        ana.setNome("Ana Souza");
        ana.setEmail("ana@empresa.com");
        ana.setCargo("Analista");
        ana = funcionarioService.cadastrar(ana, tecnologia.getId());

        Projeto portal = new Projeto();
        portal.setNome("Portal Interno");
        portal.setDescricao("Sistema para gestao de processos internos");
        portal.setDataInicio(LocalDate.of(2026, 5, 1));
        portal.setDataFim(LocalDate.of(2026, 7, 30));
        portal.setOrcamento(new BigDecimal("25000"));
        portal = projetoService.cadastrar(portal);
        projetoService.vincularFuncionario(portal.getId(), ana.getId());

        Tutor tutor = new Tutor();
        tutor.setNome("Carlos Lima");
        tutor.setTelefone("15999990000");
        tutor.setEmail("carlos@email.com");
        tutor = petcareService.cadastrarTutor(tutor);

        Animal animal = new Animal();
        animal.setNome("Mel");
        animal.setEspecie("Cachorro");
        animal.setRaca("SRD");
        animal.setDataNascimento(LocalDate.of(2021, 3, 12));
        animal = petcareService.cadastrarAnimal(animal, tutor.getId());

        Veterinario veterinario = new Veterinario();
        veterinario.setNome("Dra. Marina");
        veterinario.setCrmv("CRMV-SP 12345");
        veterinario.setEspecialidade("Cachorro");
        veterinario = petcareService.cadastrarVeterinario(veterinario);

        petcareService.agendarConsulta(
                animal.getId(),
                veterinario.getId(),
                LocalDateTime.of(2026, 5, 20, 14, 0),
                "Consulta anual"
        );
    }
}
