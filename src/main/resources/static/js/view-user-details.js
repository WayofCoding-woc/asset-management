$(document).ready(function(){
    var id = queryParam('id');
    sessionExists(function(data){
        showMenu(data);
        viewUserDetails(id);
    })
});

function viewUserDetails(id){
        $.ajax({
                url: '/api/user/getUserDetailsById?id='+id,
                dataType: 'json',
                type: 'get',
                contentType: 'application/json',
                success: function( data, textStatus, jQxhr ){
                    if(null != data && 'undefined' != data){
                        $('#userDisplayId').html(data.userDisplayId);
                        $('#user_name').html(data.userName);
                        $('#email').html(data.email);
                        $('#mobile').html(data.mobile);
                        $('#doj').html(data.dateOfJoiningStr);
                        $('#role').html(data.role);
                        $('#is_active').html(data.active.toString());
                        $('#created_date').html(data.createdDateStr);

                        var allRows = '<tr>'
                                          +'<th>Asset Id</th>'
                                          +'<th>Asset No</th>'
                                          +'<th>Asset Name</th>'
                                          +'<th>Asset Type</th>'
                                          +'<th>Allocated Date</th>'
                                      +'</tr>';

                        var assets = data.assets;
                        for(var i in assets){
                            var row = assets[i];
                            var row_template =  '<tr>'
                                                    +'<td>'+row.id+'</td>'
                                                    +'<td>'+row.assetNo+'</td>'
                                                    +'<td>'+row.assetName+'</td>'
                                                    +'<td>'+row.assetType.assetTypeDescription+'</td>'
                                                    +'<td>'+row.lastUpdatedDateStr+'</td>'
                                                +'</tr>';
                            allRows += row_template;
                        }

                        $('#asset-container').html('<table>'+allRows+'</table>');
                    }else{
                        alert('unable to load data from server, please try again later...');
                    }
                },
                 statusCode: {
                     307: sessionExpiredRedirectPage,
                     401: unauthorizedAccessRedirectPage
                 },
                error: function( jqXhr, textStatus, errorThrown ){
                    console.log( errorThrown );
                }
        });

}