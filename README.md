# Distrubuted Shared WhiteBoard

## Introduction
A Java based desktop application that allows multiple users to draw shapes and chat at the same time.

## Features

* Front-end: JavaFX
* Back-end: Java
* Communication protocol: 
  * Java RMI: all communication between whiteboard server and data server, clients send request to whiteboard server.
  * MQTT: whiteboard server publish updates to all subscribed clients.
* Access control: manager has all the rights to create/delete/close a whiteboard session, as well as save shaps and import files. Manager also has the right to manage the access of visitors.
* User need to log in/sign up to join a whiteboard session, all passwords are encrypted using SHA512 algorithm.
* Multiple whiteboards coule be created, and user is able to choose whatever whiteboard to join.

## Usage

> Prerequisite: JDK 8 installed.

### Step 1
`git clone https://github.com/ZhangzihanGit/Distributed-Shared-Whiteboard-Application.git`

### Step 2
`cd Distributed-Shared-Whiteboard-Application/runnable-jar`

### Step 3
Run the data server:

`java -jar dataServer.jar -ip <network_ipv4_address> -p <port_number>`

Example:

`java -jar dataServer.jar -ip localhost -p 1111`

### Step 4:
Run the whiteboard server:

`java -jar wbServer.jar -ip <network_ipv4_address>`

Example:

`java -jar wbServer.jar -ip localhost`

### Step 5:
Run the client application:

`java -jar client.jar`

## Demo

### Whiteboard Server GUI

<img src="imgs/wb-server/welcome.png" alt="welcome page" width="400"/>

<img src="imgs/wb-server/configure-db.png" alt="configure db" width="400"/>

<img src="imgs/wb-server/configure-wb-server-port.png" alt="configure port" width="400"/>

<img src="imgs/wb-server/configure-wb-server-mqtt.png" alt="configure mqtt" width="400"/>

<img src="imgs/wb-server/wb-server-list.png" alt="wb list" width="400"/>

<img src="imgs/wb-server/wb-server-monitor.png" alt="wb monitor" width="400"/>

### Client GUI
<img src="imgs/client/welcome.png" alt="welcome page" width="400"/>

<img src="imgs/client/login.png" alt="login" width="400"/>

<img src="imgs/client/signup.png" alt="signup" width="400"/>

User can choose a role.

<img src="imgs/client/role.png" alt="role" width="400"/>

A manager has the right the create a new whiteboard.

<img src="imgs/client/manger-naming-wb.png" alt="naming wb" width="400"/>

A user can choose to join any whiteboard, if any.

<img src="imgs/client/choose-wb-to-join.png" alt="choose wb" width="400"/>

A user must be approved by the manager to join.

<img src="imgs/client/manger-reponse.png" alt="response" width="400"/>

<img src="imgs/client/homepage.png" alt="response" width="400"/>



## Contributors
Very thankful to my brilliant teammates:

- [Xiuge Chen](https://github.com/XiugeChen)
- [Chengeng Liu](https://github.com/chengengliu)
- [Guang Yang](https://github.com/yourDanmise)
