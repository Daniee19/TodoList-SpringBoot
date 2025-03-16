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
    } else {
        output.delete(event.target.value);
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
    for (let x = 0; x < output.size; x++) {

        try {
            let array = [...output];
            let id = array.at(x)
            console.log("tamanio: " + output.size)
            console.log("El id en posicion " + x + " es: " + id);

            let casillasMarcadas = document.querySelectorAll('input[type="checkbox"]:checked');
            casillasMarcadas.forEach(t =>
                t.closest("tr").remove()
            );
            if (document.querySelectorAll('input[type="checkbox"]').length < 1) {
                let mensaje = document.querySelector(".mensaje_no_tareas");
                mensaje.innerHTML = "No hay tareas registradas.";
            }
            let respuesta = await fetch(`http://localhost:7878/tareas/${id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            if (!respuesta.ok) {
                return console.log(`"La respuesta no fue buena de la ruta dada, ${respuesta.status}"`)
            } else {
                console.log("La respuesta fue buena")
            }

            let datos = await respuesta.text(); //Convertir la respuesta json
            console.log('Respuesta del servidor:', datos);

        } catch (error) {
            console.log("Error al obtener los datos", error);
        }
    }
})

// async function consultarLista() {
//     let respuesta = await fetch("http://localhost:7878/tareas", {
//         method: "GET",
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     });
//     if (!respuesta.ok) {
//         console.log("Error al consultar la llamada a consultarLista");
//     } else {
//         console.log("la consulta lista esta bien");
//     }
//     let datos = await respuesta.text(); //Convertir la respuesta json
//     console.log(datos);
// }