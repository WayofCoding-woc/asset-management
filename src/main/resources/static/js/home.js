$(document).ready(function(){
    sessionExists(function(data){
        if(null != data.session && 'undefined' != data.session){
            $('#welcome_user_name').html(data.userName);

            if(data.role == 'support'){
                $('#user_registration_id').show();
                $('#search_user_id').show();
            }
            if(data.role == 'admin'){
                $('#user_registration_id').show();
                $('#search_user_id').show();

                $('#asset_registration_id').show();
                $('#manage_assets_id').show();
            }

            $('#logged_in_user_profile_assets').attr('href', 'view_user_details.html?id='+data.id);
        }
    })
});