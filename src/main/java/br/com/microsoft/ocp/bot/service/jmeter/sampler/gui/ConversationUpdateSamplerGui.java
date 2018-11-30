package br.com.microsoft.ocp.bot.service.jmeter.sampler.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import br.com.microsoft.ocp.bot.service.jmeter.sampler.ConversationUpdateSampler;

/**
 * Example Sampler (non-Bean version)
 *
 * This class is responsible for ensuring that the Sampler data is kept in step
 * with the GUI.
 *
 * The GUI class is not invoked in non-GUI mode, so it should not perform any
 * additional setup that a test would need at run-time
 */
public class ConversationUpdateSamplerGui extends AbstractSamplerGui {

	private static final String MEMBERS_ADDED_LABEL = "Members Added (comma sep.)";
	private static final String NUMBER_OF_RESPONSES_EXPECTED_LABEL = "# of Responses Expected";

	private static final long serialVersionUID = 240L;
	private static final int MAX_RESPONSES_ALLOWED = 99;

	private javax.swing.JLabel membersAddedLabel;
	private javax.swing.JLabel numOfResponsesExpectedLabel;

	private javax.swing.JTextField membersAddedTextField;
	private javax.swing.JTextField numOfResponsesExpectedTextField;

	public ConversationUpdateSamplerGui() {
		init();
	}

	@Override
	public String getStaticLabel() {
		return "Bot Service: Conversation Update";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(TestElement element) {
		super.configure(element);
		membersAddedTextField.setText(element.getPropertyAsString(ConversationUpdateSampler.MEMBERS_ADDED));
		numOfResponsesExpectedTextField
				.setText(String.valueOf(element.getPropertyAsInt(ConversationUpdateSampler.NUM_OF_EXPECTED_RESPONSES)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TestElement createTestElement() {
		ConversationUpdateSampler sampler = new ConversationUpdateSampler();
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

		te.setProperty(ConversationUpdateSampler.MEMBERS_ADDED, membersAddedTextField.getText());
		te.setProperty(ConversationUpdateSampler.NUM_OF_EXPECTED_RESPONSES,
				Integer.parseInt(numOfResponsesExpectedTextField.getText()));
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

		numOfResponsesExpectedLabel = new javax.swing.JLabel();
		membersAddedLabel = new javax.swing.JLabel();

		NumberFormat format = new DecimalFormat("#0");
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(MAX_RESPONSES_ALLOWED);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		numOfResponsesExpectedTextField = new javax.swing.JFormattedTextField(formatter);
		membersAddedTextField = new javax.swing.JTextField();

		numOfResponsesExpectedLabel.setText(NUMBER_OF_RESPONSES_EXPECTED_LABEL);
		membersAddedLabel.setText(MEMBERS_ADDED_LABEL);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addGroup(layout.createSequentialGroup().addComponent(numOfResponsesExpectedLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(numOfResponsesExpectedTextField,
												javax.swing.GroupLayout.PREFERRED_SIZE, 327,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
										layout.createSequentialGroup().addComponent(membersAddedLabel)
												.addGap(42, 42, 42).addComponent(membersAddedTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE, 327,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(32, 32, 32)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(numOfResponsesExpectedLabel).addComponent(numOfResponsesExpectedTextField,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(membersAddedLabel).addComponent(membersAddedTextField,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearGui() {
		super.clearGui();

		membersAddedTextField.setText(ConversationUpdateSampler.MEMBERS_ADDED_DEFAULT_VALUE);
		numOfResponsesExpectedTextField.setText(String.valueOf(ConversationUpdateSampler.NUM_OF_EXPECTED_RESPONSES_DEFAULT_VALUE));
	}

	@Override
	public String getLabelResource() {
		return null;
	}
}
