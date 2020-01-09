$(document).ready(function(){
    sessionExists(function(data){
        searchUser('');
    })
});


function processForm(){
    var search_user_name = $.trim($('#search_user_name').val());
    if(search_user_name.length == 0){
        alert('input user_name should not be empty');
        return false;
    }

    searchUser(search_user_name);
    return false;
}


function searchUser(userName){
        $.ajax({
                url: '/api/user/searchUser?userName='+userName,
                dataType: 'json',
                type: 'get',
                contentType: 'application/json',
                success: function( data, textStatus, jQxhr ){
                    if(null != data && 'undefined' != data){
                        var allRows = '<tr>'
                                          +'<th>User Display Id</th>'
                                          +'<th>User Name</th>'
                                          +'<th>Email</th>'
                                          +'<th>Mobile</th>'
                                          +'<th>Date of Joining</th>'
                                          +'<th>Active?</th>'
                                          +'<th>Created Date</th>'
                                      +'</tr>';

                        for(var i in data){
                            var row = data[i];
                            var row_template =  '<tr>'
                                                    +'<td>'+linkToUserId(row.id, row.userDisplayId)+'</td>'
                                                    +'<td>'+row.userName+'</td>'
                                                    +'<td>'+row.email+'</td>'
                                                    +'<td>'+row.mobile+'</td>'
                                                    +'<td>'+row.dateOfJoiningStr+'</td>'
                                                    +'<td>'+row.active+'</td>'
                                                    +'<td>'+row.createdDateStr+'</td>'
                                                +'</tr>';
                            allRows += row_template;
                        }

                        $('#user-search-result').html('<table>'+allRows+'</table>');
                    }else{
                        alert('unable to load data from server, please try again later...');
                    }
                },
                statusCode: {
                   307: sessionExpiredRedirectPage
                },
                error: function( jqXhr, textStatus, errorThrown ){
                    console.log( errorThrown );
                }
        });

}