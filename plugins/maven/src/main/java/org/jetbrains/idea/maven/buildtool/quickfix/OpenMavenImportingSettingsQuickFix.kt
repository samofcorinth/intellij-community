// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.idea.maven.buildtool.quickfix

import com.intellij.build.issue.BuildIssueQuickFix
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import org.jetbrains.idea.maven.project.MavenImportingConfigurable
import java.util.concurrent.CompletableFuture

class OpenMavenImportingSettingsQuickFix : BuildIssueQuickFix {
  override val id: String = ID

  override fun runQuickFix(project: Project, dataContext: DataContext): CompletableFuture<*> {
    ApplicationManager.getApplication().invokeLater {
      ShowSettingsUtil.getInstance().showSettingsDialog(project, MavenImportingConfigurable::class.java)
    }
    return CompletableFuture.completedFuture(null)
  }

  companion object {
    const val ID = "open_maven_importing_settings_quick_fix"
  }
}