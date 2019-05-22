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

import br.com.microsoft.ocp.bot.service.jmeter.sampler.EventSampler;

public class EventSamplerGui extends AbstractSamplerGui {
	public static final long serialVersionUID = 240L;
	
	private static final int MAX_RESPONSES_ALLOWED = 99;
	
	private static final String NUMBER_OF_RESPONSES_EXPECTED_LABEL = "# of Responses Expected:";
	private static final String EVENT_NAME_LABEL = "Event name:";
	private static final String CHANNELDATA_LABEL = "Channeldata (JSON string):";
	
	private javax.swing.JLabel numOfResponsesExpectedLabel = new JLabel();
	private javax.swing.JLabel eventNameLabel = new JLabel();
	private javax.swing.JLabel channeldataLabel = new JLabel();
	private javax.swing.JScrollPane channeldataAreaScrollPane = new javax.swing.JScrollPane();
	
	private javax.swing.JTextArea channeldataTextArea = new JTextArea();
	private javax.swing.JTextField eventNameTextField = new JTextField();
	private javax.swing.JTextField numOfResponsesExpectedTextField = new JTextField();
	
	public EventSamplerGui() {
		init();
	}
	
	@Override
	public String getStaticLabel() {
		return "Bot Service: Event";
	}

	@Override
	public String getLabelResource() {
		return null;
	}
	
	@Override
	public void configure(TestElement element) {
		super.configure(element);
		numOfResponsesExpectedTextField.setText(String.valueOf(element.getPropertyAsInt(EventSampler.NUM_OF_EXPECTED_RESPONSES)));
		channeldataTextArea.setText(element.getPropertyAsString(EventSampler.CHANNELDATA));
		eventNameTextField.setText(element.getPropertyAsString(EventSampler.EVENT_NAME));
	}

	@Override
	public TestElement createTestElement() {
		EventSampler sampler = new EventSampler();
		modifyTestElement(sampler);
		return sampler;
	}

	@Override
	public void modifyTestElement(TestElement te) {
		te.clear();
		configureTestElement(te);
		
		te.setProperty(EventSampler.NUM_OF_EXPECTED_RESPONSES,
				Integer.parseInt(numOfResponsesExpectedTextField.getText()));
		te.setProperty(EventSampler.CHANNELDATA, channeldataTextArea.getText());
		te.setProperty(EventSampler.EVENT_NAME, eventNameTextField.getText());		
	}
	
	/*
	 * Helper method to set up the GUI screen
	 */
	private void init() {
		// Standard setup
		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());
		add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title

		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);

		numOfResponsesExpectedLabel = new javax.swing.JLabel(NUMBER_OF_RESPONSES_EXPECTED_LABEL);
		
		channeldataTextArea.setColumns(20);
		channeldataTextArea.setRows(5);
		channeldataAreaScrollPane.setViewportView(channeldataTextArea);
		
		NumberFormat format = new DecimalFormat("#0");
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(MAX_RESPONSES_ALLOWED);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		numOfResponsesExpectedTextField = new javax.swing.JFormattedTextField(formatter);
		numOfResponsesExpectedLabel.setText(NUMBER_OF_RESPONSES_EXPECTED_LABEL);
		channeldataLabel.setText(CHANNELDATA_LABEL);
		eventNameLabel.setText(EVENT_NAME_LABEL);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(layout);

		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(channeldataLabel, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(eventNameLabel, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(numOfResponsesExpectedLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(eventNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 244,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(numOfResponsesExpectedTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
										244, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(channeldataAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 585,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(0, 469, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(channeldataAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addGap(30, 30, 30).addComponent(channeldataLabel)))
				.addGap(10, 10, 10)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(eventNameLabel).addComponent(eventNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(numOfResponsesExpectedLabel).addComponent(numOfResponsesExpectedTextField,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap(540, Short.MAX_VALUE)));
	}
	
	@Override
	public void clearGui() {
		super.clearGui();

		numOfResponsesExpectedTextField.setText(String.valueOf(EventSampler.NUM_OF_EXPECTED_RESPONSES_DEFAULT_VALUE));
		channeldataTextArea.setText(EventSampler.CHANNELDATA_DEFAULT_VALUE);
		eventNameTextField.setText(EventSampler.EVENT_NAME_DEFAULT_VALUE);
	}

}
