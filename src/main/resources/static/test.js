// CLICK CREATE NEW USER WITH PAGE ADMIN OPEN MODAL

$('#addNewUser').on('click', function () {
    $('#firstname').val('');
    $('#lastname').val('');
    $('#age').val('');
    $('#email').val('');
    $('#passwordModalWindow').attr('placeholder', 'Enter passport');
    $('#buttonEditOrCreatNewUser').html('Add new User').removeClass('btn-danger');
    $('#labelAddnewUserOrEditPage').html('Add new User page');
    $('#formPasswordRepeat').remove();
    $('#newAndEditUserModal').modal();
});

// CLICK CREATE NEW USER WITH PAGE LOGIN OPEN MODAL

$('#register').on('click', () => {
    $('#firstname').val('');
    $('#lastname').val('');
    $('#age').val('');
    $('#email').val();
    $('#passwordModalWindow').attr('placeholder', 'Enter passport');
    $('#buttonEditOrCreatNewUser').html('Register')
    $('#labelAddnewUserOrEditPage').html('Registration page')
    $('#newAndEditUserModal').modal();
});

// CLICK DELETE USER OPEN MODAL

function deleteUser(event) {
    let href = $(event).attr('id');
    $.get(href, function (user) {
        $('#id').val(user.id);
        $('#firstname').val(user.firstname);
        $('#lastname').val(user.lastname);
        $('#age').val(user.age);
        $('#email').val(user.email);
        $('#passwordModalWindow').removeAttr('placeholder');
        $('#formPasswordRepeat').remove();
        $('#buttonEditOrCreatNewUser').html('Delete User').addClass('btn-danger');
        $('#labelAddnewUserOrEditPage').html('Delete User page');
    });
    $('#newAndEditUserModal').modal();
}

// CLICK EDIT USER OPEN MODAL

function editUser(event) {
    let href = $(event).attr('id');
    $.get(href, function (user) {
        $('#id').val(user.id);
        $('#firstname').val(user.firstname);
        $('#lastname').val(user.lastname);
        $('#age').val(user.age);
        $('#email').val(user.email);
        $('#passwordModalWindow').removeAttr('placeholder');
        $('#formPasswordRepeat').remove();
        $('#buttonEditOrCreatNewUser').html('Edit User').removeClass('btn-danger');
        $('#labelAddnewUserOrEditPage').html('Edit User page');
        $('#addRoles').remove();
        if (user.roles.length === 2) {
            let html = '<select size="2" class="custom-select" name="roleIds" id="addRoles"  multiple>\n' +
                '<option  id="ADMIN" value="ADMIN" selected>ADMIN</option>\n' +
                '<option id="USER"  value="USER" selected>USER</option>\n' +
                '</select>'
            $('#selectRole').append(html);
        } else {
            let html = '<select size="2" class="custom-select" name="roleIds" id="addRoles"  multiple>\n' +
                '<option  id="ADMIN" value="ADMIN">ADMIN</option>\n' +
                '<option id="USER"  value="USER" selected>USER</option>\n' +
                '</select>'
            $('#selectRole').append(html);
        }
    });
    $('#newAndEditUserModal').modal();
}

// CLICK CREATE OR EDIT OR DELETE USER

const buttonEditOrCreatNewUser = $('#buttonEditOrCreatNewUser');
buttonEditOrCreatNewUser.on('click', () => {
    let id = $('#id').val();
    let firstname = $('#firstname').val();
    let lastname = $('#lastname').val();
    let age = $('#age').val();
    let email = $('#email').val();
    let password = $('#passwordModalWindow').val();
    // let passwordRepeat = $('#passwordRepeat').val();

    if (buttonEditOrCreatNewUser.html() === 'Delete User') {
        fetch('/delete/' + id, {
            method: 'DELETE',
        })
            .then(res => res.text()) // or res.json()
            .then(res => console.log(res))
    }

    if (buttonEditOrCreatNewUser.html() === 'Edit User') {
        fetch('/edit', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id,
                firstname: firstname,
                lastname: lastname,
                age: age,
                email: email,
                password: password
            })
        });
        console.log('PUT')
    }

    if (buttonEditOrCreatNewUser.html() === 'Add new User' || buttonEditOrCreatNewUser.html() === 'Register') {
        fetch('/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: null,
                firstname: firstname,
                lastname: lastname,
                age: age,
                email: email,
                password: password
            })
        });
    }

    $("#closeModal").click();
    $('#drowTable tr').remove()
    setTimeout(() => drawTableAllUsers(getAllUsers()), 200);

});


async function getAllUsers() {
    const response = await fetch('http://localhost:8080/allUsers');
    return await response.json();
}


async function getUserBiId(id) {
    const response = await fetch('http://localhost:8080/get/' + id);
    let c = await response.json();
    let arr = [c];
    return arr;
}

function drawTableAllUsers(usersOrUser, bool = true) {
    console.log(bool)
    usersOrUser.then(data => {
        let tabHtml = '';
        let count = 1;
        data.map(user => {
            let roleString = '';
            for (let i = 0; i < user.roles.length; i++) {
                roleString += user.roles[i].role + '  ';
            }
            let str = '';
            str = bool ?
                '<td><a id="/get/' + user.id + '" onclick="editUser(this)" type="button" class="btn btn-primary text-white dontSee">Edit</a></td>' +
                '<td><a id="/get/' + user.id + '" onclick="deleteUser(this)" type="button" class="btn btn-danger text-white dontSee">Delete</a></td></tr>' : '</tr>'
            tabHtml += '<tr>\n<td>' + count++ + '</td>\n' +
                '<td>' + user.id + '</td>\n' +
                '<td>' + user.firstname + '</td>\n' +
                '<td>' + user.lastname + '</td>\n' +
                '<td>' + user.age + '</td>\n' +
                '<td>' + user.email + '</td>\n' +
                '<td>' + roleString + '</td>\n' +
                str
        });
        $('#drowTable').append(tabHtml);
    });
}

function drawBlockRoles() {
    let button = $('#navBarWithRoles').html().split(' ');
    let html = '';
    for (b in button) {
        html += '<button type="button" class="btn btn-block btn-primary active" onclick="buttonRole(this)">' + button[b] + '</button>'
    }

    $('#rolesForUser').append(html);
}

async function buttonRole(event) {
    let html = $(event).html();
    if (html === 'ADMIN') {
        $('#drowTable tr').remove();
        $('#allUsersAndAboutUser').html('All users')
        $('#battonsUsersTableAndAddNewUser').css('display', '')
        $('#adminPanelAndUserInformation-page').html('Admin panel')
        drawTableAllUsers(getAllUsers());
        $('.dontSee').css('display', '')
    } else {
        $('#allUsersAndAboutUser').html('About user')
        $('#adminPanelAndUserInformation-page').html('User information-page')
        $('#battonsUsersTableAndAddNewUser').css('display', 'none')
        $('#drowTable tr').remove();
        drawTableAllUsers(getUserBiId($('#idAuth').html()), false)
        $('.dontSee').css('display', 'none')
    }
}

// drawTableAllUsers(getAllUsers())
drawBlockRoles()
buttonRole()