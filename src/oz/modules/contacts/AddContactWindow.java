package oz.modules.contacts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import oz.network.Client;

class AddContactWindow
{
	private static final int VMARGIN = 5;
	private static final int HMARGIN = 5;
	
	public AddContactWindow(Contacts contacts)
	{
		m_contacts = contacts;

		// Window
		m_addContactShell = new Shell(m_contacts.getView().getDisplay(), SWT.SHELL_TRIM & (~SWT.RESIZE));
		m_addContactShell.setText("Ajouter un contact");
		m_addContactShell.setLayout(new FormLayout());

		// Address label
		Label addressLabel = new Label(m_addContactShell, SWT.CENTER);
		addressLabel.setText("Adresse du contact :");
		FormData layoutData = new FormData();
		layoutData.top = new FormAttachment(0, VMARGIN);
		layoutData.left = new FormAttachment(0, HMARGIN);
		layoutData.right = new FormAttachment(100, -HMARGIN);
		addressLabel.setLayoutData(layoutData);

		// Address text
		final Text addressText = new Text(m_addContactShell, SWT.SINGLE | SWT.BORDER);
		layoutData = new FormData();
		layoutData.top = new FormAttachment(addressLabel, VMARGIN, SWT.BOTTOM);
		layoutData.left = new FormAttachment(0, HMARGIN);
		layoutData.right = new FormAttachment(100, -HMARGIN);
		layoutData.width = 200;
		addressText.setLayoutData(layoutData);

		// Confirm button
		Button confirmButton = new Button(m_addContactShell, SWT.CENTER);
		confirmButton.setText("Valider");
		layoutData = new FormData();
		layoutData.top = new FormAttachment(addressText, VMARGIN, SWT.BOTTOM);
		layoutData.left = new FormAttachment(0, HMARGIN);
		layoutData.right = new FormAttachment(100, -HMARGIN);
		confirmButton.setLayoutData(layoutData);

		// Cancel button
		Button cancelButton = new Button(m_addContactShell, SWT.CENTER);
		cancelButton.setText("Annuler");
		layoutData = new FormData();
		layoutData.top = new FormAttachment(confirmButton, VMARGIN, SWT.BOTTOM);
		layoutData.left = new FormAttachment(0, HMARGIN);
		layoutData.right = new FormAttachment(100, -HMARGIN);
		layoutData.bottom = new FormAttachment(100, -VMARGIN);
		cancelButton.setLayoutData(layoutData);

		// Events
		confirmButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				Client client = m_contacts.addContact(addressText.getText());
				if (client != null)
					m_addContactShell.close();
				else
				{
					// TODO Display error
				}
			}
		});
		addressText.addListener(SWT.DefaultSelection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				Client client = m_contacts.addContact(addressText.getText());
				if (client != null)
					m_addContactShell.close();
				else
				{
					// TODO Display error
				}
			}
		});
		cancelButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				m_addContactShell.close();
			}
		});

		// Open window
		m_addContactShell.pack();
		m_addContactShell.open();
	}

	public void run()
	{
		// Event loop
		while (!m_addContactShell.isDisposed())
		{
			if (!m_addContactShell.getDisplay().readAndDispatch())
				m_addContactShell.getDisplay().sleep();
		}
	}

	Contacts	m_contacts;
	Shell		m_addContactShell;
}