"use strict"
/*
* eliminates all JQuery
*/

const csrfToken = document.getElementById("csrfToken").value
const validateRoute = document.getElementById("validateRoutePost").value
const tasksRoute = document.getElementById("tasksRoute").value
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
        if (data) {
            document.getElementById("login-section").hidden = true;
            document.getElementById("task-section").hidden = false;
            loadTasks();
        } else {
            console.log("FALSE")
        }
    });
}

function loadTasks() {
    const ul = document.getElementById("task-list");
    fetch(tasksRoute).then(res => res.json()).then(tasks => {
        for (const task of tasks) {
            const li = document.createElement("li");
            const text = document.createTextNode(task);
            li.appendChild(text);
            ul.appendChild(li);
        }
    })
}