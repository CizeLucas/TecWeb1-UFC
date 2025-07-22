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
            alert("token de validação inválido, sua sessão pode ter terminado ou você não tem acesso a esses dados, retornando a pagina de login");
            window.location.href = "login.html"; // volta para pagina de login
        } else {
            document.getElementById("loginUsuario").textContent = data.login;
        }
    })
    .catch(error => {
        console.error("Erro ao buscar dados do usuário:", error);
        alert("Erro ao carregar dados do usuário.");
    });
}
