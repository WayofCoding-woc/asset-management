$(document).ready(function(){
    sessionExists(function(data){
        showMenu(data);
        if(null != data.session && 'undefined' != data.session){
            $('#logged_in_user_profile_assets').attr('href', 'view_user_details.html?id='+data.id);
        }
    })
});