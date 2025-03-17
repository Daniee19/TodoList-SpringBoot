function activar(valorBooleano) {
    console.log("Hello there");
    let div_eliminar = document.querySelector(".eliminarTarea");

    if (valorBooleano === true) {
        div_eliminar.classList.add("visibilidad");
        console.log("ac")
    } else {
        div_eliminar.classList.remove("visibilidad");
        console.log("desc")
    }
}

let output = new Set();

//Cuando se interactúe con algún checkbox, aparecerá el elemento eliminar, si no hay ninguno desaparecerá
let checkboxes = document.querySelectorAll(".cb");
checkboxes.forEach(checkbox => checkbox.addEventListener("change", (event) => {

    //exclusivo del elemento checkbox
    if (event.target.checked) {
        //Agregamos el valor al Set()
        output.add(event.target.value);
        checkbox.closest("tr").classList.add("tachar");
        checkbox.closest("tr").querySelector(".botonEditar").classList.add("desactivar");
    } else {
        output.delete(event.target.value);
        checkbox.closest("tr").classList.remove("tachar");
        checkbox.closest("tr").querySelector(".botonEditar").classList.remove("desactivar");
    }
    //Ver como array
    console.log("SET de los checkboxes seleccionados: ", output);
    console.log("Valor de los checkboxes seleccionados: ", [...output]);
    if (output.size > 0) {
        activar(true);
    } else {
        activar(false);
    }
    // alert(output);
}));

let eliminarTarea = document.getElementById("btnEliminarTarea");
eliminarTarea.addEventListener("click", async function (event) {
    event.preventDefault();
    let casillasMarcadas = document.querySelectorAll('input[type="checkbox"]:checked');
    casillasMarcadas.forEach(t => t.closest("tr").remove());
    if (document.querySelectorAll('input[type="checkbox"]').length < 1) {
        //Quitamos lo oculto
        document.querySelector(".mensaje_no_tareas").classList.remove("hidden");
        document.querySelector(".eliminarTarea").classList.remove("visibilidad");
    }

    /**
     * El output por ser un Set no tiene índices, y solo se manejaría por sus valores, por ende, cuando ponía
     * output.delete(x) se buscaba eliminar el valor 0 y no la posición 0.
     */
    // for (let x = 0; x < output.size; x++) {
    for (let id of [...output]) {
        try {
            let respuesta = await fetch(`http://localhost:7878/tareas/${id}`, {
                method: 'DELETE', headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (!respuesta.ok) {
                return console.log(`"La respuesta no fue buena de la ruta dada, ${respuesta.status}"`)
            } else {
                console.log("La respuesta fue buena");
                output.delete(id);
            }
            let datos = await respuesta.text(); //Convertir la respuesta json
        } catch (error) {
            console.log("Error al obtener los datos", error);
        }
    }
})
//buscar el valor del checkbox seleccionado y eliminarlo del output.