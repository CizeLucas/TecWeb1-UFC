document.getElementById("register_box").addEventListener("submit", function (event) {
    event.preventDefault();

    const login = document.getElementById("login").value;
    const senha1 = document.getElementById("senha1").value;
    const senha2 = document.getElementById("senha2").value;

    fetch("/VerificaRegistro", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            login: login,
            senha1: senha1,
            senha2: senha2
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log("login_existe: " + data.login_existe);
        console.log("senha_correta: " + data.senhas_iguais);

        if (data.login_existe) {
            alert("Login já existe");
        } else if (!data.senhas_iguais) {
            alert("Senhas não são iguais");
        } else {
            window.location.href = "login.html";
        }

    })
    .catch(error => {
        console.error("Erro ao processar login:", error);
        alert("Erro ao tentar fazer registro.");
    })
});
