# Creating Tests
The toolkit integrates natively with Apache JMeter. Its elements appear in JMeter sections as native JMeter components with the **Bot Service:** prefix.

We have basically two types of test elements: **Configuration** elements and **Sampler** elements. In the following sections we have a description of when and how to use each of the possible test elements.

## Config Element > Bot Service: Configuration
Beyond JMeter's elements required for every stress tes, like thread groups and so on, which are beyond the scope of this guide (you can find more info [here](https://jmeter.apache.org/usermanual/get-started.html) if you are a JMeter newbie), the first item you have to add to your Bot test is the **Configuration** element. 

The **Configuration** element specifies connectivity and identity information used during the tests.

![Config Element > Bot Service: Configuration](https://github.com/damadei/BotServiceStressToolkit/blob/master/docs/imgs/01-config.png)

Describing each field we have:

* **Bot URL**: is the endpoint where your bot is deployed. If your bot is running locally, the value will probably be `http://localhost:3978/api/messages`. However if your Bot is already deployed to Bot Service, this value will be similar to `https://<your service name>.azurewebsites.net/api/messages`.

* **Callback URL**: is the callback URL so your bot can return messages to JMeter. This is the address of the server that JMeter spins up and should be `http://localhost:callback-port` if running locally. `http://VM public IP:callback-port` if running in Azure from a VM pointing to Bot Service not integrated to a VNET or `http://VM private IP:callback-port` if running in Azure from a VM in the same VNET integrated to Bot service or running Bot Service over App Service Environment. If running against Bot Service, running using the integrated VNET or ASE is recommended to avoid traffic flowing through the internet. 

* **Channel Id**: id of the channel emulated by JMeter. If the value specified is emulator, Bot allows calls without security enabled. If any other value specified, security configuration is mandatory and the **Security Configuration** element should be included in the test. **Bot Service: Security Configuration** element is detailed [here](#config-element-bot-service-security-configuration).

* **From (Default Member Id)**: id of the user making requests. If item **Gen random user id per thread** is used, this value is ignored.

* **Gen random user id per thread**: generates a new user per thread so that there's no conflict between requests with the same user id. It's recommended to keep this value checked to avoid concurrency issues.

* **Recipient (Recipient Member Id)**: id of the recipient for the message. Generally `default-bot`.

* **Callback Server Listen Host**: hostname or ip to bind the callback server to. If empty, callback server binds to all network interfaces.

* **Callback Server Listen Port**: Port of the callback server started by JMeter to receive callback messages.

* **Response Timeout**: Time (in seconds) to wait for a response before considering the test step as failed because no response was received. Remember that responses are asynchronous and are received by a callback HTTP POST made from the bot to the JMeter internal server that we spin up and this timeout is the amount of time we wait for such messages with the same correlation id.


## Config Element > Bot Service: Security Configuration
Bots deployed to Bot Service will generally require a security token for calls to be made to the APIs.

For these cases, the Security Configuration element allows the specification of the App ID and App Secret so a secure token can be generated and used to make API calls. 

The items of the **Bot Service: Security Configuration** element are:

![Config Element > Bot Service: Security Configuration](https://github.com/damadei/BotServiceStressToolkit/blob/master/docs/imgs/02-security.PNG)

* **App ID**: Application ID created for the Bot.

* **App Secret**: Application Secret created for the Bot.


## Sampler > Bot Service: Conversation Update
The first item to start the conversation with the Bot is the **Bot Service: Conversation Update**

This element allows one to start a conversation with the Bot and receives the response(s) if any.

It's comprised of the following fields:

![Sampler > Bot Service: Conversation Update](https://github.com/damadei/BotServiceStressToolkit/blob/master/docs/imgs/03-convupd.PNG)

* **# of Responses Expected**: Number of responses expected after the conversation is started/updated.

* **Members added**: Members which were added to the conversation, separated by comma. If you want to add the current user which is random and attached to the thread, the special syntax **${user}** can be used.


## Sampler > Bot Service: Message
The **Bot Service: Message** sampler is where things start to get cool. This is what we use to send messages to the bot and receive responses correlated to the request message.

It's comprised of the following fields:

![Sampler > Sampler > Bot Service: Message](https://github.com/damadei/BotServiceStressToolkit/blob/master/docs/imgs/04-message.PNG)

* **Text**: Text of the message to be sent.

* **Text Format**: Text format of the message (for example `plain`).

* **Locale**: Locale of the message (for example `en-US`).

* **# of Responses Expected**: Number of responses expected after the the message is sent. When more than one response messages should be expected, enter the value here and the message text will be concatenated to the response separated by a new line.


## Assertion > Response Assertion
Although not created specifically for the Bot test scenario as it's a native JMeter test element, the **Response Assertion** assertion allows us to validate if the Bot is responding accordingly to what we expect. Please refer to JMeter documentation on how it can be used by clicking [here](https://jmeter.apache.org/usermanual/component_reference.html#Response_Assertion).

![Response Assertion](https://github.com/damadei/BotServiceStressToolkit/blob/master/docs/imgs/05-assertion.PNG)
