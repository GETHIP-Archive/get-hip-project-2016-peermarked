/*if (document.cookie.replace(/(?:(?:^|.*;\s*)signedin\s*\=\s*([^;]*).*$)|^.*$/, "$1") === 'true') {
	window.location.href = "/src/loggedIn.html";
}*/
$(document).ready(function(){
	/*<div class="uploadedContent">
		<p href="#" class="paperTitles"> 
			<span class="tags"><a class="tag" href="#">JavaScript</a>, <a class="tag" href="#">Computers</a>, <a class="tag" href="#">Programing</a></span> 
			JavaScript Functions <br>
			<span class="author">by <a class="tag" href="#">Jared_Bledsoe</a></span>
		</p>
		<hr>
	</div>*/

	var recent = ajax.get('http://localhost:9998/papers/recent', '');

	//console.log('recent: ' + recent);

	var interval = setInterval(placeInfo, 80);

	function createListener(i) {
		$('.uploadedContent' + i).click(function() {
			document.cookie = 'paper=' + recent.paper[i].id + '; expires=Thu, 18 Dec 2017 12:00:00 UTC';
			window.location.href = "/src/uploadedPaper.html";
		});
	}

	function placeInfo() {
		console.log('test' + recent);
		if(recent != undefined) {

			console.log('recent: ' + recent);

			console.log('adlkfajsdlkf');

			for(var i = 0; i < 8; i++) {
				$('.title' + i).append(recent.paper[i].title);
				$('.author' + i).append(recent.paper[i].author);
				console.log(recent.paper[i].title, recent.paper[i].author);
				createListener(i);
			}
			clearInterval(interval);
		}
	}

	$('#search').bind("enterKey",function(e){
		document.cookie = 'search=' + $(this).val() + '; expires=Thu, 18 Dec 2017 12:00:00 UTC';
		window.location.href = "/src/searched.html";
	});
	$('#search').keyup(function(e){
		if(e.keyCode == 13) {
			$(this).trigger("enterKey");
		}
	});
});