$(document).ready(function(){
    sessionExists(function(data){
        if(null != data.session && 'undefined' != data.session){
            $('#welcome_user_name').html(data.userName);
            $('#logged_in_user_profile_assets').attr('href', 'view_user_details.html?id='+data.id);
        }
    })
});