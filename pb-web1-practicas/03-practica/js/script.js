const texto = document.getElementById("texto-pais");
const inputPais = document.getElementById("inputTexto");

inputPais.addEventListener("input", () => {
    texto.textContent = inputPais.value;
});

const selectColor = document.getElementById("selectColor");
const articuloUno = document.getElementById("articuloUno");

selectColor.addEventListener("change", () => {
    articuloUno.style.backgroundColor = selectColor.value;
});

const radios = document.getElementsByName("alineacion");

radios.forEach(radio => {
    radio.addEventListener("change", () => {
        texto.style.textAlign = radio.value;
    });
});