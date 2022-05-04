$(document).ready(() => {

    filtro();
    mostrarConexiones();
    mostrarDispositivos();
})

let datosConexiones = {};
let listaHistoricoC = '';
let listaHistoricoD = '';

const filtro = () => {

    let select = document.getElementById('selectFiltro');
    let option = select.value;

    if (option === 'Conexion') {
        document.getElementById('selectHistoricoCon').removeAttribute('hidden', 'hidden');
        document.getElementById('selectHistoricoDis').setAttribute('hidden', 'hidden');

    } else if (option === 'Dispositivo') {
        document.getElementById('selectHistoricoDis').removeAttribute('hidden', 'hidden');
        document.getElementById('selectHistoricoCon').setAttribute('hidden', 'hidden');

    }

}

const mostrarConexiones = async () => {

    try {
        let listaConexiones = '';
    
        const request = await fetch('api/conexion', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
    
        const conexiones = await request.json();
        conexiones.forEach(conexion => {
    
            let selectConexionHTML = `<option id="optionCon${conexion.id}" onclick="historicoC(${conexion.id})" value="${conexion.id}">${conexion.nombre}</option>`;
            listaConexiones += selectConexionHTML;
    
        });
    
        document.querySelector('#selectHistoricoCon option').outerHTML = listaConexiones;
        
    } catch (error) {
        console.log(error);
    }


}

const historicoC = async (id) => {

    // document.getElementById('selectHistoricoCon').removeAttribute('hidden', 'hidden');

    const request = await fetch('api/historico', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const historico = await request.json();


    let encabezadoHTML = `<tr>                            
                                    <th scope="col">Id</th>
                                    <th scope="col">Conexión</th>
                                    <th scope="col">Ip</th>
                                    <th scope="col">MAC</th>                            
                                    <th scope="col">Tipo</th>                            
                              </tr>`;

    historico.forEach(h => {

        if (h.idC === id) {
            console.log('entra');
            let tablaHistoricoCon = `<tr>
                                                <td scope="row">${h.id}</td>
                                                <td>${h.nombre}</td>
                                                <td>${h.ip}</td>
                                                <td>${h.mac}</td>
                                                <td>${h.tipoD}</td>
                                            </tr>`;

            listaHistoricoC += tablaHistoricoCon;
        }

    });

    document.querySelector('#tabla thead').innerHTML = encabezadoHTML;
    document.querySelector('#datos').innerHTML = listaHistoricoC;

}

const mostrarDispositivos = async () => {

    try {

        let listaDispositivos = '';

        const request = await fetch('api/dispositivos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
    
        const dispositivos = await request.json();
        dispositivos.forEach(d => {
    
            let selectDispositivoHTML = `<option id="optionDis${d.id}" onclick="historicoD(${d.id})" value="${d.id}">${d.tipo} - ${d.mac}</option>`;
            listaDispositivos += selectDispositivoHTML;
    
        });
    
        document.querySelector('#selectHistoricoDis option').outerHTML = listaDispositivos;
    } catch (error) {
        console.log(error);
    }

}

const historicoD = async (id) => {

    // document.getElementById('selectHistoricoCon').removeAttribute('hidden', 'hidden');

    const request = await fetch('api/historico', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });

    const historico = await request.json();


    let encabezadoHTML = `<tr>                            
                                    <th scope="col">Id</th>
                                    <th scope="col">Dispositivo(MAC)</th>
                                    <th scope="col">Tipo Dispositivo</th>
                                    <th scope="col">Nombre</th>                            
                                    <th scope="col">Tipo Conexión</th>                            
                                    <th scope="col">Cifrado</th>                            
                              </tr>`;

    historico.forEach(h => {

        if (h.idD === id) {
            console.log('entra');
            let tablaHistoricoDis = `<tr>
                                                <td scope="row">${h.id}</td>
                                                <td>${h.mac}</td>
                                                <td>${h.tipoD}</td>
                                                <td>${h.nombre}</td>
                                                <td>${(h.tipoC) == "1" ? 'LAN' : 'Wifi'}</td>
                                                <td>${(h.cifrado) != "" ? h.cifrado : 'X' }</td>
                                            </tr>`;

            listaHistoricoD += tablaHistoricoDis;
        }

    });

    document.querySelector('#tabla thead').innerHTML = encabezadoHTML;
    document.querySelector('#datos').innerHTML = listaHistoricoD;

}