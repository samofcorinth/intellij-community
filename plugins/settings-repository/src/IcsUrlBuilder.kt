// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.settingsRepository

import com.intellij.openapi.components.RoamingType
import com.intellij.openapi.util.SystemInfo

private val OS_PREFIXES = arrayOf("_mac/", "_windows/", "_linux/", "_freebsd/", "_unix/")

internal fun getOsFolderName() = when {
  SystemInfo.isMac -> "_mac"
  SystemInfo.isWindows -> "_windows"
  SystemInfo.isLinux -> "_linux"
  SystemInfo.isFreeBSD -> "_freebsd"
  SystemInfo.isUnix -> "_unix"
  else -> "_unknown"
}

internal fun toRepositoryPath(path: String, roamingType: RoamingType): String {
  return if (roamingType == RoamingType.PER_OS) "${getOsFolderName()}/$path" else path
}

internal fun toIdeaPath(path: String): String {
  for (prefix in OS_PREFIXES) {
    val result = path.removePrefix(prefix)
    if (result !== path) {
      return result
    }
  }
  return path
}