const token = sessionStorage.getItem("tokenUsuario");

if (!token) {
    console.log("Tentativa de entrar em pessoal.html sem token")
    window.location.href = "login.html";
} else {
    fetch("/RetornaDadosUsuario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ token: token })
    })
    .then(response => response.json())
    .then(data => {
        if (!data.authentication) {
            alert("token de validação enviado não pertence a nenhuma sessão ativa, tentativa de invasão detectada");
            window.location.href = "login.html";
        } else if (!data.admin) {
            // se o usuário logado não for admin, volta para pagina de login
            window.location.href = "login.html"; 
        }
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao carregar dados do usuário.");
    });
}

fetch("/ListaUsuarios", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({ token: token })
})
.then(response => response.json())
.then(data => {
    if (!data.authentication) {
        alert("token de validação inválido, sua sessão pode ter terminado ou você não tem acesso a esses dados, retornando a pagina de login");
        window.location.href = "login.html"; // volta para pagina de login
    } else {
        const corpo_tabela = document.getElementById("tabelaUsuarios").querySelector("tbody");
        data.array.forEach(usuario => {
            const linha_tr = document.createElement("tr");
            linha_tr.innerHTML = `
                <td>${usuario.login}</td>
                <td>${usuario.texto}</td>
                <td>${usuario.numero}</td>
                <td>${usuario.admin ? "Sim" : "Não"}</td>
                <td>${usuario.senha_hash}</td>
            `
            corpo_tabela.appendChild(linha_tr);
        });
    }
})
.catch(error => {
    alert("Erro ao carregar usuários.");
    console.error(error);
});
