package br.com.microsoft.ocp.bot.service.jmeter.config.gui;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.layout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.microsoft.ocp.bot.service.jmeter.config.BotServiceSecurityConfig;
import br.com.microsoft.ocp.bot.service.jmeter.sampler.ConversationUpdateSampler;

public class BotServiceSecurityConfigDefaultsGui extends AbstractConfigGui {

	private static final String APP_ID_LABEL = "App ID: ";
	private static final String CLIENT_SECRET_LABEL = "Client Secret: ";

	private static final long serialVersionUID = -1551175774481565064L;

	private static final Logger log = LoggerFactory.getLogger(ConversationUpdateSampler.class);

	// Variables declaration - do not modify
	private javax.swing.JLabel appIdLabel;
	private javax.swing.JLabel clientSecretLabel;

	private javax.swing.JTextField appIdTextField;
	private javax.swing.JPasswordField clientSecretTextField;

	public BotServiceSecurityConfigDefaultsGui() {
		super();
		init();
	}

	@Override
	public String getLabelResource() {
		return null;
	}

	@Override
	public String getStaticLabel() {
		return "Bot Service: Security Configuration";
	}

	/**
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#createTestElement()
	 */
	@Override
	public TestElement createTestElement() {
		log.debug("createTestElement()");
		BotServiceSecurityConfig config = new BotServiceSecurityConfig();
		modifyTestElement(config);
		return config;
	}

	/**
	 * Modifies a given TestElement to mirror the data in the gui components.
	 *
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
	 */
	@Override
	public void modifyTestElement(TestElement config) {
		config.clear();
		configureTestElement(config);

		log.debug("modifyTestElement(%s)", config.toString());

		if (config instanceof BotServiceSecurityConfig) {
			config.setProperty(BotServiceSecurityConfig.APP_ID, appIdTextField.getText());
			config.setProperty(BotServiceSecurityConfig.CLIENT_SECRET, new String(clientSecretTextField.getPassword()));
		}
	}

	/**
	 * Implements JMeterGUIComponent.clearGui
	 */
	@Override
	public void clearGui() {
		log.debug("clearGui()");

		super.clearGui();
		appIdTextField.setText("");
		clientSecretTextField.setText("");
	}

	@Override
	public void configure(TestElement el) {
		super.configure(el);

		log.debug("configure(%s)", el.toString());

		if (el instanceof BotServiceSecurityConfig) {
			appIdTextField.setText(el.getPropertyAsString(BotServiceSecurityConfig.APP_ID));
			clientSecretTextField.setText(el.getPropertyAsString(BotServiceSecurityConfig.CLIENT_SECRET));
		} else {
			log.debug("el is not instanceof: " + el);
		}
	}

	private void init() {
		setLayout(new BorderLayout());
		setBorder(makeBorder());
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new VerticalLayout(5, VerticalLayout.BOTH));
		northPanel.add(makeTitlePanel());
		add(northPanel, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();

		clientSecretLabel = new javax.swing.JLabel();
		appIdLabel = new javax.swing.JLabel();

		appIdTextField = new javax.swing.JTextField();
		clientSecretTextField = new javax.swing.JPasswordField();

		clientSecretLabel.setText(CLIENT_SECRET_LABEL);
		appIdLabel.setText(APP_ID_LABEL);

		GroupLayout layout = new GroupLayout(mainPanel);
		mainPanel.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(layout.createSequentialGroup().addComponent(appIdLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(appIdTextField))
								.addGroup(layout.createSequentialGroup().addComponent(clientSecretLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(clientSecretTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
												526, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(0, 107, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(appIdLabel).addComponent(appIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(clientSecretLabel).addComponent(clientSecretTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap(203, Short.MAX_VALUE)));
		add(mainPanel, BorderLayout.CENTER);
	}
}