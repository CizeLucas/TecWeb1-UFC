// const pageTitle = document.title;

//     // Atualiza o placeholder do input com o título da página
// const searchInput = document.getElementById("searchInput");
// searchInput.placeholder = `Pesquisar em ${pageTitle}`;


const toggleBtn = document.getElementById("toggleSidebar");
const sidebar = document.getElementById("sidebar");

let isSidebarOpen = true;

toggleBtn.addEventListener("click", () => {
if (isSidebarOpen) {
    sidebar.style.display = "none";
} else {
    sidebar.style.display = "flex";
}
      isSidebarOpen = !isSidebarOpen;
});