# AC2 - Projetos e PetCare

Projeto simples em Spring Boot para apresentar a AC2 de Back-End.

## Como rodar

Entre na pasta do projeto:

```powershell
cd petcare
.\mvnw.cmd spring-boot:run
```

Depois abra:

- Frontend: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- JDBC URL do H2: `jdbc:h2:mem:petcaredb`
- Usuario: `sa`
- Senha: deixe em branco

## O que foi implementado

Parte 1 - Projetos:

- Cadastro e listagem de projetos
- Cadastro e listagem de funcionarios
- Cadastro e consulta de setores
- Vinculo entre funcionario e projeto
- Busca de projeto com funcionarios
- Busca de projetos por periodo
- Busca de projetos por funcionario
- Listagem de setores com funcionarios

Parte 2 - PetCare:

- Cadastro de tutor
- Cadastro de animal ligado ao tutor
- Cadastro de veterinario
- Agendamento de consulta
- Regra para nao permitir conflito de horario
- Regra para veterinario atender apenas sua especialidade
- Registro de prontuario
- Registro de vacinacao
- Historico do animal

## Endpoints principais

Projetos:

- `GET /api/projetos`
- `POST /api/projetos`
- `GET /api/projetos/{id}`
- `POST /api/projetos/{projetoId}/funcionarios/{funcionarioId}`
- `GET /api/projetos/periodo?inicio=2026-05-01&fim=2026-12-31`
- `GET /api/projetos/funcionario/{funcionarioId}`
- `GET /api/funcionarios`
- `POST /api/funcionarios`
- `GET /api/setores`
- `POST /api/setores`

PetCare:

- `GET /api/petcare/tutores`
- `POST /api/petcare/tutores`
- `GET /api/petcare/animais`
- `POST /api/petcare/animais`
- `GET /api/petcare/veterinarios`
- `POST /api/petcare/veterinarios`
- `GET /api/petcare/consultas`
- `POST /api/petcare/consultas`
- `POST /api/petcare/animais/{animalId}/prontuarios`
- `POST /api/petcare/animais/{animalId}/vacinacoes`
- `GET /api/petcare/animais/{animalId}/historico`

## Como testar

```powershell
cd petcare
.\mvnw.cmd test
```

Os testes verificam se o Spring sobe corretamente e se as regras de agenda/especialidade do PetCare funcionam.

## Explicacao curta para apresentar

- `Controller`: recebe a requisicao HTTP e chama o service.
- `Service`: concentra as regras de negocio e validacoes.
- `Repository`: conversa com o banco usando Spring Data JPA.
- `Model`: representa as tabelas do banco.
- `DTO`: representa os dados que chegam nas requisicoes.
- `ExceptionHandler`: transforma erros do sistema em respostas HTTP claras.
# Ac2BackEnd
