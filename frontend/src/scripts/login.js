document.getElementById("login_box").addEventListener("submit", function (event) {
    event.preventDefault();
    
    const login = document.getElementById("login").value;
    const senha = document.getElementById("senha").value;

    fetch("/VerificaLogin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            login: login,
            senha: senha
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log("login_existe: " + data.login_existe);
        console.log("senha_correta: " + data.senha_correta);

        if (data.login_existe && data.senha_correta) {
            // alert("Login realizado com sucesso!");

            sessionStorage.setItem("tokenUsuario", data.token);
            window.location.href = "pessoal.html";
            console.log("Token armazenado no sessionStorage:", data.token);
        } else if (data.login_existe && !data.senha_correta) {
            alert("Senha incorreta!");
        } else {
            alert("Usuário não encontrado!");
        }
    })
    .catch(error => {
        console.error("Erro ao processar login:", error);
        alert("Erro ao tentar fazer login.");
    })
});

document.getElementById("create_account_button").addEventListener("click", function (event) {
    window.location.href = "registrar.html"
});