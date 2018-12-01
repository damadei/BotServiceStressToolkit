# Creating Tests
The toolkit integrates natively to Apache JMeter and its elements appear in JMeter sections as native JMeter components.

## Config Element > Bot Service: Configuration
Beyond JMeter's elements like thread groups and so on, which are beyond the scope of this guide (you can find more info [here](https://jmeter.apache.org/usermanual/get-started.html) if you are a JMeter newbie), the first item you have to add to your test is the Configuration element. 

The configuration element specifies connectivity and identity information used during the tests.

![Config Element > Bot Service: Configuration](https://github.com/damadei/BotServiceStressToolkit/blob/master/docs/imgs/01-config.png)


Describing each field we have:

* **Bot URL**: is the endpoint where your bot is deployed. If local will probably be `http://localhost:3978/api/messages`, if already deployed to Bot Service will be `https://<your service name>.azurewebsites.net/api/messages`.

* **Callback URL**: is the callback URL so your bot can return messages to JMeter. This is the address of the server that JMeter spins up and should be `http://localhost:callback-port` if running locally. `http://VM public IP:callback-port` if running in Azure from a VM pointing to Bot Service not integrated to a VNET or `http://VM private IP:callback-port` if running in Azure from a VM in the same VNET integrated to Bot service or running Bot Service over App Service Environment. If running against Bot Service, running using the integrated VNET or ASE is recommended. 

* **Channel Id**: id of the channel emulated by JMeter. If the value specified is emulator, Bot allows calls without security enabled. If any other value specified, security configuration is mandatory. Security Config is detailed [here](#config-element-bot-service-security-configuration).

* **From (Default Member Id)**: id of the user making requests. If item **Gen random user id per thread** is used, this value is ignored.

* **Gen random user id per thread**: generates a new user per thread so that there's no conflict between requests with the same user id.

* **Recipient (Recipient Member Id)**: id of the recipient for the message.

* **Callback Server Listen Host**: hostname or ip to bind the callback server to. If empty, callback server binds to all network interfaces.

* **Callback Server Listen Port**: Port of the callback server.

* **Response Timeout**: Time (in seconds) to wait for a response before considering the test step as failed because no response was received.


## Config Element > Bot Service: Security Configuration
Bots which are deployed to Bot Service will generally require a security token for calls to be made to the APIs.

For these cases, the Security Configuration element allows the specification of the App ID and App Secret so a secure token can be generated and used to make API calls. 

The items of the Security Configuration element are:

* **App ID**: Application ID created for the Bot.
* **App Secret**: Application Secret created for the Bot.


## Sampler > Bot Service: Conversation Update
The first item to start the conversation is the **Bot Service: Conversation Update**

This element allows one to start a conversation with the Bot and receives the response(s) if any.

It's comprised of the following fields:

* **# of Responses Expected**: Number of responses expected after the conversation is started/updated.
* **Members added**: Members which were added to the conversation, separated by comma. If you want to add the current user which is random and attached to the thread, the special syntax **${user}** can be used.


## Sampler > Bot Service: Message
The **Bot Service: Message** sampler is where things start to get cool. This is where we send messages to the bot and receive responses correlated to the request message.s

This sampler allows one send a message as it .

It's comprised of the following fields:

* **Text**: Text of the message to be sent.

* **Text Format**: Text format of the message (for example `plain`).

* **Locale**: Locale of the message (for example `en-US`).

* **# of Responses Expected**: Number of responses expected after the the message is sent. When more than one response messages should be expected, enter the value here and the message text will be concatenated to the response separated by a new line.


## Assertion > Response Assertion
Although not created specifically for the **Response Assertion** assertion allows us to validate if the Bot is responding accordingly to what we expect. Please refer to JMeter documentation on how it can be used by clicking [here](https://jmeter.apache.org/usermanual/component_reference.html#Response_Assertion).
