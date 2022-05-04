$(document).ready(() => {

    datosIn();
    datosEd();
    cargarConexiones();

})

let datos = {};
let datosEdit = {}
let listaConexiones = '';

const cargarConexiones = async () => {

    try {
        const request = await fetch('api/conexion', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

        const conexiones = await request.json();

        conexiones.forEach(conexion => {
            let btnEliminar = `<a href="#" onclick="eliminarConexion(${conexion.id})" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>`;
            let btnEditar = `<a type="button" data-bs-toggle="modal" data-bs-target="#editModal" href="#" onclick="datId(${conexion.id})" class="btn btn-warning btn-circle btn-sm"><i class="fas fa-pen"></i></a>`;

            let conexionHTML = `<tr><td scope="row">${conexion.id}</td>
                                <td id="tipo">${(conexion.tipo) == "1" ? 'LAN' : 'Wifi'}</td>
                                <td id="nombre">${conexion.nombre}</td>
                                <td id="cifrado">${(conexion.cifrado) != "" ? conexion.cifrado : 'X'}</td>
                                <td id="conexion">${conexion.conexion}</td>
                                <td id="contraseña">${conexion.contraseña}</td>
                                <td>
                                    ${btnEliminar}
                                    ${btnEditar}                                
                                </td>
                                </tr>`;
            listaConexiones += conexionHTML;
        });

        document.querySelector('#conexiones tbody').outerHTML = listaConexiones;
    } catch (error) {
        console.log(error);
    }

}

const agregarConexion = async () => {

    datos.nombre = document.getElementById('txtNombre').value;
    datos.conexion = document.getElementById('txtUsuarioCon').value;
    datos.contraseña = document.getElementById('txtPass').value;

    await fetch('api/conexion', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    location.reload();

}

const disabledCheck = () => {

    document.getElementById('checkWep').setAttribute('disabled', 'disabled');
    document.getElementById('checkWPA').setAttribute('disabled', 'disabled');
    document.getElementById('checkWPA2').setAttribute('disabled', 'disabled');
    document.getElementById('checkWifi').setAttribute('disabled', 'disabled');

}

const enableCheck = () => {

    document.getElementById('checkWep').removeAttribute('disabled', 'disabled');
    document.getElementById('checkWPA').removeAttribute('disabled', 'disabled');
    document.getElementById('checkWPA2').removeAttribute('disabled', 'disabled');
    document.getElementById('checkWifi').removeAttribute('disabled', 'disabled');

}

const datosIn = () => {

    const checkLan = document.getElementById('checkLan');
    const checkWifi = document.getElementById('checkWifi');
    const checkWep = document.getElementById('checkWep');
    const checkWPA = document.getElementById('checkWPA');
    const checkWPA2 = document.getElementById('checkWPA2');

    checkLan.addEventListener('click', () => {

        if (checkLan.checked) {
            datos.cifrado = '';
            datos.tipo = checkLan.value;
            disabledCheck();
        } else {
            enableCheck();
        }

    })

    checkWifi.addEventListener('click', () => {

        if (checkWifi.checked) {

            datos.tipo = checkWifi.value;
            document.getElementById('checkLan').setAttribute('disabled', 'disabled');

            checkWep.addEventListener('click', () => {

                if (checkWep.checked) {

                    datos.cifrado = checkWep.value;
                    document.getElementById('checkWPA').setAttribute('disabled', 'disabled');
                    document.getElementById('checkWPA2').setAttribute('disabled', 'disabled');

                } else {

                    document.getElementById('checkWPA').removeAttribute('disabled', 'disabled');
                    document.getElementById('checkWPA2').removeAttribute('disabled', 'disabled');

                }

            })

            checkWPA.addEventListener('click', () => {

                if (checkWPA.checked) {

                    datos.cifrado = checkWPA.value;
                    document.getElementById('checkWep').setAttribute('disabled', 'disabled');
                    document.getElementById('checkWPA2').setAttribute('disabled', 'disabled');

                } else {

                    document.getElementById('checkWep').removeAttribute('disabled', 'disabled');
                    document.getElementById('checkWPA2').removeAttribute('disabled', 'disabled');

                }

            })

            checkWPA2.addEventListener('click', () => {

                if (checkWPA2.checked) {

                    datos.cifrado = checkWPA2.value;
                    document.getElementById('checkWep').setAttribute('disabled', 'disabled');
                    document.getElementById('checkWPA').setAttribute('disabled', 'disabled');

                } else {

                    document.getElementById('checkWep').removeAttribute('disabled', 'disabled');
                    document.getElementById('checkWPA').removeAttribute('disabled', 'disabled');

                }

            })

        } else {
            document.getElementById('checkLan').removeAttribute('disabled', 'disabled');
        }

    })

}

const eliminarConexion = (id) => {

    Swal.fire({
        title: 'Estas seguro de eliminar esta conexión?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si',
        cancelButtonText: 'No'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`api/conexion/${id}`, {
                method: 'DELETE'
            })
            location.reload();
        }
    })

}

const cargaDatosAnterioresEdit = async (id) => {

    const request = await fetch(`api/conexion/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const conexionesById = await request.json();

    document.getElementById('editNombre').value = conexionesById.nombre;
    document.getElementById('editUsuarioCon').value = conexionesById.conexion;
    document.getElementById('editPass').value = conexionesById.contraseña;

}

const editarConexion = async () => {


    datosEdit.nombre = document.getElementById('editNombre').value;
    datosEdit.conexion = document.getElementById('editUsuarioCon').value;
    datosEdit.contraseña = document.getElementById('editPass').value;

    const request = await fetch(`api/conexion`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datosEdit)
    });

    location.reload();

}

const datId = (id) => {
    datosEdit.id = id;
    cargaDatosAnterioresEdit(id);
}

const datosEd = () => {

    const editLan = document.getElementById('editLan');
    const editWifi = document.getElementById('editWifi');
    const editWep = document.getElementById('editWep');
    const editWPA = document.getElementById('editWPA');
    const editWPA2 = document.getElementById('editWPA2');

    editLan.addEventListener('click', () => {

        if (editLan.checked) {
            datosEdit.cifrado = '';
            datosEdit.tipo = editLan.value;
            document.getElementById('editWep').setAttribute('disabled', 'disabled');
            document.getElementById('editWPA').setAttribute('disabled', 'disabled');
            document.getElementById('editWPA2').setAttribute('disabled', 'disabled');
            document.getElementById('editWifi').setAttribute('disabled', 'disabled');
        } else {
            document.getElementById('editWep').removeAttribute('disabled', 'disabled');
            document.getElementById('editWPA').removeAttribute('disabled', 'disabled');
            document.getElementById('editWPA2').removeAttribute('disabled', 'disabled');
            document.getElementById('editWifi').removeAttribute('disabled', 'disabled');
        }

    })

    editWifi.addEventListener('click', () => {

        if (editWifi.checked) {

            datosEdit.tipo = editWifi.value;
            document.getElementById('editLan').setAttribute('disabled', 'disabled');

            editWep.addEventListener('click', () => {

                if (editWep.checked) {

                    datosEdit.cifrado = editWep.value;
                    document.getElementById('editWPA').setAttribute('disabled', 'disabled');
                    document.getElementById('editWPA2').setAttribute('disabled', 'disabled');

                } else {

                    document.getElementById('editWPA').removeAttribute('disabled', 'disabled');
                    document.getElementById('editWPA2').removeAttribute('disabled', 'disabled');

                }

            })

            editWPA.addEventListener('click', () => {

                if (editWPA.checked) {

                    datosEdit.cifrado = editWPA.value;
                    document.getElementById('editWep').setAttribute('disabled', 'disabled');
                    document.getElementById('editWPA2').setAttribute('disabled', 'disabled');

                } else {

                    document.getElementById('editWep').removeAttribute('disabled', 'disabled');
                    document.getElementById('editWPA2').removeAttribute('disabled', 'disabled');

                }

            })

            editWPA2.addEventListener('click', () => {

                if (editWPA2.checked) {

                    datosEdit.cifrado = editWPA2.value;
                    document.getElementById('editWep').setAttribute('disabled', 'disabled');
                    document.getElementById('editWPA').setAttribute('disabled', 'disabled');

                } else {

                    document.getElementById('editWep').removeAttribute('disabled', 'disabled');
                    document.getElementById('editWPA').removeAttribute('disabled', 'disabled');

                }

            })

        } else {
            document.getElementById('editLan').removeAttribute('disabled', 'disabled');
        }

    })

}
