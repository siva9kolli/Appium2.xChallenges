Mobile Automation Setup:
Appium 2.x

Requirements:
1. Java 11/17
2. Maven
3. NodeJS
4. Android Studio - download relevant packages for specific version
5. Appium Inspector

Environment variables:
JAVA_HOME,
MAVEN_HOME,
ANDROID_HOME, - sdk
android sdk, buildtools, tools, platformTool, etc to be added in the "path" system variables

Dependency: Java-client (Appium Client)

SetUp:

1. npm install -g appium@next
2. appium driver install xcuitest - ios
3. appium driver install uiautomator2 - android
4. Start Server - appium server

1. DeviceName
2. PlatformVerison
3. PlatformName
4. AutomationName

Locators:

Android:
id
accessbilityId
xpath -
uiAutomator

Setup IOS Real Device:

IOS Device Setup:

npm cache clean --force.
npm install -g appium@next
appium driver install xcuitest
appium driver install uiautomator2

* 		Install above applications and connect real device to mac
* 		Install latest xcode and latest Appium if ur device version is above ios 15…
Go to webdriveragent path (cd  /Users/shiva/.appium/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent

* 		Open . then open webdriver agnet folder
* 		Open webdriver agent project in xcode
* 		Select device
* 		Select folder icon at top left panel
* 		Select webdriveragentRunner under Signing&Capabilities tab
* 		Add any apple account and select team under drop down
* 		Go to build settings and select Product Bundle Identifier(Double click) in packing section and change until it accept in Signing&Capabilities
* 		 Get device udid[xcode->window->device&simulators->select device]
* 		Check all setup is successful or NOT using below command

xcodebuild -project WebDriverAgent.xcodeproj -scheme WebDriverAgentRunner -destination 'id=00008020-000525661A68002E' -allowProvisioningUpdates test



xcodebuild -project WebDriverAgent.xcodeproj -scheme WebDriverAgentRunner -destination 'id=00008020-000525661A68002E' -allowProvisioningUpdates test


* 		Device, Go to General-> VPN&development -> Give persmission


ideviceinstaller -l

xcrun --sdk iphonesimulator --show-sdk-version















