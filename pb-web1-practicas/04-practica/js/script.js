const inputTexto = document.getElementById("inputTexto");
const paisTexto = document.getElementById("paisTexto");

inputTexto.addEventListener("input", () => {
    paisTexto.textContent = inputTexto.value;
});

const radios = document.getElementsByName("color");
const articlePais = document.getElementById("articuloDos");

radios.forEach(radio => {
    radio.addEventListener("change", () => {
        articlePais.style.backgroundColor = radio.value;
    });
});

const alineacion = document.getElementById("alineacion");

alineacion.addEventListener("change", () => {
    paisTexto.style.textAlign = alineacion.value;
});