$(document).ready(function(){
    sessionExists(function(data){
    })
});

function processForm(){
    var assetNo = $('#assetNo').val();
    var assetName = $('#assetName').val();
    var assetType = $('#assetType').val();

    if($.trim(assetNo).length == 0){
        alert('assetNo should not be empty');
        return false;
    }

    if($.trim(assetName).length == 0){
        alert('assetName should not be empty');
        return false;
    }

    if($.trim(assetType).length == 0){
        alert('please select the assetType');
        return false;
    }

    return true;
}
