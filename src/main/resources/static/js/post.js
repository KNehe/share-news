const postFormHandler = (event)=>{
    sendPost(event);

    const file= document.getElementById("file").value;

    if(!file){
        event.preventDefault();
        return  showAddPostError("Add image");
    }
    removePostError();

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

            console.log("HUGE", error)
            if(error){
                return showAddPostError(error);
             }
            renderPosts(JSON.parse(result.body));
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

<form  action="/comment" method="post" >

    <label for="text"></label>
    <input type="text" name="text" id="texx t" placeholder="Add a comment" required>

    <input type="hidden" name="postId" value="${post.postId}">

    <button type="submit">Add</button>
</form>

<div th:if="" class="DeletePost" onclick="HandleConfirm(event)">
    <a href="/post/${post.postId}">${post.canDelete ?  'Delete': '' }</a>
</div>

</div>
</div>

`;

return html;

}

const sendPost = (event)=>{
 event.preventDefault();

 const form = new FormData($("#addPostForm")[0]);

 const description = form.get("description");
 const file = form.get("file").toString();


 stompClient.send("/app/post",{}, JSON.stringify({file,description}));

}

const showAddPostError = (errorMessage)=>{
    if(errorMessage){
        document.getElementById("error").innerText = errorMessage;
    }
}

const removePostError = ()=>{
    document.getElementById("error").innerText = null;


}
