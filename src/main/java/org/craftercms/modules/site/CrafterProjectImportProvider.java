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

package org.craftercms.modules.site;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SdkSettingsStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.projectImport.ProjectImportProvider;
import org.craftercms.modules.site.ui.CrafterSiteLibSelectionWizardStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.craftercms.modules.site.CrafterSiteProject.isCrafterSiteProject;

public class CrafterProjectImportProvider extends ProjectImportProvider
{

	protected CrafterProjectImportProvider() {
		super(new CrafterSiteImportBuilder());
	}

	@Override
	public boolean canImport(@NotNull final VirtualFile fileOrDirectory, @Nullable final Project project) {
		if (fileOrDirectory.isDirectory()){
			return isCrafterSiteProject(fileOrDirectory);
		}
		return canImportFromFile(fileOrDirectory);
	}

	@Override
	protected boolean canImportFromFile(final VirtualFile file) {
		return false;
	}

	@Override
	public ModuleWizardStep[] createSteps(final WizardContext context) {
			return new ModuleWizardStep[]{
					new CrafterSiteLibSelectionWizardStep((CrafterSiteImportBuilder) myBuilder,context)};
	}

}
