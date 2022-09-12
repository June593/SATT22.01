    # Automation Test for Safe Railway Website
### Link Website
http://raillog.somee.com
![Picture](http://raillog.somee.com/Image/banner.jpg)

### **1.Installation**
- Install Intellij IDE software
- Install Java Development Kit (JDK) 8
- Install Lombok from Plugins and add in `<dependency> `of pom.xml

### 2. Run project and get report
Enter the type of browser you want to run in the 'value' parameter into the file testng.xml.
Ex: `chrome`, `firefox`, `safari`, `edge`...
> ` <parameter name="browser" value="chrome"></parameter>`
#### Run project
> 1. From **Root folder** of Project
> 2. Enter `mvn test`
#### Get report
After running the project, you can get the report from the following path:
*'root folder' + /target/surefire-reports/emailable-report.html*
