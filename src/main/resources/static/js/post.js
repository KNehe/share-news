const formHandler = (event)=>{

    const file= document.getElementById("file").value;

    if(!file){
        event.preventDefault();
        return document.getElementById("error").innerText = "Add image";
    }


};

const HandleDelete = (event)=>{

    const response = confirm("Confirm Deletion");

    if(response === true){
        //do nothing
        //href will trigger
    }else if (response === false){
        //href will not trigger
        return event.preventDefault();
    }

}