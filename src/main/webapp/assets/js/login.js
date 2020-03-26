$(document).ready(function() {
    var textfield = $("input[name=user]");
    $('button[type="submit"]').click(function(e) {
        e.preventDefault();
        //little validation just to check username
        if (textfield.val() != "") {

            // var username = $("#user").val();
            // var password = $("#password").val();
            // var form_data = new FormData(); //建構new FormData()

            // form_data.append("username", username);
            // form_data.append("password", password);
            // form_data.append("_id", "3a45sdqwegjeu");
            // for (var pair of form_data.entries()) {
            //     console.log(pair[0] + ', ' + pair[1]);
            // }
            // $.ajax({
            //     url: 'https://ai-rest.cse.ntou.edu.tw/rewabo/LoginServlet',


            //     cache: false,
            //     contentType: false,
            //     processData: false,
            //     data: form_data, //data只能指定單一物件
            //     type: 'Post',
            //     success: function(data) {
            //         console.log(data);
            //         //window.location.reload();
            //     },
            //     error: function(data) {
            //         alert("失敗");
            //     }

            // });
            //$("body").scrollTo("#output");
            // $("#output").addClass("alert alert-success animated fadeInUp").html("歡迎!");
            // $("#output").removeClass(' alert-danger');
            // $("input").css({
            //     "height": "0",
            //     "padding": "0",
            //     "margin": "0",
            //     "opacity": "0"
            // });
            // //change button text 
            // $('button[type="submit"]').html("continue")
            //     .removeClass("btn-info")
            //     .addClass("btn-default").click(function() {
            //         $("input").css({
            //             "height": "auto",
            //             "padding": "10px",
            //             "opacity": "1"
            //         }).val("");
            //     });

            // //show avatar
            // $(".avatar").css({
            //     "background-image": "url('assets/image/login.png')"
            // });
        } else {
            //remove success mesage replaced with error message
            $("#output").removeClass(' alert alert-success');
            $("#output").addClass("alert alert-danger animated fadeInUp").html("請輸入帳號");
        }
        //console.log(textfield.val());

    });
});