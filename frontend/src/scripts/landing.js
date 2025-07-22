document.getElementById("register_button").addEventListener("click", function (event) {
    window.location.href = "registrar.html"
});

document.getElementById("join_button").addEventListener("click", function (event) {
    window.location.href = "registrar.html"
});

document.getElementById("login_button").addEventListener("click", function (event) {
    window.location.href = "login.html"
});

document.getElementById("explore_now_button").addEventListener("click", function (event) {
    window.location.href = "login.html"
});

document.getElementById("projects_page").addEventListener("click", function (event) {
    window.location.href = "login.html"
});

document.getElementById("classifieds_page").addEventListener("click", function (event) {
    window.location.href = "login.html"
});

document.getElementById("materials_page").addEventListener("click", function (event) {
    window.location.href = "login.html"
});

// Toggle menu open/close
const menuToggle = document.getElementById("menu_toggle");
const navMenu = document.getElementById("user_navigation_container");

menuToggle.addEventListener("click", function (event) {
    event.stopPropagation();
    if (navMenu.classList.contains("navigation-hidden")) {
        navMenu.classList.remove("navigation-hidden");
        navMenu.classList.add("navigation-visible");
        // Add listener to close menu when clicking outside
        document.addEventListener("click", closeMenuOnOutsideClick);
    } else {
        navMenu.classList.remove("navigation-visible");
        navMenu.classList.add("navigation-hidden");
        document.removeEventListener("click", closeMenuOnOutsideClick);
    }
});

function closeMenuOnOutsideClick(event) {
    // If click is outside the menu and not on the toggle button
    if (!navMenu.contains(event.target) && event.target !== menuToggle) {
        navMenu.classList.remove("navigation-visible");
        navMenu.classList.add("navigation-hidden");
        document.removeEventListener("click", closeMenuOnOutsideClick);
    }
}
