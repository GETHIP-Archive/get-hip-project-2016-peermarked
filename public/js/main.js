$(document).ready(function(){
	$('#mobileNav').hide();
    $('#comment1').hide();
    $('#dropdown').hide();

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





if(typeof homepage !== 'undefined') {
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


    $('#menuDrop').click(function(){
        $('#dropdown').toggle();


    });
}

// function myFunction() {
//     document.getElementById("myDropdown").classList.toggle("show");
// }

// // Close the dropdown if the user clicks outside of it
// 






});

