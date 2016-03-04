var ajax = {
	post: function (url, data) {
		$.ajax({
			type: 'POST',
			url: url,
			dataType: 'json',
			data: data,
			success: function () {
				console.log('success');
			},
			error: function (xhr) {
				alert('error');
			},
			contentType: 'application/json; charset="utf-8',
			async: true,
			cache: false

		});
	},
	put: function (url, data) {
		$.ajax({
			type: 'PUT',
			url: url,
			dataType: 'json',
			data: data,
			success: function () {
				console.log('success');
			},
			error: function (xhr) {
				alert('error');
			},
			contentType: 'application/json; charset="utf-8',
			async: true,
			cache: false
		});
	},
	delete: function (url, id) {
		$.ajax({
			type: 'DELETE' + id,
			url: url,
			dataType: 'json',
			contentType: 'text/plain charset=utf-8',
			async: true,
			cache: false,
			success: function() {
					console.log('success');
			},
			error: function (xhr) {
				alert('error');
			}
		});
	},
	get: function (url, id) {
		$.ajax({
			url: url + id,
			dataType: 'json',
			type: 'GET',
			contentType: 'application/json; charset=utf-8',
			async: true,
			cache: false,
			success: function () {
					console.log('success');
			},
			error: function (xhr) {
				alert('error');
			}
		});
	}
}