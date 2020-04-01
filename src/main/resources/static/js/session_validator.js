var sessionExists = function(callback){
        $.ajax({
                url: '/api/user/sessionExists',
                dataType: 'json',
                type: 'get',
                contentType: 'application/json',
                success: function( data, textStatus, jQxhr ){
                    callback(data);
                },
                statusCode: {
                    307: sessionExpiredRedirectPage,
                    401: unauthorizedAccessRedirectPage
                },
                error: function( jqXhr, textStatus, errorThrown ){
                    try{
                        console.log(jqXhr.status)
                    }catch(e){
                        console.log(e.toString());
                    }
                }
        });

}

var showMenu = function(data){

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

        }
}
