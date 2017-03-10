var j = jQuery.noConflict();   
function openNewWindow (URL, windowName, windowOptions)
      {
          var window = getWindow (URL, windowName, windowOptions);
      }

      function getWindow(URL, windowName, windowOptions)
      {
        //alert("URL"+URL);
        //alert("windowName"+windowName);
        //alert("windowOptions"+windowOptions);
          var newWindow = open (URL, windowName, windowOptions)
          newWindow.focus();
          return window;
      }
function changePassword(){
    tpwindow =  window.open("aascChangePassword.jsp","Post",'width=500,height=350,top=100,left=100,scrollbars=yes, resizable=yes');
    tpwindow.focus();
    }

function editProfile(){
tpwindow =  window.open("aascEditUserProfile.jsp","Post",'width=700,height=450,top=100,left=100,scrollbars=yes, resizable=yes');
    tpwindow.focus();
}
 
j(function() {
j(".dropdown").hover(
function() { j(".submenu").slideToggle(400); },
function() { j(".submenu").hide(); }
);
});

