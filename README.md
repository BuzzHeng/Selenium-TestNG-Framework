> **Note:** This framework is a work in progress. Features and documentation may change.

# Selenium-TestNG-Framework

## 🚀 Overview
A scalable test automation framework using Selenium WebDriver and TestNG for multi-website testing.

## 🎯 Key Features
- Multi-website support
- Page Object Model
- Parallel and cross-browser execution
- Data-driven testing (Excel/Properties)
- Smart wait strategies
- Screenshot on failure
- Configurable via properties files
- Maven integration
- Enhanced logging with test case name, class, line number, and timestamp

## 🏗️ Project Structure
```
Selenium-TestNG-Framework/
├─ pom.xml
├─ testng.xml
├─ logs/
│  └─ framework.log                  # runtime log output (git-ignored)
├─ test-suites/                      # additional suite XMLs (optional)
│  ├─ smoke.xml
│  └─ regression.xml
├─ test-output/                      # reports/screenshots (generated; git-ignored)
│  ├─ reports/
│  └─ screenshots/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/
│  │  │     └─ buzzheng/
│  │  │        ├─ api/              # (future) API automation components
│  │  │        ├─ config/
│  │  │        │  └─ Config.java
│  │  │        ├─ db/               # (future) DB automation components
│  │  │        ├─ ui/
│  │  │        │  ├─ component/
│  │  │        │  │  └─ BaseComponent.java
│  │  │        │  ├─ driver/
│  │  │        │  │  └─ WebDriverFactory.java
│  │  │        │  └─ rahulshetty/
│  │  │        │     ├─ component/
│  │  │        │     │  └─ rahulAbstractComponent.java
│  │  │        │     ├─ page/
│  │  │        │     │  ├─ AutomaticPracticePage.java
│  │  │        │     │  └─ WindowPracticePage.java
│  │  │        │     └─ README.md   # notes for this site’s POMs/components
│  │  │        └─ utils/
│  │  │           ├─ ExcelUtils.java
│  │  │           ├─ LoggerUtil.java
│  │  │           └─ ScreenshotUtils.java
│  │  └─ resources/
│  │     ├─ GlobalData.properties
│  │     └─ log4j2.xml
│  └─ test/
│     ├─ java/
│     │  └─ com/
│     │     └─ buzzheng/
│     │        ├─ rahulshettyacademy/
│     │        │  └─ AutomaticPracticePageTest.java
│     │        └─ TestComponents/
│     │           ├─ BaseTest.java
│     │           ├─ ExtentReporterNG.java
│     │           ├─ RetryAnalyzer.java
│     │           └─ TestListener.java
│     └─ resources/
│        └─ testdata/
```
