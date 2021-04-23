/**
 * Webgate post auth details
 */
const request = makeHttpObject();
request.open("GET", document.location, false);
request.send(null);
const oamUser = request.getResponseHeader("OAM_REMOTE_USER");
const params = {}
document.location.search.substr(1).split('&').forEach(pair => {
  [key, value] = pair.split('=')
  params[key] = value
})

if (params.code) {
    
    console.log(params.code);
    localStorage.setItem("code", params.code);
    console.log(location.href);
    history.pushState(null, "", location.href.split("?")[0]);
} 

function makeHttpObject() {
    try {
        return new XMLHttpRequest();
    } catch (error) { }
    try {
        return new ActiveXObject("Msxml2.XMLHTTP");
    } catch (error) { }
    try {
        return new ActiveXObject("Microsoft.XMLHTTP");
    } catch (error) { }

    throw new Error("Could not create HTTP request object.");
}