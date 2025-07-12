
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