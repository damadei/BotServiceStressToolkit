# Bot Service Stress Toolkit
This is a toolkit for Apache JMeter to stress test bots build using Azure Bot Builder SDK.

[Apache JMeter](http://jmeter.apache.org) is an open source stress testing tool which simplifies authoring, execution and analysis of stress tests using a friendly interface. 

This toolkit leverages JMeter to make it easy to perform stress tests on Bots, something which is not so trivial given the async nature of Bots, and is capable of not only making requests to the bots but also to emulate a channel, receive the responses, assert it if needed and measure things like throughput and latency of your Bot Service applications.

## Why do I need a toolkit to test my bots?
Click [HERE](./docs/why.md) to understand why you need a toolkit to test your bots with Apache JMeter. 

## Setting up JMeter with the Toolkit
Click [HERE](./docs/setup.md) to understand how to setup the toolkit.

## Creating the Tests
Click [HERE](./docs/creating.md) to learn how to create your Bot stress tests.

## Running Tests
Click [HERE](./docs/run.md) to understand how to run your tests locally and from Azure.