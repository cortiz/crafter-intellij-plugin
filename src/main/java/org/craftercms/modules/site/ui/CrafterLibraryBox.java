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

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileChooser.PathChooserDialog;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class CrafterLibraryBox extends ComboboxWithBrowseButton {

	public CrafterLibraryBox(){
		getComboBox().setRenderer(new ColoredListCellRenderer() {
			@Override
			protected void customizeCellRenderer(JList list, Object value, int index, boolean selected, boolean hasFocus) {
				if (value instanceof Library) {
					append(((Library) value).getName());
				} else {
					append("Select Crafter CMS SDK", SimpleTextAttributes.ERROR_ATTRIBUTES);
				}
			}
		});
		loadLibraries();
	}

	private void loadLibraries() {
		final LibraryTable libTable = LibraryTablesRegistrar.getInstance().getLibraryTable();
		getComboBox().setModel(new DefaultComboBoxModel(libTable.getLibraries()));
		getButton().addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				createNewLibAction(libTable);
			}
		});
	}

	private void createNewLibAction(final LibraryTable libTable) {
		FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true,
				false, false,
				false, false);
		descriptor.setTitle("Choose a Crafter SDK folder");
		PathChooserDialog pathChooser = FileChooserFactory.getInstance()
				.createPathChooser(descriptor, null, this);
		pathChooser.choose(VfsUtil.getUserHomeDir(), new FileChooser.FileChooserConsumer() {


			@Override
			public void consume(final List<VirtualFile> virtualFiles) {
				final VirtualFile crafterSKDHome = virtualFiles.get(0);
				final VirtualFile sdkConfig = crafterSKDHome.findFileByRelativePath("crafterSDK.json");
				if (sdkConfig== null  || !sdkConfig.exists() || sdkConfig.isDirectory()){
						showErrorMsg();
				}else {
					try {
						HashMap<String , Object> options = new Gson().fromJson(new FileReader(sdkConfig.getPath()),
								HashMap.class);
						Library crafterLib = libTable.createLibrary("Crafter CMS " +
								options.get("version") + "" +" SDK");
						for (VirtualFile virtualFile : crafterSKDHome.getChildren()) {
							if (virtualFile.getName().endsWith(".jar")){
								crafterLib.getModifiableModel().addRoot(virtualFile, OrderRootType.CLASSES);
							}
						}
						ApplicationManager.getApplication().runWriteAction(() -> {
							crafterLib.getModifiableModel().commit();
						});

					} catch (FileNotFoundException  e) {
						showErrorMsg();
					}
				}
			}
			@Override
			public void cancelled() {
			}
		});
		loadLibraries();
	}

	private void showErrorMsg() {
		final DialogBuilder errorDialog = new DialogBuilder(getParent().getParent());
		errorDialog.setErrorText("Invalid Crafter SDK Folder");
		errorDialog.setTitle("Error");
		errorDialog.addCloseButton();
		errorDialog.show();
	}

	public Library selectedLib(){
		return (Library) this.getComboBox().getSelectedItem();
	}

}
