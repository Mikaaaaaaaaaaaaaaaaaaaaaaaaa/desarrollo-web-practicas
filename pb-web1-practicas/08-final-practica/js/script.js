const titulo_unlam = document.querySelector('#titulo-unlam');
const ingresar_universidad = document.querySelector('#ingresar_universidad');

ingresar_universidad.addEventListener('input', () => {
    titulo_unlam.textContent = ingresar_universidad.value;
});

const seleccionar_color = document.querySelectorAll('button');
const article_datos_unlam = document.querySelector('.article-datos-unlam');

seleccionar_color.forEach(boton => {
    boton.addEventListener('click', () => {
        article_datos_unlam.style.backgroundColor = boton.value || '';
    });
});