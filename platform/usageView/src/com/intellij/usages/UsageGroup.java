// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.usages;

import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public interface UsageGroup extends Comparable<UsageGroup>, Navigatable {

  /**
   * @deprecated implement {@link #getIcon()} instead
   */
  @ScheduledForRemoval(inVersion = "2022.1")
  @Deprecated
  default @Nullable Icon getIcon(@SuppressWarnings("unused") boolean isOpen) {
    return null;
  }

  default @Nullable Icon getIcon() {
    return getIcon(true);
  }

  @NlsContexts.ListItem @NotNull String getText(@Nullable UsageView view);

  default @Nullable FileStatus getFileStatus() {
    return null;
  }

  default boolean isValid() {
    return true;
  }

  default void update() {
  }
}
