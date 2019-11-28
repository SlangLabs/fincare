Slang Sample App
----------------

This is a starter shell app for trying out a Slang integration. 

To use this, 

- Create an account in the Slang Console (https://console.slanglabs.in). 
- You can either create a new buddy there or you can use the sample demo buddy
- You need to publish the buddy to stage before you can continue

Run the setup script to generate the starter code and configure this sample app for the
given buddy

$ node slang_setup --api-key <api_key> --buddy-id <buddy_id> [--package <package name>]

(Note: This is a node js script)

This will generate a bunch of files into this empty android app. 

You can now build and run this Android project to experience Slang for the given buddy. 

How to integrate Slang into your actual app?
--------------------------------------------

To do that, you can copy the "SlangInterface.java" that would be available in

app/src/main/java/<package path>/SlangInterface.java

over to your application code (remember to make sure that the package name is fixed up to match your code base)

And follow the instructions in https://docs.slanglabs.in/slang/getting-started/for-android-apps


