$(document).ready(function(){
	var id = document.cookie.replace(/(?:(?:^|.*;\s*)paper\s*\=\s*([^;]*).*$)|^.*$/, "$1");
	console.log(id);

	var paper = ajax.get('http://localhost:9998/papers/', id);

	/*
	paper will be represented as:
		{
		"author": "Matthew"
		"content": "<span class="highlighted">Java </span>is cool"
		"created": e
		"id": "1"
		"title": "Java Programming in Depth"
		}
	*/

	$('#content').append(paper.content);
	$('.title').append(paper.title);
});