    if( window.location.pathname === "/register"){

        document.getElementById("signUpLink").classList.add("ActiveLink");
        document.getElementById("loginLink").classList.remove("ActiveLink");

    }
    if( window.location.pathname === "/login"
               || window.location.pathname === "/"){
        document.getElementById("loginLink").classList.add("ActiveLink");
        document.getElementById("signUpLink").classList.remove("ActiveLink");
    }

    if( window.location.pathname === "/forgotPassword"){

        if(document.getElementById("loginLink").classList.contains("ActiveLink")
          || document.getElementById("signUpLink").classList.contains("ActiveLink") ){
            document.getElementById("loginLink").classList.remove("ActiveLink");
            document.getElementById("signUpLink").classList.remove("ActiveLink");
        }
    }


