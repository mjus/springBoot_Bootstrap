// function getAllUsers() {
//     $.ajax({url: "users/"}).then(function (userList) {
//         var userListHtml = document.getElementById('userListHtml');
//         for (var i = userList.length - 1; i >= 0; i--) {
//             addUserPage(userListHtml, userList[i]);
//         }
//     });
// }

function getAllUsers() {
    $.ajax({url: "/users/"}).then(function (userList) {
        var userListHtml = document.getElementById('userListHtml');

        for (var i = userList.length - 1; i >= 0; i--) {
            addUserPage(userListHtml, userList[i]);
        }
    });
}
// function getAllUsers() {
//     $.ajax({
//         url: 'users',
//         type: "GET"
//     }).done(function (data) {
//         // var userListHtml = document.getElementById('userListHtml');
//         // for (var i = userList.length - 1; i >= 0; i--) {
//         //     addUserPage(userListHtml, userList[i]);
//         addUserPage(data);
//     });
// }

function getAllRoles() {
    $.ajax({url: "/roles/"}).then(function (roleList) {
        var addRoleListHtml = document.getElementById('addRoleListHtml');
        for (var i = roleList.length - 1; i >= 0; i--) {
            addRolePage(addRoleListHtml, roleList[i]);
        }
    });
}

function getAllRoles2() {
    $.ajax({url: "/roles/"}).then(function (roleList) {
        var roleListHtmlUpdate = document.getElementById('roleListHtmlUpdate');
        for (var i = roleList.length - 1; i >= 0; i--) {
             addRolePage1(roleListHtmlUpdate, roleList[i]);
        }
    });
}

$(document).ready(function () {
    getAllUsers();
    getAllRoles();
    getAllRoles2();
});

function addUserPage(userListHtml, user) {
    userListHtml.insertAdjacentHTML('afterBegin', strAddUser(user));
}

// function addUserPage(data) {
//     for (var i = 0; i < data.length; i++) {
//         strAddUser(data);
//     }
// }

function addRolePage(userListHtml, role) {
    userListHtml.insertAdjacentHTML('afterBegin', strAddRole(role));
}

function addRolePage1(userListHtml1, role) {
    userListHtml1.insertAdjacentHTML('afterBegin', strAddRole(role));
}

function strAddUser(user) {
    return "<tr id='userList'" + user.id + ">" +
        "<td>" + user.id + "</td>" +
        "<td>" + changeRoleToStr(user.roles) + "</td>" +
        "<td>" + user.login + "</td>" +
        "<td>" + user.password + "</td>" +
        "<td>" + user.email + "</td>" +
        "<td><button id='" + user.id + "' onclick='getUserUpdate(this.id);' class='btn btn-info' type='button'>Edit</button></td>" +
        "<td><button id='" + user.id + "' onclick='deleteUser(this.id);' class='btn btn-danger'  type='button'>Delete</button></td></tr>";
}

function getUserUpdate(id) {
    $.ajax({
        url: 'users/update/' + id,
        type: "GET"
    }).done(function (data) {
        showModalUpdateUser(data);
    });
}

function updateUser() {
    var sendData = {};
    sendData.id = $( '#inputIdUpdate' ).val();
    sendData.email = $( '#inputEmailUpdate' ).val();
    sendData.login = $( '#inputLoginUpdate').val();
    sendData.password = $( '#inputPasswordUpdate').val();
    sendData.roles = [];
    $('#roleListHtmlUpdate option:selected').each( function () {
        sendData.roles.push({
            id: $(this).val()
        });
    });

    $.ajax({
        url: 'users/update/' + sendData.id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(sendData)
    }).done(function () {
        clearFormAddUser();
        clearTable();
        getAllUsers();
        window.location.href = "http://localhost:8080/admin";
    });
}

function showModalUpdateUser(data) {
    $('.input').val('');
    var modal = $('#updateUserModal').modal('show');
    modal.on('shown.bs.modal', function () {
        $('#inputEmailUpdate').focus()
    });
    populate('#formUpdateUserModal', data);
}

function populate(frm, data) {
    var roles = "";
    var flag = false;
    $.each(data, function (keyi, valuei) {
        if (Array.isArray(valuei)) {
            $.each(valuei, function (key, value) {
                if (value.size % 2 !== 0) {
                    if (flag) {
                        roles += ", ";
                    } else {
                        flag = true;
                    }
                    roles += value.role;
                }
            });
            $('[name=' + keyi + ']', frm).val(roles);
        } else {
            $('[name=' + keyi + ']', frm).val(valuei);
        }
    });
}

function strAddRole(role) {
    return "<option id='roleListHtml'" + role.id + " value='" + role.id + "'>" + role.role + "</option";
}

function deleteUser(id) {
    $.ajax({
        url: 'users/' + id,
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
            sendData.email = $( '#addUserEmail' ).val();
            sendData.login = $( '#addUserLogin').val();
            sendData.password = $( '#addUserPassword').val();
    sendData.roles = [];
    $('#addRoleListHtml option:selected').each( function () {
        sendData.roles.push({
            id: $(this).val()
        });
    });

    $.ajax({
        url: 'users/add',
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(sendData)
    }).done(function () {
        clearFormAddUser();
        clearTable();
        getAllUsers();
        window.location.href = "http://localhost:8080/admin";
    });
}

function clearTable() {
    $("#userListHtml").empty();
}

function clearFormAddUser() {
    $('#formAddUser input').val('');
}
