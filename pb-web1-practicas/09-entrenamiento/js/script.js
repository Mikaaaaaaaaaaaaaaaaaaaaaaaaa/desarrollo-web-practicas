const ingresar_universidad = document.getElementById("ingresar_universidad");
const texto_evento = document.getElementById("texto-evento");

const color_fondo = document.getElementById("colorFondo");
const article_evento = document.getElementById("article-evento");

const alineaciones = document.querySelectorAll(".alineacion button");

ingresar_universidad.addEventListener("input", () => {
    texto_evento.textContent = ingresar_universidad.value;
});

color_fondo.addEventListener("change", () => {
    article_evento.style.backgroundColor = color_fondo.value;
});

alineaciones.forEach(boton => {
    boton.addEventListener("click", () => {
        texto_evento.style.textAlign = boton.value;
    });
});