# Android WifiP2P Communication
 Example/ Template code of how to create a TCP connection between two android phones using Wifi Direct
 
 I'm working on connecting a raspberry pi to an android through Wifi Direct as well, and I'll probably upload a repo with a detailed tutorial on how to do that once I get it to work, since there's pretty much no information on how to do it on the internet and I had to do a lot of research, learn a lot of network engineering and pretty much reinvent the wheel to get it to work. 
 
 The folder WifiDirectTestApp2 contains a fully functional android app that can be run on android phones that are connected via Wifi Direct. The java code for the app is in
 Java-Code but can also be found at WifiDirectTestApp2\app\src\main\java\com\isopod\wifidirecttestapp2
 
 TCPSocketThread.java contains a basic, single threaded example of TCP Socket communication. It can be used for one way communication or for a send and then wait for response style communication scheme. TCPSocketManager.java contains a more complex example of socket communication that has an input thread and an output thread, inter thread communication, and support for communication to and from the UI thread.
 
 email me at clashley@umd.edu if you have any questions. Have a great day!
