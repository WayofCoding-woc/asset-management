var sessionExpiredRedirectPage = function() {
     var currentUrl = window.location.toString();
     currentUrl = currentUrl.replace(window.location.pathname, '/session_expired.html');
     window.location.replace(currentUrl);
}

var unauthorizedAccessRedirectPage = function() {
     var currentUrl = window.location.toString();
     currentUrl = currentUrl.replace(window.location.pathname, '/unauthorized_access.html');
     window.location.replace(currentUrl);
}

var isEmpty = function(input){
    if(null == input || 'undefined' == input || $.trim(input).length == 0){
        return true;
    }else{
        return false;
    }
}

var linkToUserId = function (userId, userDisplayId){
    if(isEmpty(userDisplayId)){
        return '';
    }

    return '<a href="view_user_details.html?id='+userId+' ">'+userDisplayId+'</a>'
}

var queryParam = function(key){
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(key);
}