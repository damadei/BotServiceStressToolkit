package br.com.microsoft.ocp.bot.service.jmeter.config.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.layout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.microsoft.ocp.bot.service.jmeter.config.BotServiceConfig;
import br.com.microsoft.ocp.bot.service.jmeter.sampler.ConversationUpdateSampler;

public class BotServiceConfigDefaultsGui extends AbstractConfigGui {

	private static final String DEFAULT_BOT_LABEL = "default-bot";
	private static final String RECIPIENT_DEFAULT_MEMBER_ID_LABEL = "Recipient (Default Member Id): ";
	private static final String GEN_RANDOM_USER_ID_PER_THREAD_LABEL = "Gen. Random User Id per Thread: ";
	private static final String FROM_DEFAULT_MEMBER_ID_LABEL = "From (Default Member Id): ";
	private static final String CHANNEL_ID_LABEL = "Channel Id (emulator): ";
	private static final String CALLBACK_URL_LABEL = "Callback URL: ";
	private static final String CALLBACK_SERVER_LISTEN_PORT_LABEL = "Callback Server Listen Port: ";
	private static final String BOT_URL_LABEL = "Bot URL: ";
	private static final String CALLBACK_SERVER_LISTEN_HOST_LABEL = "Callback Server Listen Host: ";
	private static final String RESPONSE_TIMEOUT_LABEL = "Response Timeout: ";

	private static final int PORT_MAX_VAL = 65536;

	private static final long serialVersionUID = -1551175774481565064L;

	private static final Logger log = LoggerFactory.getLogger(ConversationUpdateSampler.class);

	// Variables declaration - do not modify
	private javax.swing.JLabel botUrlLabel;
	private javax.swing.JLabel callbackServerHostLabel;
	private javax.swing.JLabel callbackServerPortLabel;
	private javax.swing.JLabel callbackUrlLabel;
	private javax.swing.JLabel channelldLabel;
	private javax.swing.JLabel fromMemberLabel;
	private javax.swing.JLabel genRandomIdPerThreadLabel;
	private javax.swing.JLabel recipientMemberLabel;
	private javax.swing.JLabel responseTimeoutLabel;

	private javax.swing.JTextField botUrlTextField;
	private javax.swing.JTextField callbackUrlTextField;
	private javax.swing.JTextField channelIdTextField;
	private javax.swing.JTextField callbackServerHostTextField;
	private javax.swing.JTextField callbackServerPortTextField;
	private javax.swing.JTextField fromMemberTextField;
	private javax.swing.JCheckBox genRandomIdPerThreadCheckbox;
	private javax.swing.JTextField recipientMemberTextField;
	private javax.swing.JTextField responseTimeoutTextField;

	public BotServiceConfigDefaultsGui() {
		super();
		init();
	}

	@Override
	public String getLabelResource() {
		return null; // $NON-NLS-1$
	}

	@Override
	public String getStaticLabel() {
		return "Bot Service: Configuration";
	}

	/**
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#createTestElement()
	 */
	@Override
	public TestElement createTestElement() {
		log.debug("createTestElement()");
		BotServiceConfig config = new BotServiceConfig();
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

		if (config instanceof BotServiceConfig) {
			log.info("config is instanceof " + BotServiceConfig.class);
			config.setProperty(BotServiceConfig.BOT_URL, botUrlTextField.getText());
			config.setProperty(BotServiceConfig.CALLBACK_URL, callbackUrlTextField.getText());
			config.setProperty(BotServiceConfig.CHANNEL_ID, channelIdTextField.getText());
			config.setProperty(BotServiceConfig.CALLBACK_SERVER_HOST, callbackServerHostTextField.getText());
			config.setProperty(BotServiceConfig.CALLBACK_SERVER_PORT, callbackServerPortTextField.getText());
			config.setProperty(BotServiceConfig.FROM_MEMBER_ID, fromMemberTextField.getText());
			config.setProperty(BotServiceConfig.RECIPIENT_MEMBER_ID, recipientMemberTextField.getText());
			config.setProperty(BotServiceConfig.GEN_RANDOM_ID_PER_THREAD, genRandomIdPerThreadCheckbox.isSelected());
			config.setProperty(BotServiceConfig.RESPONSE_TIMEOUT, Integer.parseInt(responseTimeoutTextField.getText()));
		} else {
			log.info("config is not instanceof " + config);
		}
	}

	/**
	 * Implements JMeterGUIComponent.clearGui
	 */
	@Override
	public void clearGui() {
		log.debug("clearGui()");

		super.clearGui();
		botUrlTextField.setText(BotServiceConfig.BOT_URL_DEFAULT_VALUE);
		callbackUrlTextField.setText(BotServiceConfig.CALLBACK_URL_DEFAULT_VALUE);
		channelIdTextField.setText(BotServiceConfig.CHANNEL_ID_DEFAULT_VALUE);
		callbackServerHostTextField.setText(BotServiceConfig.CALLBACK_SERVER_HOST_DEFAULT_VALUE);
		callbackServerPortTextField.setText(BotServiceConfig.CALLBACK_SERVER_PORT_DEFAULT_VALUE);
		fromMemberTextField.setText(BotServiceConfig.FROM_MEMBER_ID_DEFAULT_VALUE);
		recipientMemberTextField.setText(BotServiceConfig.RECIPIENT_MEMBER_ID_DEFAULT_VALUE);
		genRandomIdPerThreadCheckbox.setSelected(BotServiceConfig.GEN_RANDOM_ID_PER_THREAD_DEFAULT_VALUE);
		responseTimeoutTextField.setText(String.valueOf(BotServiceConfig.RESPONSE_TIMEOUT_DEFAULT_VALUE));
	}

	@Override
	public void configure(TestElement el) {
		super.configure(el);

		log.debug("configure(%s)", el.toString());

		if (el instanceof BotServiceConfig) {
			log.info("el is instanceof " + BotServiceConfig.class);
			botUrlTextField.setText(el.getPropertyAsString(BotServiceConfig.BOT_URL));
			callbackUrlTextField.setText(el.getPropertyAsString(BotServiceConfig.CALLBACK_URL));
			channelIdTextField.setText(el.getPropertyAsString(BotServiceConfig.CHANNEL_ID));
			callbackServerHostTextField.setText(el.getPropertyAsString(BotServiceConfig.CALLBACK_SERVER_HOST));
			callbackServerPortTextField.setText(el.getPropertyAsString(BotServiceConfig.CALLBACK_SERVER_PORT));
			fromMemberTextField.setText(el.getPropertyAsString(BotServiceConfig.FROM_MEMBER_ID));
			recipientMemberTextField.setText(el.getPropertyAsString(BotServiceConfig.RECIPIENT_MEMBER_ID));
			genRandomIdPerThreadCheckbox
					.setSelected(el.getPropertyAsBoolean(BotServiceConfig.GEN_RANDOM_ID_PER_THREAD));
			responseTimeoutTextField.setText(String.valueOf(el.getPropertyAsInt(BotServiceConfig.RESPONSE_TIMEOUT)));
		} else {
			log.info("el is not instanceof: " + el);
		}
	}

	private void init() { // WARNING: called from ctor so must not be overridden
							// (i.e. must be private or final)
		setLayout(new BorderLayout());
		setBorder(makeBorder());
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new VerticalLayout(5, VerticalLayout.BOTH));
		northPanel.add(makeTitlePanel());
		add(northPanel, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel();

		callbackServerHostLabel = new javax.swing.JLabel();
		botUrlTextField = new javax.swing.JTextField();
		botUrlLabel = new javax.swing.JLabel();
		callbackServerHostTextField = new javax.swing.JTextField();
		callbackServerPortLabel = new javax.swing.JLabel();
		callbackUrlLabel = new javax.swing.JLabel();
		callbackUrlTextField = new javax.swing.JTextField();
		channelldLabel = new javax.swing.JLabel();
		channelIdTextField = new javax.swing.JTextField();
		fromMemberLabel = new javax.swing.JLabel();
		fromMemberTextField = new javax.swing.JTextField();
		genRandomIdPerThreadLabel = new javax.swing.JLabel();
		genRandomIdPerThreadCheckbox = new javax.swing.JCheckBox();
		recipientMemberLabel = new javax.swing.JLabel();
		recipientMemberTextField = new javax.swing.JTextField();
		responseTimeoutLabel = new JLabel();

		NumberFormat format = new DecimalFormat("####0");
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(PORT_MAX_VAL);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		callbackServerPortTextField = new javax.swing.JFormattedTextField(formatter);

		format = new DecimalFormat("##0");
		formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(PORT_MAX_VAL);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		responseTimeoutTextField = new javax.swing.JFormattedTextField(formatter);

		callbackServerHostLabel.setText(CALLBACK_SERVER_LISTEN_HOST_LABEL);
		botUrlLabel.setText(BOT_URL_LABEL);
		callbackServerPortLabel.setText(CALLBACK_SERVER_LISTEN_PORT_LABEL);
		callbackUrlLabel.setText(CALLBACK_URL_LABEL);
		channelldLabel.setText(CHANNEL_ID_LABEL);
		fromMemberLabel.setText(FROM_DEFAULT_MEMBER_ID_LABEL);
		genRandomIdPerThreadLabel.setText(GEN_RANDOM_USER_ID_PER_THREAD_LABEL);
		recipientMemberLabel.setText(RECIPIENT_DEFAULT_MEMBER_ID_LABEL);
		recipientMemberTextField.setText(DEFAULT_BOT_LABEL);
		responseTimeoutLabel.setText(RESPONSE_TIMEOUT_LABEL);

		GroupLayout layout = new GroupLayout(mainPanel);
		mainPanel.setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(layout.createSequentialGroup().addComponent(genRandomIdPerThreadLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(genRandomIdPerThreadCheckbox))
								.addGroup(layout.createSequentialGroup().addComponent(recipientMemberLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(recipientMemberTextField))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(callbackServerPortLabel)
												.addComponent(fromMemberLabel)
												.addComponent(callbackServerHostLabel))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(fromMemberTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE, 343,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(callbackServerHostTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE, 344,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(callbackServerPortTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE, 140,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createSequentialGroup().addComponent(responseTimeoutLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(responseTimeoutTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
												153, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
												layout.createSequentialGroup().addComponent(channelldLabel)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(channelIdTextField))
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
												layout.createSequentialGroup().addComponent(callbackUrlLabel)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(callbackUrlTextField))
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
												layout.createSequentialGroup().addComponent(botUrlLabel)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(botUrlTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE, 621,
																javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(549, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(30, 30, 30)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(botUrlLabel).addComponent(botUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(callbackUrlLabel).addComponent(callbackUrlTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(channelldLabel).addComponent(channelIdTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(fromMemberLabel).addComponent(fromMemberTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(genRandomIdPerThreadLabel).addComponent(genRandomIdPerThreadCheckbox))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(recipientMemberLabel).addComponent(recipientMemberTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(callbackServerHostLabel).addComponent(callbackServerHostTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(callbackServerPortLabel).addComponent(callbackServerPortTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(12, 12, 12)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(responseTimeoutLabel).addComponent(responseTimeoutTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap(494, Short.MAX_VALUE)));

		add(mainPanel, BorderLayout.CENTER);
	}
}