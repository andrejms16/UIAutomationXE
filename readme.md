# Automation Test Task

The main goal of this project is validating automation skills in https://www.xe.com/currencyconverter/.

## Project Structure
```
.
|__src
   |__main
       |__java
          |__com.xe
             |__page
                |CurrencyConverterPage.java
             |__step
                |CurrencyConverterSteps.java
   |__test
       |__java
          |__com
             |__suporte
                |Driver.java
                |PropertiesCache.java                
             |__test
                |CurrencyConverterTest.java
       |__resources
          |__config.properties
|__target
|__pom.xml
```

### Before Running
- Set Chrome Driver Path in config.properties.

### Run
mvn clean test

### Allure Report
allure serve /home/path/to/project/target/surefire-reports/

### Surefire Report
You can find the surefire report in /home/path/to/project/target/surefire-reports/index.html