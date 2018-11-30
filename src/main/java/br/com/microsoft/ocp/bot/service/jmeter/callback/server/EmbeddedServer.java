package br.com.microsoft.ocp.bot.service.jmeter.callback.server;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedServer {
	private static final Logger log = LoggerFactory.getLogger(EmbeddedServer.class);

	private static volatile Server server = null;

	private static int SERVER_START_TIMEOUT = 30000;

	public static void stop() throws Exception {
		log.info("Stopping callback server.");
		server.stop();
	}

	public static synchronized void ensureServerRunning(String hostname, int port) {
		log.info("Waiting for server to start...");

		if (server == null || !server.isRunning()) {
			start(hostname, port);
		}

		long startTime = System.currentTimeMillis();

		while (server == null || !server.isRunning()) {
			try {
				Thread.sleep(1000);
				log.info("Waiting for server to start...");
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			if (timeoutExpired(startTime)) {
				throw new IllegalStateException("Server did not start in the timeout specified");
			}
		}
	}

	private static boolean timeoutExpired(long startTime) {
		return (System.currentTimeMillis() - startTime) > SERVER_START_TIMEOUT;
	}

	private static void start(String hostname, int port) {
		log.info("Starting callback server.");

		new Thread(new Runnable() {
			@Override
			public void run() {
				_start(hostname, port);
			}
		}).start();
	}

	private static void _start(String hostname, int port) {
		InetSocketAddress addr = null;

		if (StringUtils.isNotEmpty(hostname)) {
			addr = new InetSocketAddress(hostname, port);
		} else {
			addr = new InetSocketAddress(port);
		}

		log.info("Callback server listening on " + addr);

		server = new Server(addr);
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler.setContextPath("/");
		server.setHandler(handler);

		ServletHolder jerseyServlet = handler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.packages",
				"br.com.microsoft.ocp.bot.service.jmeter.callback.server");

		try {
			server.start();
			log.info("Callback server started.");
			server.join();
		} catch (Exception e) {
			log.error("Error on callback server init", e);
			throw new RuntimeException(e);
		}
	}

}
