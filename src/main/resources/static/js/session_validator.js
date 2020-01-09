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
                    307: sessionExpiredRedirectPage
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
