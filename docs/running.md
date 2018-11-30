#Running the Tests#
This section describes how to run tests in JMeter pointing to Bots locally, in Bot service and so on. Each scenario has it's own takeaways so it's worth reading.

##Running Locally##
Running tests locally is the easiest step, however probably it won't validate the target environmnet. It can, however, be a good option to test the script before pointing to Bot Service.

To test locally we must specify `localhost` as the hostname and the port in **Bot URL** field. Sample: `http://localhost:3978/api/messages`. We should also specify as Callback URL the `localhost:callbackport`. `callbackport` is the value specified in **Callback Server Listen Port** field which, by default, is 45678.

##Running Against Bot Service##
To be able to run the tests against Bot Service, we need the ability for Bot Service to communicate back to JMeter. This is due to the fact that JMeter is emulating a channel and also the way Bots work (for more info, please read [here]('../why.md')).

This way, we need a way for a callback from the bot to reach the server started by JMeter. We have two options in this scenario: 

* Running JMeter in a VM with a Public IP 
* Running JMeter in a VM with a Private IP 

###Running JMeter in a VM with a Public IP ###
* This scenario runs JMeter in a VM with a public IP what can be an option if it's not possible to use a private IP. 
* The port that JMeter spins up should be open in the NSG so that Bot Service (by default port 45678).
* Take into consideration that currently there's no HTTPS support so traffic will flow open what may be a security issue (HTTPS support is on the roadmap).

####Node.js Bots####
* If your bots are built on Node.js, only the private IP option is supported.

###Running JMeter in a VM with a Private IP ###
* This scenario runs JMeter in a VM with a private IP in a VNET integrated with Bot Service. 
* We have two options for that, one is integrating the App Service where your Bot runs with a VNet or running Bot Service over an App Service Environment.

## Running from the command line##
XXXX