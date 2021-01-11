const postFormHandler = (event)=>{

    const file= document.getElementById("file").value;

    if(!file){
        event.preventDefault();
        return  showAddPostError("Add image");
    }

    removePostError();

    sendPost(event);


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

let stompClient = null;


const connect = () =>{
    var socket = new SockJS('/share-news-app');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        stompClient.send("/app/get-posts", {},null);

        stompClient.subscribe('/news-app/get-posts', function (result) {
            const parsedResult = JSON.parse(result.body);
            const error = parsedResult?.body?.failure || undefined;
            if(error){
                return showAddPostError(error);
             }
            renderPosts(parsedResult.body);
        });
    });
}

window.onload = (()=>{
connect();
});

const renderPosts =(posts) =>{
    let content = [];

    posts.forEach( post=>{
        content.push(createPostContent(post));
    });

    $(".Posts").html(content);

}

const createPostContent = (post) =>{

const html = `

<div class="PostsContent">

<div class="PostedBySection">
<div class="PostedByGraphic"><span class="Icon">${post.icon}</span></div>
<h4>${post.postByName}</h4>
</div>

<img src="${post.image}" alt="image">

<div class="PostedDescriptionSection">
<p>${post.description}</p>
</div>

<div class="PostCommentCount">
<a href="/post/${post.postId}/comments">
    <p>${post.noOfComments != 0 ? post.noOfComments + ' comments' : ''}</p>
</a>
    <p>${post.noOfComments == 0 ? post.noOfComments + ' comments' : ''}</p>
</div>


<div class="AddCommentSection">

<form onsubmit="addComment(event,${post.postId})">

    <label for="text"></label>
    <input type="text" name="text" id="commentText" placeholder="Add a comment" required onchange="getCommentText(event)">
    <button type="submit">Add</button>
</form>

<div th:if="" class="DeletePost" onclick="deletePost(event,${post.postId})">
    <a>${post.canDelete ?  '<span class="fa fa-trash"></span>': '' }</a>
</div>

</div>
</div>

`;

return html;

}

const sendPost = async (event)=>{
 event.preventDefault();

 const description = $('#description').val();

 const imageUrl = await uploadImage();

 stompClient.send("/app/post",{}, JSON.stringify({imageUrl,description}));

}

const showAddPostError = (errorMessage)=>{
    if(errorMessage){
        document.getElementById("error").innerText = errorMessage;
    }
}

const removePostError = ()=>{
    document.getElementById("error").innerText = null;
}

const uploadImage = async () =>{
     
    const form = new FormData($("#addPostForm")[0]);

    return new Promise((resolve,reject)=>{
        
    $.ajax({
        url: "/image-upload",
        type: "POST",
        data: form,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (res) {
            $("#addPostForm")[0].reset();
            resolve(res);
        },
        error: function (err) {
            const errMsg = JSON.parse(err.responseText);
            $("#addPostForm")[0].reset();
            showAddPostError(errMsg.failure);
        }
    });

    });    

}

const deletePost =  (event, postId) =>{

    const response = confirm("Confirm operation");

    if(response === true){
        
     stompClient.send("/app/delete-post",{},JSON.stringify(postId));
     
    }else if (response === false){
        return event.preventDefault();
    }

;

}

let commentText;
const getCommentText = (event) =>{
    commentText = event.target.value;
}

const addComment = (event,postId) =>{
  event.preventDefault();
   stompClient.send("/app/add-comment",{},JSON.stringify({postId,"text":commentText}));
}
