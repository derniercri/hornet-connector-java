# Hornet Java Connector

## Description

[Hornet](https://github.com/nectify/hornet) is a realtime engine that let you enhance your web application by connecting users together. [Hornet](https://github.com/nectify/hornet) is a publish/suscribe system extremely easy to use, secure and scalable. Well, [Hornet](https://github.com/nectify/hornet) will integrates very well your own existing application, no matter what language or framework you're using.

[Hornet](https://github.com/nectify/hornet) is powered by NodeJs, Socket.io and is backed by Redis.

The current library is a connector, a piece of software that will make your application able to talk with [Hornet](https://github.com/nectify/hornet). See Hornet README for the connector specification


### Hornet philosophy: core engine and connectors

Hornet is intented to act as a hub for your existing web application, offering the possibility to keep a pool of connected users and broadcasting messages to them in realtime. That's where NodeJS is goot at.

The connector is a small library that your existing application will use to connect your clients to Hornet and to broadcast message to them. When a client access a page with realtime features on it, your web application will generates a connection token to let the client subscribe to a hornet channel. 

When you want to broadcast a message to a specific channel, you'll also use the hornet connector to publish it. Using Redis publish/subscribe mechanism, Hornet core engines will be notified by this new message and transfer it to subscribed clients.

Hornet messages should always be JSON valid objects, containing at least the "type" attribute.

## Connector Installation

Add the following dependency in your pom.xml file:

	<dependency>
		<groupId>com.nectify</groupId>
		<artifactId>hornet-connector-java</artifactId>
		<version>X.Y.Z</version>
	</dependency>

## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information.

