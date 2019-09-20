$(document).ready(function() {
    var sidebar = $('#menu-toggle'),
        wrapper = $('#wrapper');
    sidebar.click(function(e) {
        e.preventDefault();
        wrapper.toggleClass("toggled");
    });

});