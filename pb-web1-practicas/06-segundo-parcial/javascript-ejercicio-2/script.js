const paises = ["Argentina", "Brasil", "Uruguay"];
const colores = ["Rojo", "Azul", "Verde"];
const mascotas = ["Perro", "Gato", "Pececito"];

function actualizarLista2() {
	const lista1 = document.getElementById("lista1");
	const lista2 = document.getElementById("lista2");
	const categoria = lista1.value;

	lista2.innerHTML = '<option value="">--Seleccione una opción--</option>';

	if (categoria) {
		switch (categoria) {
			case "paises":
				paises.forEach(item => {
					const option = document.createElement("option");
					option.value = item.toLowerCase();
					option.textContent = item;
					lista2.appendChild(option);
				});
				break;

			case "colores":
				colores.forEach(item => {
					const option = document.createElement("option");
					option.value = item.toLowerCase();
					option.textContent = item;
					lista2.appendChild(option);
				});
				break;

			case "mascotas":
				mascotas.forEach(item => {
					const option = document.createElement("option");
					option.value = item.toLowerCase();
					option.textContent = item;
					lista2.appendChild(option);
				});
				break;
		}
	}
}

function mostrarImagen() {
	const lista2 = document.getElementById("lista2");
	const itemSeleccionado = lista2.value;
	const imagenDiv = document.getElementById("imagen");

	if (itemSeleccionado) {
		imagenDiv.innerHTML = "<img src='" + itemSeleccionado + ".png' alt='" + itemSeleccionado + "'>";
	} else {
		imagenDiv.innerHTML = '';
	}
}