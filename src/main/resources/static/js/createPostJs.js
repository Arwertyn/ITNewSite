$('#postName').on('change', async function () {
    if($('#postName').val() !== ""){
        let value = await $.ajax({
            url: "../api/post/getByName",
            method: "get",
            data: {name: $('#postName').val()}
        })

        if(value !== ""){
            $('#saveButton').prop('disabled', true)
            $('#alert').html("Такая стаья уже существует").addClass("alert-warning")
        } else {
            $('#alert').html("").removeClass("alert-warning")
            $('#saveButton').prop('disabled', false)
        }
    }
})


