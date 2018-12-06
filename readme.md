# Bot Service Stress Toolkit
This is a toolkit for Apache JMeter to stress test bots built using Azure Bot Builder SDK.

[Apache JMeter](http://jmeter.apache.org) is an open source stress testing tool which simplifies authoring, execution and analysis of stress tests using a friendly interface. 

This toolkit provides the following features:
* Leverages JMeter to make it easy and visual to perform stress tests on Bots. 
* Capable of not only making requests to the Bots but also receive the responses.
* Capable of asserting responses if needed.
* Capable of loading test data from files or other data sources supported by JMeter (sample [here](https://jmeter.apache.org/usermanual/component_reference.html#CSV_Data_Set_Config)).
* Measures throughput and latency of your Bot Service applications.
* Able to stress test bots running locally or in Azure Bot Service.

## Why do I need a toolkit to test my bots?
Click [HERE](./docs/why.md) to understand why you need a toolkit to test your bots with Apache JMeter. 

## Setting up JMeter with the Toolkit
Click [HERE](./docs/setup.md) to understand how to setup the toolkit.

## Creating the Tests
Click [HERE](./docs/creating.md) to learn how to create your Bot stress tests.

## Running Tests
Click [HERE](./docs/running.md) to understand how to run your tests locally and from Azure.