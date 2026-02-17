const input = document.getElementById("ingresar_universidad");
const titulo = document.getElementById("titulo-a-cambiar");
const button_celeste = document.getElementById("button_celeste");
const button_violeta = document.getElementById("button_violeta");
const button_orange = document.getElementById("button_orange");
const button_restablecer = document.getElementById("button_restablecer");
const article_datos_unlam = document.getElementById("article_datos_unlam");

input.addEventListener("input", () => {
    if (input.value.length > 7) {
        titulo.textContent = "Máximo 7 caracteres.";
        titulo.style.textAlign = "center"
        titulo.style.color = "red";
        return;
    }

    titulo.textContent = input.value || "UNLAM";
    titulo.style.color = "yellow";
    titulo.style.textShadow = "0px 1px 0px rgba(0, 0, 0, 0.1)";
});

button_celeste.addEventListener("click", () => {
    article_datos_unlam.style.backgroundColor = "lightskyblue";
    article_datos_unlam.style.color = "white";
});

button_violeta.addEventListener("click", () => {
    article_datos_unlam.style.backgroundColor = "violet";
    article_datos_unlam.style.color = "white";
});

button_orange.addEventListener("click", () => {
    article_datos_unlam.style.backgroundColor = "orange";
    article_datos_unlam.style.color = "white";
});

button_restablecer.addEventListener("click", () => {
    article_datos_unlam.style.backgroundColor = "white";
    article_datos_unlam.style.color = "black";
    titulo.textContent = "UNLAM";
});