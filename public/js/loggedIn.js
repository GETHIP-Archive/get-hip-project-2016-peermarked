/*if (document.cookie.replace(/(?:(?:^|.*;\s*)signedin\s*\=\s*([^;]*).*$)|^.*$/, "$1") !== 'true') {
	window.location.href = "/src/index.html";
}*/
$(document).ready(function(){
	$('#button').click(function() {
		alert("Sorry, this feature is not yet implemented");
	})

	var result = ajax.get('http://localhost:9998/papers/filter/', 'Matthew');

	function createListener(i) {
		$('.title' + i).click(function() {
			document.cookie = 'paper=' + result.paper[i].id + '; expires=Thu, 18 Dec 2017 12:00:00 UTC';
			window.location.href = "/src/uploadedPaper.html";
		});
	}

	if(result == null) {
		$('#uploadedPapers').empty();
		$('#uploadedPapers').text('No results found');
	} else {
		for(var i = 0; i < 8; i++) {
			console.log(i);
			$('.title' + i).text(result.paper[i].title);
			createListener(i);
		}
	}
});