document.getElementById("login").addEventListener("submit", function (event) {
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
})