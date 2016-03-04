/*{
"paper": [10]
0:  {
"author": "Matthew"
"content": "<span class="highlighted">Java </span>is cool"
"created": "2016-02-22T22:08:55-06:00"
"id": "1"
"title": "Java Programming in Depth"
}-
1:  {
"author": "Kaitlyn"
"content": "Kick the ball"
"created": "2016-02-22T22:08:55-06:00"
"id": "2"
"title": "Soccer and how to play"
}*/

var recent = ajax.get('http://localhost:9998/papers/recent', '');


for(var i = 0; i < 10; i++) {
	$('.title' + i).text(recent[i].title);
	$('.author' + i).text(recent[i].author);
}