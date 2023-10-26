# Old-World-Auctions
CPS 410 Project Group

## How to run Java Application
1. Clone the repo.
2. Open code in Intellij Community Edition.
3. Navigate to src/main/java/com.example.cps410proto
4. Run the Cps410ProtoApplication.Java file.
5. Wait for console to say "Started Cps410ProtoApplication in x.xxx seconds"
6. Go to localhost:8080 in your browser.
7. Our home page should show up.

## Things that may be useful to know
1. The main framework being used to run this app is called Spring Boot.
2. HTML files are stored in src/main/resources/templates
2. CSS and JS files are stored in src/main/resources/static
3. The "Dao" Java classes will be responsible for interacting with the DB after we set it up.
4. The "Controller" Java classes will be responsible for interacting with the HTML/CSS/JS.
5. The classes in the "model" directory represent objects on our website.
6. The Java application interacts with HTML/CSS/JS through something called Thymeleaf. The HTML/JS files may need to be modified to work with Thymeleaf.
7. The Java application will have to interact with the DB using JDBC or JPA most likely. The JPA dependency is in the pom.xml file but is currently commented out as it isn't configured.
8. Ask me (Brock) if you have any other questions regarding the application as I am pretty familiar with this type of application.
