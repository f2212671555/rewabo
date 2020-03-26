<%@ page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate">
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<html>
   <head>
      <title>File Uploading Form</title>
   </head>

   <body>
      <h3>File Upload:</h3>
      Select a file to upload: <br />

         <input id="myText" type="text"  />
         <input id="myFile" type="file" />
         <input id="upload" type="submit" />
   </body>
<script >

   $('#upload').on('click', function() {
      var file_data = $('#myFile').prop('files')[0];   //取得上傳檔案屬性
      var text_data = $('#myText').val();
      var form_data = new FormData();  //建構new FormData()
      form_data.append('file', file_data);  //吧物件加到file後面
      form_data.append("className",text_data);
      form_data.append("_id","3a45sdqwegjeu");

      $.ajax({
         url: 'UploadServlet',
         cache: false,
         contentType: false,
         processData: false,
         data: form_data,     //data只能指定單一物件
         type: 'post',
         success: function(data){

         }
      });
   });

</script>
</html>
