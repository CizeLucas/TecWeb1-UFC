document.getElementById("register_box").addEventListener("submit", function (event) {
    event.preventDefault();

    const login = document.getElementById("login").value;
    const senha1 = document.getElementById("senha1").value;
    const senha2 = document.getElementById("senha2").value;

    if (senha1 !== senha2) {
        alert("As senhas não são iguais!");
        return;
    }

    fetch("/VerificaRegistro", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            login: login,
            senha: senha1
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log("login_existe: " + data.login_existe);

        if (data.login_existe) {
            alert("Login já existe");
        } else {
            window.location.href = "login.html";
        }

    })
    .catch(error => {
        console.error("Erro ao processar login:", error);
        alert("Erro ao tentar fazer registro.");
    })
});
