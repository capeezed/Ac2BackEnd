const toast = document.querySelector("#toast");

function showToast(message) {
    toast.textContent = message;
    toast.style.display = "block";
    setTimeout(() => {
        toast.style.display = "none";
    }, 2600);
}

async function request(url, options = {}) {
    const response = await fetch(url, {
        headers: {"Content-Type": "application/json"},
        ...options
    });
    const data = await response.json();
    if (!response.ok) {
        throw new Error(data.erro || data.error || "Erro na requisicao");
    }
    return data;
}

function formData(form) {
    return Object.fromEntries(new FormData(form).entries());
}

async function carregarProjetos() {
    const projetos = await request("/api/projetos");
    const container = document.querySelector("#projetos");
    container.innerHTML = projetos.map((projeto) => `
        <article class="item">
            <strong>#${projeto.id} - ${projeto.nome}</strong>
            <div class="meta">${projeto.dataInicio || ""} ate ${projeto.dataFim || "em aberto"} | ${projeto.status || ""}</div>
            <div>${projeto.descricao || ""}</div>
            <div class="meta">Funcionarios: ${(projeto.funcionarios || []).map((f) => f.nome).join(", ") || "nenhum"}</div>
        </article>
    `).join("");
}

document.querySelector("#btnProjetos").addEventListener("click", async () => {
    try {
        await carregarProjetos();
        showToast("Projetos atualizados");
    } catch (error) {
        showToast(error.message);
    }
});

document.querySelector("#formProjeto").addEventListener("submit", async (event) => {
    event.preventDefault();
    const values = formData(event.target);
    const payload = {
        nome: values.nome,
        descricao: values.descricao,
        dataInicio: values.dataInicio,
        dataFim: values.dataFim || null,
        orcamento: values.orcamento || null
    };

    try {
        await request("/api/projetos", {
            method: "POST",
            body: JSON.stringify(payload)
        });
        event.target.reset();
        await carregarProjetos();
        showToast("Projeto cadastrado");
    } catch (error) {
        showToast(error.message);
    }
});

document.querySelector("#formVinculo").addEventListener("submit", async (event) => {
    event.preventDefault();
    const values = formData(event.target);
    const output = document.querySelector("#resultadoVinculo");

    try {
        const data = await request(`/api/projetos/${values.projetoId}/funcionarios/${values.funcionarioId}`, {
            method: "POST"
        });
        output.textContent = JSON.stringify(data, null, 2);
        await carregarProjetos();
        showToast("Funcionario vinculado");
    } catch (error) {
        output.textContent = error.message;
        showToast(error.message);
    }
});

async function carregarPetcare() {
    const [animais, veterinarios, consultas] = await Promise.all([
        request("/api/petcare/animais"),
        request("/api/petcare/veterinarios"),
        request("/api/petcare/consultas")
    ]);
    document.querySelector("#petcare").textContent = JSON.stringify({animais, veterinarios, consultas}, null, 2);
}

document.querySelector("#btnPetcare").addEventListener("click", async () => {
    try {
        await carregarPetcare();
        showToast("PetCare carregado");
    } catch (error) {
        showToast(error.message);
    }
});

document.querySelector("#formConsulta").addEventListener("submit", async (event) => {
    event.preventDefault();
    const values = formData(event.target);
    const payload = {
        animalId: Number(values.animalId),
        veterinarioId: Number(values.veterinarioId),
        dataHora: values.dataHora,
        motivo: values.motivo
    };

    try {
        const consulta = await request("/api/petcare/consultas", {
            method: "POST",
            body: JSON.stringify(payload)
        });
        document.querySelector("#petcare").textContent = JSON.stringify(consulta, null, 2);
        showToast("Consulta agendada");
    } catch (error) {
        document.querySelector("#petcare").textContent = error.message;
        showToast(error.message);
    }
});

carregarProjetos().catch((error) => showToast(error.message));
carregarPetcare().catch(() => {});
