# Setup
Assuming you have JMeter already installed and running, setting this toolkit up is very simple.

To run the toolkit you have to build it from the sources, so make sure you have **Apache JMeter version 5 or later**, **git**, **JDK 8+** and **maven**: 

1. Clone the repository `git clone https://github.com/damadei/BotServiceStressToolkit`
1. Go to the repo directory `cd BotServiceStressToolkit`
1. Run `mvn clean package`
1. Copy the JAR generated in `target` directory to `JMETER_HOME\lib\ext` assuming JMETER_HOME is the install dir of JMeter. Important: if you have older versions of the toolkit, make sure you remove them or they will take precedence over the new version.

Restart JMeter and the toolkit actions should be available under the **Config Element** and **Sampler** context menus with the **Bot Service:** prefix.