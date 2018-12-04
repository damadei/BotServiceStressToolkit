# Why do I need a plugin to test my bots?
Bots are async in nature and this makes hard to stress test them using traditional stress testing tools which are based on HTTP protocol and hence rely on requests and responses being synchronous. 

Bots are different. In a very basic scenario, they work as APIs where a channel makes a `POST` to the Bot API, the Bot makes the processing and replies making another `POST` back to the channel, so responses are not sent as part of the original request.

The image bellow shows how this works in practice:

![How Bots Work](https://github.com/damadei/BotServiceStressToolkit/blob/master/docs/imgs/06-bots.png)

This asynchronous nature makes hard to use tools like Apache JMeter, without a specialized toolkit like this one, to test Bot applications because responses are sent as posts to a different endpoint and not as part of the response of the original HTTP request.

Using this toolkit, we can capture the response sent by the bot. This is done with JMeter emulating a channel and spinning up a server to receive the callback response. 

With this approach we are able to measure the whole cycle from a request message until a response is receivedand have a clear view on the throughput the bot is capable of handling as well as the average, median and percentile latency the Bot is capable of.