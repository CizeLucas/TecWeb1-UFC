const token = sessionStorage.getItem("tokenUsuario");

const admin_link = document.getElementById("admin_link");
admin_link.style.pointerEvents = "none";
admin_link.style.opacity = "0.5";
admin_link.title = "Apenas para admin";

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
        } else {
            document.getElementById("loginUsuario").textContent = data.login;
            document.getElementById("textoUsuario").textContent = data.texto;
            document.getElementById("numeroUsuario").value = data.numero;

            if (data.admin) {
                admin_link.style.pointerEvents = "auto";
                admin_link.style.opacity = "1";
                admin_link.title = "";
            }
        }
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