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

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static org.craftercms.modules.site.CrafterSiteProject.CRAFTER_SITE_MODULE_ID;

public class CrafterSiteModuleType extends ModuleType<CrafterSiteModuleBuilder> {


	public CrafterSiteModuleType() {
		super(CRAFTER_SITE_MODULE_ID);
	}

	public static CrafterSiteModuleType getInstance() {
		return (CrafterSiteModuleType) ModuleTypeManager.getInstance().findByID(CRAFTER_SITE_MODULE_ID);
	}

	public static ModuleType getModuleType() {
		return ModuleTypeManager.getInstance().findByID(CRAFTER_SITE_MODULE_ID);
	}

	@NotNull
	@Override
	public CrafterSiteModuleBuilder createModuleBuilder() {
		return new CrafterSiteModuleBuilder();
	}



	@NotNull
	@Override
	public String getName() {
		return "Crafter CMS";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Crafter CMS site";
	}

	@Override
	public Icon getBigIcon() {
		return 	getNodeIcon(false);
	}


	@Override
	public Icon getNodeIcon(final boolean isOpened) {
		return CrafterSiteProject.crafterCMSIcon();
	}
}
