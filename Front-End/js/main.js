$(document).ready(function() {


$("jumper").click(function() {
    $("html,body").animate({
        scrollTop: $("#searchSpot").offset().top},
        "slow");
    debugger;
});
});