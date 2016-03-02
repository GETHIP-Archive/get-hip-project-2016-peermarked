$(document).ready(function(){
	$('#mobileNav').hide();
    $('#comment1').hide();

 $('#highlightAnimation')
      .delay(1000)
      .queue( function(next){ 
        $(this).css("background-color", "#C6D53E"); 
        next(); 
      });

// Show correct navBar for device
	if($(window).width() <= 768){
	$('#navBar').hide();
	$('#mobileNav').show();
	}

    if($(window).width() > 768) {
        $('#comment1').delay(1000).fadeIn(500);
    }






// Typewriter
	var str = '<p>PeerMarked</p>',
    i = 0,
    isTag,
    text;

(function type() {
    text = str.slice(0, ++i);
    if (text === str) return;
    
    document.getElementById('typewriter').innerHTML = text;

    var char = text.slice(-1);
    if( char === '<' ) isTag = true;
    if( char === '>' ) isTag = false;

    if (isTag) return type();
    setTimeout(type, 80);
}());





function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(e) {
  if (!e.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    for (var d = 0; d < dropdowns.length; d++) {
      var openDropdown = dropdowns[d];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}





// Type once in sight
// if(aboutTyper)return;

// var aboutTyper = false;

// $(window).scroll(function() {
//     if ($('#aboutSpot').is(':visible')) {

//     var str = "<p>About</p>",
//     i = 0,
//     isTag,
//     text;

// (function type() {
//     text = str.slice(0, ++i);
//     if (text === str) return;
    
//     document.getElementById("typewriter").innerHTML = text;

//     var char = text.slice(-1);
//     if( char === '<' ) isTag = true;
//     if( char === '>' ) isTag = false;

//     if (isTag) return type();
//     setTimeout(type, 80);

//     var aboutTyper = false;
// }());

//     }
// });



});

