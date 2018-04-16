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

import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootModel;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrafterSiteProject {

	public static final List<String> CRAFTER_SITE_PATHS = Collections.unmodifiableList(
			Arrays.asList("templates","static-assets", "scripts","config", "site"));

	public static final String CRAFTER_SITE_FRAMEWORK = "CRAFTER_SITE_FRAMEWORK";
	public static final String CRAFTER_SITE_MODULE_ID = "CRAFTER_SITE";


	public static boolean isCrafterSiteProject(VirtualFile root){
		List<String> subdirPaths = new ArrayList<>();
		for (VirtualFile child : root.getChildren()) {
			subdirPaths.add(child.getName());
		}
		return subdirPaths.containsAll(CRAFTER_SITE_PATHS);
	}

	@Nullable
	public static ContentEntry findContentEntry(@NotNull ModuleRootModel model, @NotNull VirtualFile vFile) {
		final ContentEntry[] contentEntries = model.getContentEntries();
		for (ContentEntry contentEntry : contentEntries) {
			final VirtualFile contentEntryFile = contentEntry.getFile();
			if (contentEntryFile != null && VfsUtilCore.isAncestor(contentEntryFile, vFile, false)) {
				return contentEntry;
			}
		}
		return null;
	}

	public static Icon crafterCMSIcon(){
		return IconLoader.findIcon("/images/crafter.png",
			CrafterSiteProject.class.getClassLoader());
	}
}
