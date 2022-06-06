
$( document ).ready(function() {
    console.log( "ready!" );
    var modal = document.getElementById("myModal");
    console.log("Hello world!" +modal);
    // Get the button that opens the modal
    var btn = document.getElementById("myBtn");
    console.log("Hello world!"+btn);

    var email = document.getElementById("email").value;
     console.log(email);
     console.log("Hello world!");
    var firstname = document.getElementById("firstname").value;
      console.log(firstname);
    var lastname = document.getElementById("lastname").value;
     console.log(lastname);
   const password = document.querySelector('input[name=password]');
     console.log(password.value);
    var confirm = document.getElementById("confirm").value;
     console.log(confirm);
    var address = document.getElementById("address").value;
     console.log(address);
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    // When the user clicks on the button, open the modal
    btn.onclick = function() {
    if(email === null ){
        console.log(email);
        alert("Không được để rỗng");
    }else{
      modal.style.display = "block";
    }
    }
    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
      modal.style.display = "none";
    }
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
      if (event.target == modal) {
        modal.style.display = "none";
      }
    }
});
