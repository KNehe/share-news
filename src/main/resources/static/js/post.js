const formHandler = (event)=>{

    const file= document.getElementById("file").value;

    if(!file){
        event.preventDefault();
        return document.getElementById("error").innerText = "Add image";
    }


};

const HandleConfirm = (event)=>{

    const response = confirm("Confirm operation");

    if(response === true){
        //do nothing
        //href will trigger
    }else if (response === false){
        //href will not trigger
        return event.preventDefault();
    }

}