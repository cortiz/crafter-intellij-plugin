/*
 * Copyright (C) 2007-2018 Carlos Ortiz. All rights reserved.
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

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.options.ConfigurationException;
import org.craftercms.modules.site.CrafterSiteImportBuilder;

import javax.swing.*;

public class CrafterSiteLibSelectionWizardStep extends ModuleWizardStep {

	private final CrafterSitePanel crafterPanel;
	private final CrafterSiteImportBuilder builder;

	public CrafterSiteLibSelectionWizardStep(CrafterSiteImportBuilder builder, final WizardContext context) {
		crafterPanel = new CrafterSitePanel(builder, context);
		this.builder = builder;
	}

	@Override
	public JComponent getComponent() {
		 return crafterPanel;
	}

	@Override
	public void updateDataModel() {
		try {
			crafterPanel.updateDataModel();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		builder.setCrafterLibrary(crafterPanel.selectedLib());
	}



}
