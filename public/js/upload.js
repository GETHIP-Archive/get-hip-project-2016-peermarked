$(document).ready(function() {
	$('#button').click(function() {
		var title = $('#titleInput').val();
		var paper = $('textarea').val();
		console.log(title, paper);
	})
})