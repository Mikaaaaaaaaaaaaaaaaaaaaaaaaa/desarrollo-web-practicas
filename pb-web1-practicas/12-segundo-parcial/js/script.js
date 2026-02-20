const id_select = document.getElementById("id_select");
const article_dos = document.getElementById("article_dos");

id_select.addEventListener("change", () => {
    article_dos.style.backgroundColor = id_select.value;
});