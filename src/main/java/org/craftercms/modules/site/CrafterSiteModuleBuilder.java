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

package org.craftercms.modules.site;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleBuilderListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CrafterSiteModuleBuilder<T>  extends ModuleBuilder implements ModuleBuilderListener {

	@Override
	public void setupRootModel(final ModifiableRootModel modifiableRootModel) throws ConfigurationException {
		doAddContentEntry(modifiableRootModel);
	}


	@Nullable
	@Override
	public Module commitModule(@NotNull Project project, @Nullable ModifiableModuleModel model) {
		readSiteName(project.getBaseDir());
		Module module = super.commitModule(project, model);
		if (module != null) {
			doGenerate(module);
		}
		return module;
	}

	private void readSiteName(final VirtualFile baseDir) {
		final VirtualFile siteConfig = baseDir.findFileByRelativePath("config/studio/site-config.xml");
		if (siteConfig!=null && siteConfig.exists()){
			try {
				SAXBuilder saxBuilder = new SAXBuilder();
				final Document doc = saxBuilder.build(siteConfig.getInputStream());
				setName(doc.getRootElement().getChild("wem-project").getValue());
			} catch (IOException | JDOMException e) {
				e.printStackTrace();
			}

		}
	}


	private void doGenerate(final Module module) {
		ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
		VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
		final ModifiableRootModel modifiableModel = moduleRootManager.getModifiableModel();
		VirtualFile dir = module.getProject().getBaseDir();
		if (contentRoots.length > 0 && contentRoots[0] != null) {
			dir = contentRoots[0];
		}
		if (CrafterSiteProject.isCrafterSiteProject(dir)){
			markFolderAsSource(dir, modifiableModel, "scripts/rest");
			markFolderAsSource(dir, modifiableModel, "classes");// 2.5 support.
			markFolderAsSource(dir, modifiableModel, "scripts/pages");
			markFolderAsSource(dir, modifiableModel, "scripts/components");
			markFolderAsSource(dir, modifiableModel, "scripts/classes");
			markFolderAsSource(dir, modifiableModel, "scripts/filters");
			markFolderAsSource(dir, modifiableModel, "scripts/controllers");
			commitModel(module, modifiableModel);

		}
	}

	protected  void markFolderAsSource(final VirtualFile parent, final ModifiableRootModel modifiableModel, final String sourceFolderToMark){
		final VirtualFile folderToMark = parent.findFileByRelativePath(sourceFolderToMark);
		if (folderToMark!=null && folderToMark.exists()) {
			ContentEntry contentEntry = CrafterSiteProject.findContentEntry(modifiableModel, folderToMark);
			if (contentEntry == null) {
				contentEntry = doAddContentEntry(modifiableModel);
			}
			contentEntry.addSourceFolder(folderToMark, JavaSourceRootType.SOURCE);
		}
	}

	protected void commitModel(@NotNull Module module, ModifiableRootModel model) {
		ApplicationManager.getApplication().runWriteAction(() -> {
			model.commit();
			module.getProject().save();
		});
	}

	@Override
	public ModuleType getModuleType() {
		return CrafterSiteModuleType.getInstance();
	}

	@Override
	public boolean isTemplateBased() {
		return true;
	}

	@Override
	public String getGroupName() {
		return "Crafter CMS";
	}

	@Override
	public void moduleCreated(@NotNull final Module module) {
		final ModifiableRootModel modifiableRootModel = ModuleRootManager.getInstance(module).getModifiableModel();
		final ContentEntry contentEntry = doAddContentEntry(modifiableRootModel);

	}


}
