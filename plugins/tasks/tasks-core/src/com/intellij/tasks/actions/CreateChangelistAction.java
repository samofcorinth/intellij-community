/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.intellij.tasks.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.ui.Messages;
import com.intellij.tasks.LocalTask;
import com.intellij.tasks.TaskManager;
import com.intellij.tasks.impl.TaskManagerImpl;
import com.intellij.tasks.impl.TaskUtil;

/**
 * @author Dmitry Avdeev
 */
public class CreateChangelistAction extends BaseTaskAction {

  @Override
  public void update(AnActionEvent event) {
    TaskManager manager = getTaskManager(event);
    Presentation presentation = event.getPresentation();

    if (manager == null || !manager.isVcsEnabled() || !manager.getOpenChangelists(manager.getActiveTask()).isEmpty()) {
      presentation.setText(getTemplatePresentation().getText());
      presentation.setEnabled(false);
    } else {
      presentation.setText("Create changelist for '" + TaskUtil.getTrimmedSummary(manager.getActiveTask()) + "'");
      presentation.setEnabled(true);
    }
    super.update(event);
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    LocalTask activeTask = getActiveTask(e);
    TaskManagerImpl manager = (TaskManagerImpl)getTaskManager(e);
    assert manager != null;
    String name = Messages.showInputDialog(getProject(e), "Changelist name:", "Create Changelist", null, manager.getChangelistName(activeTask), null);
    if (name != null) {
      manager.createChangeList(activeTask, name);
    }
  }
}
