const usuario = sessionStorage.getItem("usuarioLogado");

if (!usuario) {
    // se não tiver usuário logado, volta para pagina de login
    window.location.href = "login.html";
} else {
    fetch("/RetornaDadosUsuario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ login: usuario })
    })
    .then(response => response.json())
    .then(data => {
        // se o usuário logado não for admin, volta para pagina de login
        if (!data.admin) {
            window.location.href = "login.html";
        }
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao carregar dados do usuário.");
    });
}

fetch("/ListaUsuarios")
.then(response => response.json())
.then(data => {
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
})
.catch(error => {
    alert("Erro ao carregar usuários.");
    console.error(error);
});
