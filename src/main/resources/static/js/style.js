function getAllUsers() {
    $.ajax({url: "http://localhost:8080/api/users/"}).then(function (userList) {
        var userListHtml = document.getElementById('userListHtml');
        for (var i = userList.length - 1; i >= 0; i--) {
            addUserPage(userListHtml, userList[i]);
        }
    });
}

function getAllRoles() {
    $.ajax({url: "http://localhost:8080/api/roles/"}).then(function (roleList) {
        var roleListHtml = document.getElementById('roleListHtml');
        for (var i = roleList.length - 1; i >= 0; i--) {
            addRolePage(roleListHtml, roleList[i]);
        }
    });
}

$(document).ready(function () {
    getAllUsers();
    getAllRoles();
});

function addUserPage(userListHtml, user) {
    userListHtml.insertAdjacentHTML('afterBegin', strAddUser(user));
}

function addRolePage(userListHtml, role) {
    userListHtml.insertAdjacentHTML('afterBegin', strAddRole(role));
}

function strAddUser(user) {
    return "<tr id='userList'" + user.id + ">" +
        "<td>" + user.id + "</td>" +
        "<td>" + changeRoleToStr(user.roles) + "</td>" +
        "<td>" + user.login + "</td>" +
        "<td>" + user.password + "</td>" +
        "<td>" + user.email + "</td>" +
        "<td><button id='" + user.id + "' class='btn btn-info' type='button' >Edit</button></td>" +
        "<td><button id='" + user.id + "' onclick='deleteUser(this.id);' class='btn btn-danger'  type='button'>Delete</button></td></tr>";
}

function updateUser(id) {
    $("#custom-close").modal();
}


function strAddRole(role) {
    // return "<option id='roleListHtml'" + role.id + ">" + role.role + "</option";
    return "<option id='roleListHtml'" + role.id + " value='" + role.id + "'>" + role.role + "</option";
    // return "<option id='roleListHtml'" + role.id + " value='roles\":[{\"id\":1,\"role\":\"ROLE_USER\"}]'>" + role.role + "</option";
}

function deleteUser(id) {
    $.ajax({
        url: 'api/' + id,
        type: "DELETE"
    }).done(function () {
        clearTable();
        getAllUsers();
    });
}

function changeRoleToStr(roles) {
    var strRole = '';
    for (var i = 0; i < roles.length; i++) {
        strRole = strRole + roles[i].role + ',';
    }
    return strRole.substring(0, strRole.length - 1);
}


function addUser() {
    var sendData = {};
    // $('form#formAddUser').each( function (index) {
    //     if(index === 0) {
            sendData.email = $( '#addUserEmail' ).val();
        // } else if(index === 1) {
            sendData.login = $( '#addUserLogin').val();
        // } else if(index === 2) {
            sendData.password = $( '#addUserPassword').val();
        // }
    // });
    sendData.roles = [];
    $('#roleListHtml option:selected').each( function () {
    // $('#roleListHtml select option:selected').each( function () {
        sendData.roles.push({
            id: $(this).val()
            // name: $('#roleListHtml1').val()
        });
    });

    $.ajax({
        url: 'api/',
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(sendData)
    }).done(function () {
        clearFormAddUser();
        clearTable();
        getAllUsers();
        window.location.href = "./users";
    });
}

// function addUser() {
//     var obj = $("form#formAddUser").serializeToJSON({
//         // options here
//     });
//     var data = JSON.stringify(obj);
//     $.ajax({
//         url: 'api/',
//         type: "POST",
//         contentType: "application/json",
//         data: data
//     }).done(function () {
//         clearFormAddUser();
//         clearTable();
//         getAllUsers();
//         window.location.href = "./users";
//     });
// }

function clearTable() {
    $("#userListHtml").empty();
}

function clearFormAddUser() {
    $('#formAddUser input').val('');
}
