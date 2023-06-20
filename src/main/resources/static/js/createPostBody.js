const formPostBody = document.getElementById('form-post-body');
const formComment = document.getElementById('comment-form');
const formPublish = document.getElementById('publish-form')
const formUpdateHead = document.getElementById('update-head-form')

setupComments().then(console.log("Loaded"))

if (json == null) json = JSON.stringify({
    blocks: [{
        id: "2435234sedsdfa",
        data: {level: 2, text: "Напишити свой пост"},
        type: "paragraph"
    }]
})

var editor = new EditorJS({
    readOnly: readOnly,
    holder: 'editorjs',

    tools: {

        header: {
            class: Header,
            inlineToolbar: ['marker', 'link'],
            config: {
                placeholder: 'Header'
            },
            shortcut: 'CMD+SHIFT+H'
        },


        image: SimpleImage,

        list: {
            class: List,
            inlineToolbar: true,
            shortcut: 'CMD+SHIFT+L'
        },

        checklist: {
            class: Checklist,
            inlineToolbar: true,
        },

        quote: {
            class: Quote,
            inlineToolbar: true,
            config: {
                quotePlaceholder: 'Enter a quote',
                captionPlaceholder: 'Quote\'s author',
            },
            shortcut: 'CMD+SHIFT+O'
        },

        warning: Warning,

        marker: {
            class: Marker,
            shortcut: 'CMD+SHIFT+M'
        },

        code: {
            class: CodeTool,
            shortcut: 'CMD+SHIFT+C'
        },

        delimiter: Delimiter,

        inlineCode: {
            class: InlineCode,
            shortcut: 'CMD+SHIFT+C'
        },

        linkTool: LinkTool,

        embed: Embed,

        table: {
            class: Table,
            inlineToolbar: true,
            shortcut: 'CMD+ALT+T'
        },

    },

    data: {
        blocks: JSON.parse(json).blocks
    },
});

async function saveBodyForm() {
    let data = await getData();
    console.log(data)
    formPostBody.json.value = JSON.stringify(data);
    formPostBody.submit()
}

function deletePost()
{
    let delForm = document.getElementById("delete-form")
    let dialog = document.getElementById("dialog")
    let ok = document.getElementById("confirmBtn")
    let cancel = document.getElementById("cancelBtn")

    dialog.showModal();
    ok.addEventListener('click',function () {
        delForm.submit()
    })

    cancel.addEventListener('click', function () {
        dialog.close()
    })

}

async function getData() {
    let data = await editor.save();
    return data;
}

function addComment(event){
    event.preventDefault();
    let content = $(formComment).serialize();

    $.ajax({
        url: "../api/post/addComment",
        method: "post",
        data: content,
    })
    setTimeout(()=>{setupComments().then(console.log("ok"))},2000)
}

function publish(event){
    event.preventDefault();
    let content =$(formPublish).serialize();
    $.ajax({
        url: "../api/post/publish",
        method: "post",
        data: content,
        success: function () {
            if(formPublish.bool.value === "true"){
                $("#status-pub").html("Опубликовано").css("color", "lawngreen")
            } else  $("#status-pub").html("Не опубликовано").css("color", "red")
        }
    })
}


async function loadComments(id) {
    return $.getJSON("../api/comment/"+id)
}

async function setupComments() {
    let comments = await loadComments(idPost);
    let list = document.getElementById("comment-list")
    let h3 = document.getElementById("h3-msg")
    if(list !== null) {
        if(h3 !== null) h3.innerHTML = "";
        list.innerHTML="";
        comments.forEach(com => {
            let msg ="<div style='height: 50px' class=\"mb-2 border border-dark rounded-3 d-flex\">\n" +
                "      <div class=\"px-2 bg-dark text-white rounded-3 d-flex align-items-center\">"+com.username+"</div>\n" +
                "      <div class='w-100 mx-2 d-flex align-items-center'>"+com.text+"</div>\n" +
                "     </div>"
            list.innerHTML+=msg;
        })
    }
}

formPostBody.addEventListener('submit', async function (ev) {
    let data = await getData();
    console.log(data)
    formPostBody.json.value = JSON.stringify(data);
})




