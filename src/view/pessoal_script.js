const usuario = sessionStorage.getItem("usuarioLogado");
if (!usuario) {
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
        document.getElementById("loginUsuario").textContent = data.login;
        document.getElementById("textoUsuario").textContent = data.texto;
        document.getElementById("numeroUsuario").value = data.numero;
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao carregar dados do usuário.");
    });
}

document.getElementById("text_box").addEventListener("submit", function(event) {
    event.preventDefault();

    console.log("botão de salvar texto pressionado");

    const texto = document.getElementById("textoUsuario").value;

    console.log("texto a ser salvo: " + texto);

    fetch("/SalvaDadosUsuario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ login: usuario, data_to_save: "text", text: texto })
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao salvar dados do usuário.");
    });
})

document.getElementById("number_box").addEventListener("submit", function(event) {
    event.preventDefault();

    console.log("botão de salvar numero pressionado");

    const numero = document.getElementById("numeroUsuario").value;

    console.log("numero a ser salvo: " + numero);

    fetch("/SalvaDadosUsuario", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ login: usuario, data_to_save: "number", number: numero })
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao salvar dados do usuário.");
    });
})