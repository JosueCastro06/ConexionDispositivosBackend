$(document).ready(() => {

    datosIn();
    cargarDispositivos();
    mostrarConexiones();
    mostrarConexionesEd();
    extraerIdsConexion();
})

let datos = {};
let datosEdit = {};

let listaDispositivos = '';
let listaConexiones = '';
let listaConexionesEd = '';

localStorage.setItem('ids', JSON.stringify([]));
localStorage.setItem('idsHis', JSON.stringify([1]));
localStorage.setItem('repMac', JSON.stringify([]));

const cargarDispositivos = async () => {

    try {

        const request = await fetch('api/dispositivos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

        const dispositivos = await request.json();

        dispositivos.forEach(dispositivo => {

            let btnEliminar = `<a href="#" onclick="eliminarDispositivo(${dispositivo.id})" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>`;
            let btnEditar = `<a id="btnEd${dispositivo.id}" data-bs-toggle="modal" data-bs-target="#editModal" onclick="datosEd(${dispositivo.id})" class="btn btn-warning btn-circle btn-sm"><i class="fas fa-pen"></i></a>`;

            let dispositivoHTML = `<tr>
                                        <td scope="row">${dispositivo.id}</td>
                                        <td id="conectado">${(dispositivo.conectado) == 1 ? 'Si' : 'No'}</td>
                                        <td id="ip">${dispositivo.ip}</td>
                                        <td id="mac">${(dispositivo.mac)}</td>
                                        <td id="marca">${(dispositivo.marca)}</td>
                                        <td id="tipo">${dispositivo.tipo}</td>
                                        <td id="conexion">${dispositivo.conexion.nombre}</td>
                                        <td>
                                            ${btnEliminar}
                                            ${btnEditar}                                
                                        </td>
                                    </tr>`;

            listaDispositivos += dispositivoHTML;

        });

        document.querySelector('#dispositivos tbody').outerHTML = listaDispositivos;

    } catch (error) {
        let alerta = `<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
                        <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                        </symbol>
                        <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
                        </symbol>
                        <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                        </symbol>
                    </svg>
                    
                    <div class="alert alert-primary d-flex align-items-center mt-5" role="alert">
                        <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Info:"><use xlink:href="#info-fill"/></svg>
                        <div>
                            No hay dispositivos registrados
                        </div>
                    </div>`;



        document.getElementById("alerta").innerHTML = alerta;
        setTimeout(() => {
            document.getElementById("alerta").remove();
        }, 5000);
    }

}

const extraerIdsConexion = async () => {
    try {

        let id = [];
        let idTip = 0;
        let idsHis = [];
        let repMac = [];
        const request = await fetch('api/dispositivos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });

        const dispositivos = await request.json();

        dispositivos.forEach(d => {

            tipoMinuscula = d.tipo.toLowerCase();
            if (tipoMinuscula === 'smartphone' || tipoMinuscula === 'celular' || tipoMinuscula === 'tablet' ) {
                id = [...id, d.conexion.id];
            }else if (tipoMinuscula !== 'smartphone' || tipoMinuscula !== 'celular' || tipoMinuscula !== 'tablet' ) {
                idTip = 1;
            }
            idsHis = [d.id];
            repMac = [...repMac, d.mac];
        })

        let triplicados = [];

        const tempArray = [...id].sort();

        for (let i = 0; i < tempArray.length; i++) {
            if (tempArray[i + 1] === tempArray[i] && tempArray[i + 2] === tempArray[i]) {
                triplicados.push(tempArray[i]);
            }
        }

        localStorage.setItem('ids', JSON.stringify(triplicados));
        localStorage.setItem('idsTip', JSON.stringify(idTip));
        localStorage.setItem('idsHis', JSON.stringify(idsHis));
        localStorage.setItem('repMac', JSON.stringify(repMac));

    } catch (error) {
        console.log("No hay dispositivos registrados");
    }
}

const agregarDispositivo = async () => {

    datos.ip = document.getElementById('inIp').value;
    datos.mac = document.getElementById('inMac').value;
    datos.marca = document.getElementById('inMarca').value;
    datos.tipo = document.getElementById('inTipo').value;

    const rMac = JSON.parse(localStorage.getItem('repMac'));

    for (let i = 0; i < rMac.length; i++) {
        if (rMac[i] == document.getElementById('inMac').value) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Dirección MAC no valida',
            })
            return;
        }
    }


    await fetch('api/dispositivos', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });

    const h = JSON.parse(localStorage.getItem('idsHis'));

    // for (let i = 0; i < his.length; i++) {
    await fetch('api/historico', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            'idD': h[0] + 1,
            'idC': datos.conexion.id,
            'cifrado': datos.conexion.cifrado,
            'ip': datos.ip,
            'mac': datos.mac,
            'nombre': datos.conexion.nombre,
            'tipoC': datos.conexion.tipo,
            'tipoD': datos.tipo
        })
    })


    location.reload();

}

const mostrarConexiones = async () => {

    const request = await fetch('api/conexion', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const conexiones = await request.json();
    conexiones.forEach(conexion => {

        let selectConexionHTML = `<option id="optionCon${conexion.id}" onclick="extraerObj(${conexion.id})" value="${conexion.id}">${conexion.nombre}</option>`;
        listaConexiones += selectConexionHTML;

    });

    document.querySelector('#selectConexion option').outerHTML = listaConexiones;

    if (listaConexiones !== "") {
        evaluarIdConexiones();
    }

}

const evaluarIdConexiones = () => {

    try {
        const btn = document.getElementById('btnAgregarD');
        

        btn.addEventListener('click', () => {
            const evalId = JSON.parse(localStorage.getItem('ids'));

            if (evalId.length != 0) {

                for (let i = 0; i < evalId.length; i++) {
                    // console.log(Number(document.getElementById(optionCon+evalId[i]).value));

                    if (evalId[i] === (Number(document.getElementById("optionCon" + evalId[i]).value))) {
                        console.log(document.getElementById('optionCon' + evalId[i]));
                        document.getElementById('optionCon' + evalId[i]).setAttribute('disabled', 'disabled');
                    } else if (evalId[i] === "" ) {
                        document.getElementById('optionCon' + evalId[i]).removeAttribute('disabled', 'disabled');
                    } 

                }

            }
        })
    } catch (error) {
        console.log(error);
    }
}

const extraerObj = async (id) => {

    const request = await fetch(`api/conexion/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const obj = await request.json();
    datos.conexion = obj;
    datosEdit.conexion = obj;

}

const datosIn = () => {

    const conSi = document.getElementById('conSi');
    const conNo = document.getElementById('conNo');

    conSi.addEventListener('click', () => {
        if (conSi.checked) {
            if (conSi.value == 'true') {
                datos.conectado = true;
            }
            document.getElementById('conNo').setAttribute('disabled', 'disabled');
        } else {
            document.getElementById('conNo').removeAttribute('disabled', 'disabled');
        }
    })

    conNo.addEventListener('click', () => {
        if (conNo.checked) {
            if (conNo.value == 'false') {
                datos.conectado = false;
            }
            document.getElementById('conSi').setAttribute('disabled', 'disabled');
        } else {
            document.getElementById('conSi').removeAttribute('disabled', 'disabled');
        }
    })

}


const eliminarDispositivo = (id) => {

    Swal.fire({
        title: 'Estas seguro de eliminar este dispositivo?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si',
        cancelButtonText: 'No'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`api/dispositivos/${id}`, {
                method: 'DELETE'
            })

            const evalId = JSON.parse(localStorage.getItem('ids'));

            if (evalId.length != 0) {

                for (let i = 0; i < evalId.length; i++) {
                    // console.log(Number(document.getElementById(optionCon+evalId[i]).value));
                    if (evalId[i] === Number(id)) {
                        localStorage.setItem('ids', JSON.stringify([]));
                    }
                }
            }

            location.reload();
        }
    })

}


const editarDispositivo = async () => {

    datosEdit.ip = document.getElementById('editIp').value;
    datosEdit.mac = document.getElementById('editMac').value;
    datosEdit.marca = document.getElementById('editMarca').value;
    datosEdit.tipo = document.getElementById('editTipo').value;

    await fetch(`api/dispositivos/${datosEdit.id}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datosEdit)
    });

}

const datosEd = (id) => {

    datosEdit.id = id;
    cargaDatosAnterioresEdit(id);

    const editSi = document.getElementById('editSi');
    const editNo = document.getElementById('editNo');

    editSi.addEventListener('click', () => {
        if (editSi.checked) {
            if (editSi.value == 'true') {
                datosEdit.conectado = true;
            }
            document.getElementById('editNo').setAttribute('disabled', 'disabled');
        } else {
            document.getElementById('editNo').removeAttribute('disabled', 'disabled');
        }
    })

    editNo.addEventListener('click', () => {
        if (editNo.checked) {
            if (editNo.value == 'false') {
                datosEdit.conectado = false;
            }
            document.getElementById('editSi').setAttribute('disabled', 'disabled');
        } else {
            document.getElementById('editSi').removeAttribute('disabled', 'disabled');
        }
    })

    evaluarIdConexionesEd(`btnEd${id}`);

}

const mostrarConexionesEd = async () => {

    const request = await fetch('api/conexion', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const conexiones = await request.json();
    conexiones.forEach(conexion => {

        let selectConexionHTML = `<option id="optionEdCon${conexion.id}" onclick="extraerObj(${conexion.id})" value="${conexion.id}">${conexion.nombre}</option>`;
        listaConexionesEd += selectConexionHTML;

    });

    document.querySelector('#editSelectDispositivo option').outerHTML = listaConexionesEd;

}

const evaluarIdConexionesEd = () => {

    const evalId = JSON.parse(localStorage.getItem('ids'));

    if (evalId.length != 0) {

        for (let i = 0; i < evalId.length; i++) {
            // console.log(Number(document.getElementById(optionCon+evalId[i]).value));
            if (evalId[i] === (Number(document.getElementById("optionEdCon" + evalId[i]).value))) {
                console.log(document.getElementById('optionEdCon' + evalId[i]));
                document.getElementById('optionEdCon' + evalId[i]).setAttribute('disabled', 'disabled');
            } else if (evalId[i] === "") {
                document.getElementById('optionEdCon' + evalId[i]).removeAttribute('disabled', 'disabled');
            }
        }
    }
}

const cargaDatosAnterioresEdit = async (id) => {

    const request = await fetch(`api/dispositivos/${id}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const dispositivosById = await request.json();

    document.getElementById('editIp').value = dispositivosById.ip;
    document.getElementById('editMac').value = dispositivosById.mac;
    document.getElementById('editTipo').value = dispositivosById.tipo;
}
