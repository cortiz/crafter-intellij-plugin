/*
 * Copyright (C) 2007-2018  Crafter Software Corporation. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.modules.site.ui;

import com.intellij.ide.util.projectWizard.SdkSettingsStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.application.ApplicationBundle;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.JavaSdkType;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.projectRoots.impl.JavaSdkImpl;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.ui.configuration.JdkComboBox;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.util.ui.JBUI;
import org.craftercms.modules.site.CrafterSiteImportBuilder;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

public class CrafterSitePanel extends JPanel{
	private final CrafterSiteImportBuilder builder;
	private final WizardContext context;
	private JPanel root;
	private JLabel txtSelectLib;
	private CrafterLibraryBox cmbCrafterSdk;
	private JPanel SDKPanel;
	private JdkComboBox myCbProjectJdk;
	private SdkSettingsStep panel;

	public CrafterSitePanel(final CrafterSiteImportBuilder builder, final WizardContext context) {
		super(new BorderLayout());
		add(root, BorderLayout.CENTER);
		this.builder = builder;
		this.context = context;
		buildJDKChooser();
	}

	private void buildJDKChooser() {

		panel = new SdkSettingsStep(context, builder.getCrafterSiteModuleBuilder(), id -> JavaSdk
				.getInstance() == id, null);

//		final ProjectSdksModel sdksModel = new ProjectSdksModel();
//		final JPanel myJdkPanel = new JPanel(new GridBagLayout());
		SDKPanel.add(panel.getComponent(), new GridConstraints());
//		final Project myProject = ProjectManager.getInstance().getDefaultProject();
//		sdksModel.reset(myProject);
//
//		myCbProjectJdk = new JdkComboBox(sdksModel);
//		myCbProjectJdk.insertItemAt(new JdkComboBox.NoneJdkComboBoxItem(), 0);
//		myJdkPanel.add(myCbProjectJdk, new GridBagConstraints(0, 1, 1, 1, 0, 1.0, NORTHWEST, NONE, JBUI.insetsLeft(4), 0, 0));
//		final JButton setUpButton = new JButton(ApplicationBundle.message("button.new"));
//		myCbProjectJdk.setSetupButton(setUpButton, myProject, sdksModel, new JdkComboBox.NoneJdkComboBoxItem(), null, false);
//		myJdkPanel.add(setUpButton, new GridBagConstraints(1, 1, 1, 1, 0, 0, WEST, NONE, JBUI.insetsLeft(4), 0, 0));
	}

	public Library selectedLib(){
		return cmbCrafterSdk.selectedLib();
	}


	public void updateDataModel() throws ConfigurationException {
		panel.validate();
		panel.updateDataModel();
	}
}
