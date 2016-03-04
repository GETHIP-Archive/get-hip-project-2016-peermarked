if (document.cookie.replace(/(?:(?:^|.*;\s*)signedin\s*\=\s*([^;]*).*$)|^.*$/, "$1") == 'true') {
	window.location.href = "/src/loggedIn.html";
}
$(document).ready(function() {
	$('#button').click(function(){
		document.cookie = 'signedin=true'
		window.location.href = "/src/loggedIn.html";
	});
});