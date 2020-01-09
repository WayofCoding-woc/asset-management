$(document).ready(function(){
    sessionExists(function(data){
    })
});

function processForm(){
    var loginId = $('#loginId').val();
    var password = $('#password').val();
    var confirm_password = $('#confirm_password').val();

    if($.trim(loginId).length == 0){
        alert('loginId should not be empty');
        return false;
    }

    if($.trim(password).length == 0){
        alert('password should not be empty');
        return false;
    }

    if($.trim(confirm_password).length == 0){
        alert('confirm_password should not be empty');
        return false;
    }

    if(password != confirm_password){
        alert('password and confirm_password should match');
        return false;
    }

    if($.trim($('#user_name').val()).length == 0){
        alert('user_name should not be empty');
        return false;
    }
    if($.trim($('#email').val()).length == 0){
        alert('email should not be empty');
        return false;
    }
    if($.trim($('#mobile').val()).length == 0){
        alert('mobile should not be empty');
        return false;
    }
    if($.trim($('#doj').val()).length == 0){
        alert('doj should not be empty');
        return false;
    }

    return true;
}
