MovieRelations
==============

Finds related movies

Deployment instructions:

1) Download jetty from http://eclipse.org/downloads/download.php?file=/jetty/stable-9/dist/jetty-distribution-9.0.5.v20130815.zip&r=1  
2) After the above zip file is downloaded unzip the above zip file using command "unzip jetty-distribution-9.0.5.v20130815.zip"  
3) cd to directory "jetty-distribution-9.0.5.v20130815" using "cd jetty-distribution-9.0.5.v20130815". This is the jetty home directory.  
4) copy the file "movies_latest.json" in the base directory of this repo to the jetty home directory "jetty-distribution-9.0.5.v20130815".  
5) copy the war file "build/bin/movies.war" to the directory "jetty-distribution-9.0.5.v20130815/webapps" i.e. to the "webapps" folder in the jetty home "jetty-distribution-9.0.5.v20130815".  
6) Create a new directory "html" in "jetty-distribution-9.0.5.v20130815/webapps" and copy the file "welcome.html" in the base directory of this repo to the newly created "html" directory.  
7) To start the server run "java -jar start.jar" in jetty home i.e. in directory "jetty-distribution-9.0.5.v20130815"  



Accessing the application:
To access the application run hit the URL "http://hostname:8080/html/welcome.html". This show a form to enter movie names .  
After entering the movie name and you hit "submit" it will display the related movies in the order.  
