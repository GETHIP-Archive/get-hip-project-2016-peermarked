//Require the express module and save it as a variable
var express = require('express');
var app = express();

//Bind the appropriate enviornmental variables to javascript variables for later use
var port = process.env.OPENSHIFT_NODEJS_PORT || 8000;
var ip = process.env.OPENSHIFT_NODEJS_IP || "127.0.0.1";


//define all the routes.
app.use('/', express.static(__dirname + '/public'));
/*app.get('/', function(req, res) {
	res.redirect('/public/src/index.html');
})*/

//listen on the specified ports.
app.listen(port, ip, function() {
	console.log((new Date()) + 'server is now listening');
});
