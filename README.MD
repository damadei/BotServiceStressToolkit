# Bot Framework Stress Plugin
This is a plugin for Apache JMeter to stress test bots build using Azure Bot Builder SDK.

[Apache JMeter](http://jmeter.apache.org) is an open source stress testing tool which simplifies authoring, execution and analysis of stress tests using a friendly interface. 

This plugin leverages JMeter to make easy to perform stress tests on Bots, something which is not so trivial given the async nature of Bots, and is capable of not only making requests to the bots but also to emulate a channel, receive the responses, assert it if needed and measure things like throughput and latency of your Bot Service applications.

## Why do I need a plugin to test my bots?
Click [here](./docs/why.md) to understand why you need a plugin to test your bots with Apache JMeter. 

## Setting up JMeter with the Plugin
Click [here](./docs/setup.md) to understand how to setup the plugin.

## Creating the Tests
Click [here](./docs/creating.md) to learn how to create your Bot stress tests.

## Running Tests
Click [here](./docs/run.md) to understand how to run your tests locally and from Azure.