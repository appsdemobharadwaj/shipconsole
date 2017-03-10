/*-----------------------------------------------------------------------------------*/
/* 		Mian Js Start ( Drive Me - Driving School Management HTML5 Theme )
/*-----------------------------------------------------------------------------------*/
$(document).ready(function($) {
	"use strict"
	/*-----------------------------------------------------------------------------------*/
	/*    STICKY NAVIGATION
	/*-----------------------------------------------------------------------------------*/
	$(".sticky").sticky({topSpacing:0});
	/*-----------------------------------------------------------------------------------*/
	/*    DATE PICKER
	/*-----------------------------------------------------------------------------------*/
	$("#datepicker").datepicker({
		inline: true
	});
	/*-----------------------------------------------------------------------------------*/
	/*  ISOTOPE PORTFOLIO
	/*-----------------------------------------------------------------------------------*/
	var $container = $('.portfolio-wrapper .items');
	$container.imagesLoaded(function () {
	    $container.isotope({
	        itemSelector: '.item',
	        layoutMode: 'fitRows'
	    });
	});
	$('.filter li a').on("click",function() {
	    $('.filter li a').removeClass('active');
	    $(this).addClass('active');
	    var selector = $(this).attr('data-filter');
	    $container.isotope({
	        filter: selector
	    });
	    return false;
	});
	/*-----------------------------------------------------------------------------------*/
	/* 	BANNER SLIDER
	/*-----------------------------------------------------------------------------------*/
	$('.flexslider').flexslider({
	    animation: "fade",
		slideshow: true,                //Boolean: Animate slider automatically
	    slideshowSpeed: 6000,           //Integer: Set the speed of the slideshow cycling, in milliseconds
	    animationSpeed: 400,            //Integer: Set the speed of animations, in milliseconds
		pauseOnAction: true,            //Boolean: Pause the slideshow when interacting with control elements, highly recommended.
	    pauseOnHover: true            //Boolean: Pause the slideshow when hovering over slider, then resume when no longer hovering
	});
	/*-----------------------------------------------------------------------------------*/
	/* 	WOW ANIMATION
	/*-----------------------------------------------------------------------------------*/
	var wow = new WOW({
	    boxClass:     'wow',      // animated element css class (default is wow)
	    animateClass: 'animated', // animation css class (default is animated)
	    offset:       10,          // distance to the element when triggering the animation (default is 0)
	    mobile:       false,       // trigger animations on mobile devices (default is true)
	    live:         true       // act on asynchronously loaded content (default is true)
	});
	wow.init();
	/*-----------------------------------------------------------------------------------*/
	/*    Parallax
	/*-----------------------------------------------------------------------------------*/
	jQuery.stellar({
		horizontalScrolling: false,
		scrollProperty: 'scroll',
		positionProperty: 'position'
	});
});
/*-----------------------------------------------------------------------------------*/
/* 	DROPDOWN HOVER
/*-----------------------------------------------------------------------------------*/
/*$('.navbar .dropdown').on("hover",function() {
	$(this).find('.dropdown-menu').first().stop(true, true).slideDown(0);
},
function() {
	$(this).find('.dropdown-menu').first().stop(true, true).slideUp(0)
});*/
/*-----------------------------------------------------------------------------------*/
/*    TESTIMONIALS SLIDER
/*-----------------------------------------------------------------------------------*/
$(".testi-slide").owlCarousel({ 
      autoPlay: 6000, //Set AutoPlay to 6 seconds 
      items : 1,
	  singleItem:true,
	  autoPlay : true,
      navigation : false, // Show next and prev buttons
	  pagination : true,
	  navigationText: ["<i class='fa fa-chevron-left'></i>","<i class='fa fa-chevron-right'></i>"]
}); 
/*-----------------------------------------------------------------------------------*/
/*    NEWS SLIDER
/*-----------------------------------------------------------------------------------*/
$('.news-slide').lightSlider({
	item: 2,
	slideMargin: 15,
	adaptiveHeight:true,
	slideMove: 2, // slidemove will be 1 if loop is true
	speed:400
});
/*-----------------------------------------------------------------------------------*/
/*    RELATED SLIDER
/*-----------------------------------------------------------------------------------*/
$('.raleted-slide').lightSlider({
    item: 1,
	slideMargin: 0,
	adaptiveHeight:true,
    slideMove: 1, // slidemove will be 1 if loop is true
    speed:400
});
/*-----------------------------------------------------------------------------------*/
/*    SLIDER
/*-----------------------------------------------------------------------------------*/
$(".owl-slide").owlCarousel({ 
      autoPlay: 6000, //Set AutoPlay to 6 seconds 
      items : 1,
	  singleItem:true,
	  autoPlay : false,
      navigation : true, // Show next and prev buttons
	  pagination : true,
	  navigationText: ["<i class='fa fa-chevron-left'></i>","<i class='fa fa-chevron-right'></i>"]
}); 
/*-----------------------------------------------------------------------------------*/
/*    POPUP VIDEO
/*-----------------------------------------------------------------------------------*/
$('.popup-vedio').magnificPopup({
		type: 'inline',
		fixedContentPos: false,
		fixedBgPos: true,
		overflowY: 'auto',
		closeBtnInside: true,
		preloader: true,
		midClick: true,
		removalDelay: 300,
		mainClass: 'my-mfp-slide-bottom'
});
$('.gallery-pop').magnificPopup({
	delegate: 'a',
	type: 'image',
	tLoading: 'Loading image #%curr%...',
	mainClass: 'mfp-img-mobile',
	gallery: {
		enabled: true,
		navigateByImgClick: true,
		preload: [0,1] // Will preload 0 - before current, and 1 after the current image
	},
	image: {
		tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
		titleSrc: function(item) {
			return item.el.attr('title') + '';
	}}
});
function validateForm() {
 var x = document.forms["newsletter"]["newsletter"].value;
   if (x == null || x == "") {
       alert("Name must be filled out");
       return false;
}}
/*-----------------------------------------------------------------------------------*/
/*    CONTACT FORM
/*-----------------------------------------------------------------------------------*/
function checkmail(input){
  var pattern1=/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
  	if(pattern1.test(input)){ return true; }else{ return false; }}     
    function proceed(){
    	var name = document.getElementById("name");
		var email = document.getElementById("email");
		var company = document.getElementById("company");
		var web = document.getElementById("website");
		var msg = document.getElementById("message");
		var errors = "";
		  if(name.value == ""){ 
		  	name.className = 'error';
		  return false;}    
		  else if(email.value == ""){
		  email.className = 'error';
		  return false;}
		    else if(checkmail(email.value)==false){
		        alert('Please provide a valid email address.');
		        return false;}
		    else if(company.value == ""){
		        company.className = 'error';
		        return false;}
		   else if(web.value == ""){
		        web.className = 'error';
		        return false;}
		   else if(msg.value == ""){
		        msg.className = 'error';
		        return false;}
		   else 
		  {
    	$.ajax({
			type: "POST",
			url: "submit.php",
			data: $("#contact_form").serialize(),
			success: function(msg){
			//alert(msg);
            if(msg){
                $('#contact_form').fadeOut(1000);
                $('#contact_message').fadeIn(1000);
                document.getElementById("contact_message");
            return true;
        }}
    });
}};
