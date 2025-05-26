const usuario = sessionStorage.getItem("usuarioLogado");
if (!usuario) {
    window.location.href = "login.html";
} else {
    fetch("/DadosUsuario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ login: usuario })
    })
    .then(response => response.json())
    .then(data => {
        // TODO: colocar dados do usuário na pagina
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao carregar dados do usuário.");
    });
}