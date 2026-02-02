const inputTitulo = document.getElementById("inputTitulo");
const titulo = document.getElementById("texto-titulo");

inputTitulo.addEventListener("input", () => {
    titulo.textContent = inputTitulo.value;
});

const color = document.getElementById("select-color");
const articleUno = document.getElementById("articulo-color");

color.addEventListener("change", () => {
    articleUno.style.backgroundColor = color.value;
});

const radios = document.getElementsByName("alineacion");
const textoTitulo = document.getElementById("texto-titulo");

radios.forEach(radio => {
    radio.addEventListener("change", () => {
        titulo.style.textAlign = radio.value;
    });
});