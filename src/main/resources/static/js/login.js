function processForm(){
    var loginId = $('#loginId').val();
    var password = $('#password').val();
    console.log('loginId=' + loginId);
    console.log('password=' + password);

    if($.trim(loginId).length == 0){
        alert('loginId should not be empty');
        return false;
    }

    if($.trim(password).length == 0){
        alert('password should not be empty');
        return false;
    }

    var userData = {};
    userData.loginId = loginId;
    userData.password = password;

    console.log("userData="+userData);
    console.log("JSON.stringify(userData)=" +JSON.stringify(userData) );

    $.ajax({
        url: '/api/doLogin',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data : JSON.stringify(userData),
        success: function( data, textStatus, jQxhr ){
            if(null != data.session && 'undefined' != data.session){
                var currentUrl = window.location.toString();
                currentUrl = currentUrl.replace(window.location.pathname, '/home.html');
                window.location.replace(currentUrl);
            }else if(null != data.invalidCredentials && 'true' == data.invalidCredentials.toString()){
                var currentUrl = window.location.toString();
                currentUrl = currentUrl.replace(window.location.pathname, '/error.html');
                window.location.replace(currentUrl);
            }else if(null != data.inActiveUser && 'true' == data.inActiveUser.toString()){
                 var currentUrl = window.location.toString();
                 currentUrl = currentUrl.replace(window.location.pathname, '/inactive_user.html');
                 window.location.replace(currentUrl);
             }


        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });

    return false;
}
