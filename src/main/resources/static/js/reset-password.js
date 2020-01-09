function processForm(){
    var loginId = $('#loginId').val();
    var old_password = $('#old_password').val();
    var new_password = $('#new_password').val();

    if($.trim(loginId).length == 0){
        alert('loginId should not be empty');
        return false;
    }

    if($.trim(old_password).length == 0){
        alert('old_password should not be empty');
        return false;
    }

    if($.trim(new_password).length == 0){
        alert('new_password should not be empty');
        return false;
    }

    if(old_password == new_password){
        alert('old_password and new_password should not be same');
        return false;
    }

    return true;
}
