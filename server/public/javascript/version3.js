"use strict"
/*
* eliminates all JQuery
*/

const csrfToken = document.getElementById("csrfToken").value
const validateRoute = document.getElementById("validateRoutePost").value
const createRoute = document.getElementById("createRoute").value
const deleteTaskRoute = document.getElementById("deleteTaskRoute").value
const addTaskRoute = document.getElementById("addTaskRoute").value

function login() {
    const username = document.getElementById("loginName").value
    const password = document.getElementById("loginPassword").value

    fetch(validateRoute, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Csrf-Token': csrfToken
        },
        body: JSON.stringify({ username, password })
    }).then(res => res.json()).then(data => {
        console.log(data);
    });
}