package br.com.microsoft.ocp.bot.service.jmeter.sampler.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import br.com.microsoft.ocp.bot.service.jmeter.sampler.MessageSampler;

/**
 * Example Sampler (non-Bean version)
 *
 * This class is responsible for ensuring that the Sampler data is kept in step
 * with the GUI.
 *
 * The GUI class is not invoked in non-GUI mode, so it should not perform any
 * additional setup that a test would need at run-time
 */
public class MessageSamplerGui extends AbstractSamplerGui {
	public static final long serialVersionUID = 240L;

	private static final int MAX_RESPONSES_ALLOWED = 99;

	private static final String NUMBER_OF_RESPONSES_EXPECTED_LABEL = "# of Responses Expected:";
	private static final String MESSAGE_TEXT_LABEL = "Text:";
	private static final String MESSAGE_TEXT_FORMAT_LABEL = "Text Format:";
	private static final String LOCALE_LABEL = "Locale:";

	private javax.swing.JLabel messageTextLabel = new JLabel();
	private javax.swing.JLabel messageTextFormatLabel = new JLabel();
	private javax.swing.JLabel localeLabel = new JLabel();
	private javax.swing.JLabel numOfResponsesExpectedLabel = new JLabel();
	private javax.swing.JScrollPane messageTextAreaScrollPane = new javax.swing.JScrollPane();

	private javax.swing.JTextArea messageTextTextArea = new JTextArea();
	private javax.swing.JTextField messageTextFormatTextField = new JTextField();
	private javax.swing.JTextField localeTextField = new JTextField();
	private javax.swing.JTextField numOfResponsesExpectedTextField;

	public MessageSamplerGui() {
		init();
	}

	@Override
	public String getStaticLabel() {
		return "Bot Service: Message";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(TestElement element) {
		super.configure(element);
		numOfResponsesExpectedTextField
				.setText(String.valueOf(element.getPropertyAsInt(MessageSampler.NUM_OF_EXPECTED_RESPONSES)));
		messageTextTextArea.setText(element.getPropertyAsString(MessageSampler.MESSAGE_TEXT));
		messageTextFormatTextField.setText(element.getPropertyAsString(MessageSampler.MESSAGE_TEXT_FORMAT));
		localeTextField.setText(element.getPropertyAsString(MessageSampler.LOCALE));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TestElement createTestElement() {
		MessageSampler sampler = new MessageSampler();
		modifyTestElement(sampler);
		return sampler;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyTestElement(TestElement te) {
		te.clear();
		configureTestElement(te);

		te.setProperty(MessageSampler.NUM_OF_EXPECTED_RESPONSES,
				Integer.parseInt(numOfResponsesExpectedTextField.getText()));
		te.setProperty(MessageSampler.MESSAGE_TEXT, messageTextTextArea.getText());
		te.setProperty(MessageSampler.MESSAGE_TEXT_FORMAT, messageTextFormatTextField.getText());
		te.setProperty(MessageSampler.LOCALE, localeTextField.getText());
	}

	/*
	 * Helper method to set up the GUI screen
	 */
	private void init() { // WARNING: called from ctor so must not be overridden
							// (i.e. must be private or final)
		// Standard setup
		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());
		add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title

		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);

		numOfResponsesExpectedLabel = new javax.swing.JLabel(NUMBER_OF_RESPONSES_EXPECTED_LABEL);

		messageTextTextArea.setColumns(20);
		messageTextTextArea.setRows(5);
		messageTextAreaScrollPane.setViewportView(messageTextTextArea);

		NumberFormat format = new DecimalFormat("#0");
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(MAX_RESPONSES_ALLOWED);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		numOfResponsesExpectedTextField = new javax.swing.JFormattedTextField(formatter);
		numOfResponsesExpectedLabel.setText(NUMBER_OF_RESPONSES_EXPECTED_LABEL);
		messageTextLabel.setText(MESSAGE_TEXT_LABEL);
		messageTextFormatLabel.setText(MESSAGE_TEXT_FORMAT_LABEL);
		localeLabel.setText(LOCALE_LABEL);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(layout);

		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(messageTextLabel, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(messageTextFormatLabel, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(localeLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(numOfResponsesExpectedLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(localeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 244,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(numOfResponsesExpectedTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
										244, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(messageTextFormatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 244,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(messageTextAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 585,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(0, 469, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(messageTextAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addGap(30, 30, 30).addComponent(messageTextLabel)))
				.addGap(10, 10, 10)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(messageTextFormatLabel).addComponent(messageTextFormatTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(localeLabel).addComponent(localeTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(numOfResponsesExpectedLabel).addComponent(numOfResponsesExpectedTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap(540, Short.MAX_VALUE)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearGui() {
		super.clearGui();

		numOfResponsesExpectedTextField.setText(String.valueOf(MessageSampler.NUM_OF_EXPECTED_RESPONSES_DEFAULT_VALUE));
		messageTextTextArea.setText(MessageSampler.MESSAGE_TEXT_DEFAULT_VALUE);
		messageTextFormatTextField.setText(MessageSampler.MESSAGE_TEXT_FORMAT_DEFAULT_VALUE);
		localeTextField.setText(MessageSampler.LOCALE_DEFAULT_VALUE);
	}

	@Override
	public String getLabelResource() {
		return null;
	}
}
