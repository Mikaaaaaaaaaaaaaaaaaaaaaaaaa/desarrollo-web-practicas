const input_for_ingresar_texto = document.getElementById("for_ingresar_texto");
const cambiar_parrafo = document.getElementById("id_cambiar_parrafo");

input_for_ingresar_texto.addEventListener('input', () => {
    if (input_for_ingresar_texto.value.length > 7) {
        cambiar_parrafo.style.fontSize = "16px";
        cambiar_parrafo.textContent = "Error."
        return;
    }

cambiar_parrafo.textContent = input_for_ingresar_texto.value;
});

const article_titulo = document.getElementById("article_titulo");
const select_for_cambiar_color_fondo = document.getElementById("for_cambiar_color_fondo");

select_for_cambiar_color_fondo.addEventListener("change", () => {
    article_titulo.style.backgroundColor = select_for_cambiar_color_fondo.value;
});

const id_div_input_radio = document.querySelectorAll("#id_div_input_radio input");

id_div_input_radio.forEach(radio => {
    radio.addEventListener("change", () => {
        console.log("Se ejecuta", radio.value);
        cambiar_parrafo.style.textAlign = radio.value;
    });
});