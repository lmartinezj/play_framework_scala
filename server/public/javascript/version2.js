
$("#contents").load("/login2")

function login() {
    const username = $("#loginName").val()
    const password = $("#loginPassword").val()
    console.log(username + " Tried to login with password "  + password)
    $("#contents").load("/validateGet2?username=" + username + "&password=" + password)
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