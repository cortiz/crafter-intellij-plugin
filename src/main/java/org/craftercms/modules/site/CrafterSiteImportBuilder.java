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

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.IconLoader;
import com.intellij.packaging.artifacts.ModifiableArtifactModel;
import com.intellij.projectImport.ProjectImportBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class CrafterSiteImportBuilder extends ProjectImportBuilder {

	private Library selectedCrafterLibrary;

	private CrafterSiteModuleBuilder crafterSiteModuleBuilder;


	public CrafterSiteImportBuilder() {
		crafterSiteModuleBuilder = new CrafterSiteModuleBuilder();
	}

	@NotNull
	@Override
	public String getName() {
		return "Crafter CMS Site";
	}

	@Override
	public Icon getIcon() {
		 return IconLoader.findIcon("/images/crafter.png", this.getClass().getClassLoader());
	}

	@Override
	public List getList() {
		return null;
	}

	@Override
	public boolean isMarked(final Object element) {
		return false;
	}

	@Override
	public void setList(final List list) throws ConfigurationException {

	}

	@Override
	public void setOpenProjectSettingsAfter(final boolean on) {

	}

	@Nullable
	@Override
	public List<Module> commit(final Project project,
							   final ModifiableModuleModel model,
							   final ModulesProvider modulesProvider,
							   final ModifiableArtifactModel artifactModel) {

		final List<Module> modules = crafterSiteModuleBuilder.commit(project, model, modulesProvider);
		for (Module module : modules) {
			ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
			final ModifiableRootModel modifiableModel = moduleRootManager.getModifiableModel();
			ApplicationManager.getApplication().runWriteAction(() -> {
				modifiableModel.addLibraryEntry(selectedCrafterLibrary);
				modifiableModel.setSdk(ProjectRootManager.getInstance(project).getProjectSdk());
				modifiableModel.commit();
				module.getProject().save();
			});
		}
		return modules;
	}

	public void setCrafterLibrary(final Library library) {
		 selectedCrafterLibrary = library;
	}

	public CrafterSiteModuleBuilder getCrafterSiteModuleBuilder() {
		return crafterSiteModuleBuilder;
	}


}
