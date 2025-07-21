const menuToggle = document.getElementById("menu-toggle");
const sidebar = document.querySelector(".barra-naveg-esquerda");

const token = sessionStorage.getItem("tokenUsuario");

if (!token) {
    console.log("Tentativa de recuperar publicacoes sem token")
    window.location.href = "login.html";
} else {
    fetch("/ListaPublicacoes", {
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
            var publicacoes = data.publicacoes;

            const containerPublicacoes = document.getElementById("posts");

            publicacoes.forEach(publicacao => {
                var titulo = publicacao.titulo;
                var conteudo = publicacao.conteudo;
                var loginUsuario = publicacao.loginUsuario;
                var tempo = '? minutos atras';
                var visualizacoes = -1;
                var categorias = "nenhuma";

                var publicacaoHTML = document.createElement('article');
                publicacaoHTML.className = 'post-card';
                publicacaoHTML.innerHTML = `
                <header class="post-header">
                    <div class="post-user-info">
                        <div class="post-avatar">
                            <i class="fa-regular fa-user"></i>
                        </div>
                        <div class="post-user-details">
                            <span class="post-user-name">${loginUsuario}</span>
                            <span class="post-timestamp">${tempo}</span>
                        </div>
                    </div>
                    <button class="post-menu-button">
                        <i class="fa-solid fa-ellipsis-vertical"></i>
                    </button>
                </header>

                <div class="post-body">
                    <h3>${titulo}</h3>
                    <p>${conteudo}</p>
                    <!--
                    <div class="post-gallery">
                        <div class="gallery-item-placeholder"></div>
                        <div class="gallery-item-placeholder ver-mais">
                            <span>Ver Mais</span>
                        </div>
                    </div>
                    -->
                </div>

                <footer class="post-footer">
                    <div class="post-meta-info">
                        <div class="post-categories">
                            <span>Categorias:</span>
                            <span class="category-tag">${categorias}</span>
                        </div>
                        <div class="post-actions">
                            <button><i class="fa-regular fa-thumbs-up"></i></button>
                            <button><i class="fa-regular fa-comment"></i></button>
                            <div class="post-views">
                                <i class="fa-regular fa-eye"></i>
                                <span>${visualizacoes} Visualizações</span>
                            </div>
                        </div>
                    </div>
                    <button class="post-save-button">
                        <i class="fa-regular fa-bookmark"></i>
                    </button>
                </footer>`;

                containerPublicacoes.appendChild(publicacaoHTML);
            });
        }
    })
    .catch(error => {
        console.error("Erro ao buscar dados das publicações:", error);
        alert("Erro ao carregar dados das publicações.");
    });
}

menuToggle.addEventListener("click", () => {
    sidebar.classList.toggle("open");
});

// vai fechar o menu se clicar em algum link dentro dele
sidebar.addEventListener("click", e => {
    if (e.target.classList.contains("barra-naveg-item")) {
        sidebar.classList.remove("open");
    }
});

document.addEventListener('DOMContentLoaded', () => {
    console.log("ConectaUFC carregado!");

    const tabs = document.querySelectorAll('.tab-button');

    tabs.forEach(clickedTab => {
        clickedTab.addEventListener('click', () => {
            tabs.forEach(tab => {
                tab.classList.remove('active');
            });

            clickedTab.classList.add('active');
        });
    });

    // botão de publicar
    const btnSubmit = document.querySelector('.btn-submit');
    if (btnSubmit) {
        btnSubmit.addEventListener('click', () => {
            const textarea = document.querySelector('.post-input-area textarea');
            if (textarea.value.trim() !== '') {
                alert('Sua publicação seria enviada: ' + textarea.value.trim());
                textarea.value = ''; // limpa o textarea
                // integrar com um backend para realmente enviar o post
            } else {
                alert('Por favor, escreva algo para publicar!');
            }
        });
    }

    // ação de curtir/comentar (visual)
    const actionButtons = document.querySelectorAll('.action-btn');
    actionButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            const icon = button.querySelector('i');
            const currentCount = parseInt(button.textContent.replace(/\D/g, '')) || 0; // Pega o número

            if (icon.classList.contains('fa-heart')) {
                // simula curtir
                if (!icon.classList.contains('liked')) {
                    icon.classList.add('liked');
                    icon.style.color = 'red'; // coração vermelho ao curtir
                    button.innerHTML = `<i class="fas fa-heart liked"></i> ${currentCount + 1}`;
                } else {
                    icon.classList.remove('liked');
                    icon.style.color = ''; // volta à cor padrão
                    button.innerHTML = `<i class="fas fa-heart"></i> ${currentCount - 1}`;
                }
            } else if (icon.classList.contains('fa-comment')) {
                alert('Você clicou em Comentar!');
                // abrir uma caixa de comentários
            } else if (icon.classList.contains('fa-share')) {
                alert('Você clicou em Compartilhar!');
                // implementar funcionalidade de compartilhamento
            }
        });
    });
});

