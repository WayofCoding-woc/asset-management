$(document).ready(function(){
    var assetNo = queryParam('assetNo');
    sessionExists(function(data){
        if(isEmpty(assetNo)){
            search('', '', '');
        }else{
            $('#search_asset_no').val(assetNo);
            search(assetNo, '', '');
        }
    })
    bindAllocateAssetDialog();
    bindAssetAuditDialog();
});

function bindAllocateAssetDialog(){
    $("#allocate-asset-dialog").dialog({
         autoOpen : false,
         modal : true,
         show : "blind",
         hide : "blind",
         width: 400,
         height: 300,
         buttons: {
                       "Submit": function() {
                            var assetAllocateToUserIdDOM = $("#assetAllocateToUserId");
                            var userDisplayId = assetAllocateToUserIdDOM.val();
                            if(isEmpty(userDisplayId)){
                                alert("User Id can't be empty");
                                return false;
                            }

                            userDisplayId = $.trim(userDisplayId);
                            if(!userDisplayId.startsWith('AM-')){
                                alert('User Id should start with AM-');
                                return false;
                            }

                            var userId = userDisplayId.substring('AM-'.length);

                            var assetId = $.trim(assetAllocateToUserIdDOM.attr('asset_id'));

                            console.log("asset allocation request for input userId="+userId+", userDisplayId="+userDisplayId+", assetId="+assetId);

                            assetAllocateToUserIdRequestToServer(assetId, userId, userDisplayId);

                            $(this).dialog("close");
                       }
                   }
    });
}

function openAllocateAssetDialog(){
    $("#allocate-asset-dialog").dialog("open");
}

function openAssetAuditDialog(){
    $("#asset-audit-dialog").dialog("open");
}

function bindAssetAuditDialog(){
    $("#asset-audit-dialog").dialog({
         autoOpen : false,
         modal : true,
         show : "blind",
         hide : "blind",
         width: 600,
         height: 500
    });
}

function processForm(){
    var search_asset_no = $.trim($('#search_asset_no').val());
    var search_asset_type = $('#search_asset_type').val();
    var search_asset_is_available = $('#search_asset_is_available').val();

    search(search_asset_no, search_asset_type, search_asset_is_available);
    return false;
}

function search(search_asset_no, search_asset_type, search_asset_is_available){
        $.ajax({
                url: '/api/asset/searchAsset?assetNo='+search_asset_no+'&assetType='+search_asset_type+'&isAvailable='+search_asset_is_available,
                dataType: 'json',
                type: 'get',
                contentType: 'application/json',
                success: function( data, textStatus, jQxhr ){
                    if(null != data && 'undefined' != data){
                        var allRows = '<tr>'
                                          +'<th>Id</th>'
                                          +'<th>Asset No</th>'
                                          +'<th>Asset Name</th>'
                                          +'<th>Asset Type</th>'
                                          +'<th>User Display Id</th>'
                                          +'<th>Action</th>'
                                          +'<th>Audit Details</th>'
                                      +'</tr>';

                        for(var i in data){
                            var row = data[i];
                            var actionData = '';
                            if(isEmpty(row.userDisplayId)){
                                //it is unallocated
                                actionData = '<a href="#" onclick="allocateAssetToUser('+row.id+');" >Allocate</a>';
                            }else{
                                //it is already allocated
                                actionData = '<a href="#" onclick="deallocateAssetFromUser('+row.id+');" >Deallocate</a>';
                            }

                            var auditData = '<a href="#" onclick="showAuditForAssetId('+row.id+');" >Show</a>';

                            var row_template =  '<tr>'
                                                    +'<td>'+row.id+'</td>'
                                                    +'<td>'+row.assetNo+'</td>'
                                                    +'<td>'+row.assetName+'</td>'
                                                    +'<td>'+row.assetType.assetTypeDescription+'</td>'
                                                    +'<td id="userDisplayId_container_for_asset_id_'+row.id+'">'+linkToUserId(row.userId, row.userDisplayId)+'</td>'
                                                    +'<td id="action_container_for_asset_id_'+row.id+'">'+actionData+'</td>'
                                                    +'<td id="audit_container_for_asset_id_'+row.id+'">'+auditData+'</td>'
                                                +'</tr>';
                            allRows += row_template;
                        }

                        $('#search-result').html('<table>'+allRows+'</table>');
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

function allocateAssetToUser(assetId){
     var assetAllocateToUserIdDOM = $("#assetAllocateToUserId");
     assetAllocateToUserIdDOM.val('');
     assetAllocateToUserIdDOM.attr('asset_id', assetId);
     openAllocateAssetDialog();
}

function assetAllocateToUserIdRequestToServer(assetId, userId, userDisplayId){
    $.ajax({
        url: '/api/asset/allocate',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({"assetId":assetId, "userId":userDisplayId}),
        success: function( data, textStatus, jQxhr ){
            if(!isEmpty(data.errorMsg)){
                alert(data.errorMsg);
                return;
            }

            $('#userDisplayId_container_for_asset_id_'+assetId).html(linkToUserId(userId, userDisplayId));
            $('#action_container_for_asset_id_'+assetId).html('<a href="#" onclick="deallocateAssetFromUser('+assetId+');" >Deallocate</a>');
        },
        statusCode: {
            307: sessionExpiredRedirectPage
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
}

function deallocateAssetFromUser(assetId){
    if(false == confirm('Are you sure?')){
        return false;
    }

    $.ajax({
        url: '/api/asset/deallocate',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({"assetId":assetId}),
        success: function( data, textStatus, jQxhr ){
            if(!isEmpty(data.errorMsg)){
                alert(data.errorMsg);
                return;
            }

            $('#userDisplayId_container_for_asset_id_'+assetId).html('');
            $('#action_container_for_asset_id_'+assetId).html('<a href="#" onclick="allocateAssetToUser('+assetId+');" >Allocate</a>');
        },
        statusCode: {
            307: sessionExpiredRedirectPage
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
}

function showAuditForAssetId(assetId){
    $.ajax({
            url: '/api/asset/audit?assetId='+assetId,
            dataType: 'json',
            type: 'get',
            contentType: 'application/json',
            success: function( data, textStatus, jQxhr ){
                if(null != data && 'undefined' != data){
                    openAssetAuditDialog();

                    var allRows = '<tr>'
                                      +'<th>User Id</th>'
                                      +'<th>Asset Id</th>'
                                      +'<th>Operation</th>'
                                      +'<th>Operation Date</th>'
                                  +'</tr>';

                    for(var i in data){
                        var row = data[i];
                        var row_template =  '<tr>'
                                                +'<td>'+row.userId+'</td>'
                                                +'<td>'+row.assetId+'</td>'
                                                +'<td>'+row.operation+'</td>'
                                                +'<td>'+row.createdDateStr+'</td>'
                                            +'</tr>';
                        allRows += row_template;
                    }

                    $('#asset-audit-dialog-container').html('<table>'+allRows+'</table>');
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
