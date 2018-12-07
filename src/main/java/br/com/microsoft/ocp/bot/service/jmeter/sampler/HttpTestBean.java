package br.com.microsoft.ocp.bot.service.jmeter.sampler;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpTestBean extends BaseBotSampler implements TestBean {

	private static final Logger log = LoggerFactory.getLogger(HttpTestBean.class);

	private static final long serialVersionUID = 240L;

	@Override
	public SampleResult sample(Entry entry) {
		SampleResult res = new SampleResult();
		res.setSampleLabel(getName());
		res.sampleStart();
		res.setDataType(SampleResult.TEXT);

		try {
			JMeterContext context = getThreadContext();
			JMeterVariables vars = context.getVariables();

			String body = Unirest.get("http://www.uol.com.br").asString().getBody();

			res.setResponseData(body, null);
			res.sampleEnd();
			res.setSuccessful(true);

		} catch (UnirestException e) {
			res.setResponseData(e.toString(), null);
			res.sampleEnd();
			res.setSuccessful(false);
		} 

		return res;
	}

}
