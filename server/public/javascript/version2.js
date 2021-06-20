const csrfToken = $("#csrfToken").val()
const loginRoute = $("#loginRoute").val()
const validateRoute = $("#validateRoutePost").val()
const createRoute = $("#createRoute").val()
const deleteTaskRoute = $("#deleteTaskRoute").val()
const addTaskRoute = $("#addTaskRoute").val()

$("#contents").load(loginRoute)

function login() {
    const username = $("#loginName").val()
    const password = $("#loginPassword").val()

    console.log(username + " Tried to login with password "  + password)

    $.post(validateRoute,
        {username, password, csrfToken},
        data => {
            $("#contents").html(data)
        }
    )
}

function createUser() {
    const username = $("#createUsername").val()
    const password = $("#createPassword").val()

    console.log("Try to create user " + username + " with password " + password)

    $.post(createRoute,
        {username, password, csrfToken},
            data => {
                $("#contents").html(data)
        }
    )
}

function deleteTask(index) {

console.log("Try delete task number " + index)

    $.post(deleteTaskRoute,
        {index, csrfToken},
        data => {
            $("#contents").html(data)
        }
    )
}

function addTask() {

    const newTask = $("#newTask").val();
    console.log("Try add task: " + newTask)

    $.post(addTaskRoute,
        {newTask, csrfToken},
        data => {
            $("#contents").html(data)
        }
    )
}