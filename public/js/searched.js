$(document).ready(function() {
	var cookieValue = document.cookie.replace(/(?:(?:^|.*;\s*)search\s*\=\s*([^;]*).*$)|^.*$/, "$1");
	console.log(cookieValue);

	var result = ajax.get('http://localhost:9998/papers/filter/', cookieValue);

	function createListener(i) {
		$('.uploadedContent' + i).click(function() {
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
			$('.author' + i).text(result.paper[i].author);
			createListener(i);
		}
	}
})