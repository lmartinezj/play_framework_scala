const csrfToken = $("#csrfToken").val()
const loginRoute = $("#loginRoute").val()
const validateRoute = $("#validateRoutePost").val()

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
    $("#contents").load("/createUser2?username=" + username + "&password=" + password)
}

function deleteTask(index) {
    $("#contents").load("/deleteTask2?index=" + index)
}

function addTask() {
    const newTask = $("#newTask").val();
    $("#contents").load("/addTask2?newTask=" + encodeURIComponent(newTask))
}