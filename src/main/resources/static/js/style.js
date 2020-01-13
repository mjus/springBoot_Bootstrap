function getAllUsers() {
    $.ajax({url: "http://localhost:8080/api/users/"}).then(function (userList) {
        var userListHtml = document.getElementById('userListHtml');
        for (var i = userList.length - 1; i >= 0; i--) {
            addUserPage(userListHtml, userList[i]);
        }
    });
}

function getAllRoles() {

}

// $(document).ready(function () {
//     getAllUsers();
//     getAllRoles();
// })

$('#modalButton').click(function (e) {
    var form = $("#modalForm");
    var data = getFormData(form);
    console.log(data)
    $.ajax({
        type: 'PUT',
        url: '/api/users',
        data: data,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            $.ajax({
                url: '/api/users',
                dataType: 'json',
                success: function (data) {
                    reloadTable(data)
                }
            })
        }
    })
})

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};
    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });
    return JSON.stringify(indexed_array);
}

function reloadTable(data) {
    console.log(data)
    var html = '<table class="table table-striped">\n' +
        '                                <thead>\n' +
        '                                <tr>\n' +
        '                                    <th scope="col">#</th>\n' +
        '                                    <th scope="col">Role</th>\n' +
        '                                    <th scope="col">Login</th>\n' +
        '                                    <th scope="col">Password</th>\n' +
        '                                    <th scope="col">Email</th>\n' +
        '                                    <th scope="col">Edit</th>\n' +
        '                                </tr>\n' +
        '                                </thead>\n' +
        '                                <tbody>'
    $.each(data, function (i, value) {
        html += '<tr><th scope="row">' + (i + 1) + '</th><td>' + value.role + '</td><td>' + value.username + '</td><td>' + value.password + '</td><td>' + value.email +
            '</td><td>' + '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#windowModal" data-login="' +
            value.username + '" data-password="' + value.password + '" data-email="' + value.email + '" data-role="' + value.role + '">Edit</button></td></tr>'
    })
    html += '</tbody></table>'
    $('#usertable').html(html)
}

