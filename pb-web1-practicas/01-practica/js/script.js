const selectUniversidad = document.getElementById("select-universidad");
const texto = document.getElementById("texto-universidad");

selectUniversidad.addEventListener("change", () => {

    if (selectUniversidad.value !== "") {
        texto.textContent = selectUniversidad.value;
    }
});

const color = document.getElementById("select-color");
const articulo = document.getElementById("articulo-color");

color.addEventListener("change", () => {
    articulo.style.backgroundColor = color.value;
});

const radios = document.getElementsByName("colorTexto");

radios.forEach(radio => {
    radio.addEventListener("change", () => {
        texto.style.color = radio.value;
    });
});