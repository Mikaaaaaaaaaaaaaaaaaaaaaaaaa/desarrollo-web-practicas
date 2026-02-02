let campoTexto = document.getElementById("campo_texto");
let campoColor = document.getElementById("campo_color");
let cuadroTexto = document.getElementById("cuadro_texto");

campoTexto.addEventListener('keyup', function() {
    cuadroTexto.innerHTML = campoTexto.value;
});

campoColor.addEventListener('change', function(){
    cuadroTexto.style.backgroundColor = campoColor.value;
});
