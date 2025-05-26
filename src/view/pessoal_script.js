const usuario = sessionStorage.getItem("usuarioLogado");
if (!usuario) {
    window.location.href = "login.html";
} else {
    fetch("/DadosUsuario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ login: usuario, pedido: "retornarDados" })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("loginUsuario").textContent = data.login;
        document.getElementById("textoUsuario").textContent = data.texto;
        document.getElementById("numeroUsuario").textContent = data.numero;
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao carregar dados do usuário.");
    });
}

document.getElementById("salvarTexto").addEventListener("submit", function(event) {
    event.preventDefault();

    console.log("botão de salvar texto pressionado");

    const texto = getElementById("textoUsuario").textContent;

    console.log("texto a ser salvo: " + texto);

    fetch("/DadosUsuario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ login: usuario, pedido: "salvarTexto", texto: texto })
    })
})