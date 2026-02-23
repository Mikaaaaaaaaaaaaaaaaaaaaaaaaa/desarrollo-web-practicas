const id_seleccionar_universidad = document.getElementById("id_seleccionar_universidad");
const id_parrafo = document.getElementById("id_parrafo");

id_seleccionar_universidad.addEventListener("change", () => {
    id_parrafo.textContent = id_seleccionar_universidad.value;
});

const id_seleccionar_fondo = document.getElementById("id_seleccionar_fondo");
const id_article_uno = document.getElementById("id_article_uno");

id_seleccionar_fondo.addEventListener("change", () => {
    id_article_uno.style.backgroundColor = id_seleccionar_fondo.value;
});

const div_seleccionar_color = document.querySelectorAll("#div_seleccionar_color input");

div_seleccionar_color.forEach(radio => {
    radio.addEventListener("change", () => {
        id_article_uno.style.backgroundColor = radio.value;
    });
});