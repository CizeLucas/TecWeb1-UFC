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
            alert("token de validação inválido, sua sessão pode ter terminado ou você não tem acesso a esses dados, retornando a pagina de login");
            window.location.href = "login.html"; // volta para pagina de login
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
