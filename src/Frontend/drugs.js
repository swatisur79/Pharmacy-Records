const socket = io.connect("http://localhost:8080", {transports: ['websocket']});

socket.on('all_tasks', displayTasks);
socket.on('message', displayMessage);

function displayMessage(newMessage) {
    println(1)
}

function displayTasks(tasksJSON) {
    const tasks = JSON.parse(tasksJSON);
    let formattedTasks = "";
    for (const task of tasks) {
        formattedTasks += "<hr/>";
        formattedTasks += "<b>" + task['id'] + "</b> - " + task['name'] + "</b> - "+ task['qty']+"</b> - " + task['exp'] +"</b> - " + task['rack'] +"</b> - " + task['ac'] +"</b> - " + task['cost'] "<br/>";
    }
    document.getElementById("search").innerHTML = formattedTasks;
}


function addDrug() {
    let id = document.getElementById("id").value;
    let name = document.getElementById("name").value;
    let qty= document.getElementById("qty").value;
    let exp= document.getElementById("exp").value;
    let ac= document.getElementById("ac").value;
    let rack= document.getElementById("rack").value;
    let cost= document.getElementById("cost").value;
    socket.emit("add_drug", JSON.stringify({"id": id, "name": name, "qty":qty,"exp":exp,"ac":ac,"rack":rack,"cost":cost}))

    document.getElementById("id").value = "";
    document.getElementById("name").value = "";
    document.getElementById("qty").value="";
    document.getElementById("exp").value="";
    document.getElementById("ac").value="";
    document.getElementById("rack").value="";
    document.getElementById("cost").value="";
}

function searchDrug() {
let id= document.getElementById("id").value;
    socket.emit("search_drug", id);
}

function deleteDrug(id){
let id= document.getElementById("id").value;
socket.emit("delete_drug",id);
}

//function submitUsername() {
//    enteredUsername = document.getElementById("username").value;
////    if (enteredUsername !== "") {
////        username = enteredUsername;
//        document.location.href = "navigate.html";
////    }
//}