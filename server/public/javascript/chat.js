

/**
* Code for the WebSocket chat application.
*/

const inputField = document.getElementById("chat-input");
const outputArea = document.getElementById("chat-area");

const socketRoute = document.getElementById("ws-route").value
const socket = new WebSocket(socketRoute.replace("http", "ws"))

inputField.onkeydown = (event) => {
    if(event.key === "Enter") {
        socket.send(inputField.value)
        inputField.value = ''
    }
}

socket.onopen = () => socket.send("New User Connected")