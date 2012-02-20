package ru.redsolution.bst.ui;

import ru.redsolution.bst.R;
import ru.redsolution.bst.data.BST;
import ru.redsolution.bst.data.DocumentType;
import ru.redsolution.bst.ui.dialog.AuthorizationDialogBuilder;
import ru.redsolution.dialogs.AcceptAndDeclineDialogListener;
import ru.redsolution.dialogs.DialogBuilder;
import android.app.Dialog;
import android.os.Bundle;
import android.preference.Preference;

/**
 * Окно настроет.
 * 
 * @author alexander.ivanov
 * 
 */
public class SettingsActivity extends BaseSettingsActivity implements
		AcceptAndDeclineDialogListener {

	private static final int DIALOG_AUTH_ID = 0x10;
	private Preference loginPreference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String value = getIntent().getStringExtra(
				BaseSettingsActivity.EXTRA_TYPE);
		if (value == null)
			addPreferencesFromResource(R.xml.settings);
		else {
			DocumentType type = DocumentType.valueOf(value);
			if (type == DocumentType.supply)
				addPreferencesFromResource(R.xml.supply);
			else if (type == DocumentType.inventory)
				addPreferencesFromResource(R.xml.inventory);
			else if (type == DocumentType.demand)
				addPreferencesFromResource(R.xml.demand);
			else if (type == DocumentType.move)
				addPreferencesFromResource(R.xml.move_defaults);
			else
				throw new UnsupportedOperationException();
			setTitle(R.string.defaults_title);
		}
		loginPreference = findPreference(getString(R.string.login_key));
		if (loginPreference != null)
			loginPreference.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey().equals(getString(R.string.login_key))) {
			showDialog(DIALOG_AUTH_ID);
			return true;
		} else
			return super.onPreferenceClick(preference);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_AUTH_ID:
			return new AuthorizationDialogBuilder(this, id, this).create();
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	public void onDecline(DialogBuilder dialogBuilder) {
	}

	@Override
	protected void updateView() {
		super.updateView();
		if (loginPreference != null)
			loginPreference.setSummary(BST.getInstance().getLogin());
	}

	@Override
	protected String getMyCompany() {
		return BST.getInstance().getDefaultMyCompany();
	}

	@Override
	protected String getWarehouse() {
		return BST.getInstance().getDefaultWarehouse();
	}

	@Override
	protected String getTargetWarehouse() {
		return "";
	}

	@Override
	protected String getSupplyCompany() {
		return BST.getInstance().getDefaultSupplyCompany();
	}

	@Override
	protected String getSupplyContract() {
		return BST.getInstance().getDefaultSupplyContract();
	}

	@Override
	protected String getDemandCompany() {
		return BST.getInstance().getDefaultDemandCompany();
	}

	@Override
	protected String getDemandContract() {
		return BST.getInstance().getDefaultDemandContract();
	}

	@Override
	protected String getProject() {
		return BST.getInstance().getDefaultProject();
	}

	@Override
	protected void setMyCompany(String value) {
		BST.getInstance().setDefaultMyCompany(value);
	}

	@Override
	protected void setWarehouse(String value) {
		BST.getInstance().setDefaultWarehouse(value);
	}

	@Override
	protected void setTargetWarehouse(String value) {
	}

	@Override
	protected void setSupplyCompany(String value) {
		BST.getInstance().setDefaultSupplyCompany(value);
	}

	@Override
	protected void setSupplyContract(String value) {
		BST.getInstance().setDefaultSupplyContract(value);
	}

	@Override
	protected void setDemandCompany(String value) {
		BST.getInstance().setDefaultDemandCompany(value);
	}

	@Override
	protected void setDemandContract(String value) {
		BST.getInstance().setDefaultDemandContract(value);
	}

	@Override
	protected void setProject(String value) {
		BST.getInstance().setDefaultProject(value);
	}

}