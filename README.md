## Rewabo網站網址：
https://ai-rest.cse.ntou.edu.tw/rewabo
## 目錄結構：

程式或檔案放置用下方的Maven標準的目錄結構來放：      
    
![enter image className here](https://cloud.google.com/appengine/docs/standard/java/tools/images/maven_layout.png)      
      
java程式都放在src/main/java底下，再依套件去分。    
第二張參考圖：    
    
![enter image className here](http://www.murraywilliams.com/wordpress/wp-content/uploads/2012/04/maven-directory.png)    
    
Maven標準結構說明：[enter link className here](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)  

## 部署時：

專案Push時，Jenkins會將ServerConnectData.json換成ServerConnectData2.json的內容，即會執行以下指令：  
cp -f src/main/webapp/WEB-INF/ServerConnectData2.json src/main/webapp/WEB-INF/ServerConnectData.json 
為了讓程式部署上去時，連線Host會換成127.0.0.1# rewabo
1
