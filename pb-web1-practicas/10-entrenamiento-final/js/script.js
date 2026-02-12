const notas_label = document.querySelectorAll(".nota");
const textAreas = document.querySelectorAll("textarea");
const nota_final = document.getElementById("nota_final");

notas_label.forEach(select => {
    select.addEventListener("change", calcularNotas);
});

function calcularNotas() {
    let total = 0;

    notas_label.forEach((select, index) => {
        const valor = parseFloat(select.value);
        total += valor;

        const textArea = textAreas[index];

        if (valor === 0) {
            textArea.style.backgroundColor = "pink";
        } else if (valor === 0.5) {
            textArea.style.backgroundColor = "yellow";
        } else if (valor === 1) {
            textArea.style.backgroundColor = "lightgreen";
        } else {
            textArea.style.backgroundColor = "white";
        }
    });

    nota_final.value = total;

    if (total < 4) {
        nota_final.style.backgroundColor = "pink";
    } else if (total >= 4 && total < 7) {
        nota_final.style.backgroundColor = "yellow";
    } else {
        nota_final.style.backgroundColor = "lightgreen";
    }
}