This project compilation and running is dependent on Gradle version 4.9 and above. If it is not present in your OS please follow instructions from
https://gradle.org/install/

1) Move the terminal to the project location. When you type "ls" command in the terminal you should see build.gradle file
2) Type command gradle run --args='fullLocationOfData fullLocationToSave'. Example:-

gradle run --args='/Users/sandeep/nursery.csv /Users/sandeep/nursery_output.xml'

3) Please only use full path for both data location and save location
4) The location to save should have folders. This program won't create folders. Example:- In above command command Both "Users" and "sandeep" folder should exist for nursery_output.xml file to produce.