// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.jetbrains.kotlin.idea.caches.project

import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.analyzer.ModuleInfo
import org.jetbrains.kotlin.caches.project.cacheInvalidatingOnRootModifications
import org.jetbrains.kotlin.idea.util.application.getServiceSafe

/**
 * Maintains and caches mapping ModuleInfo -> SdkInfo *form its dependencies*
 * (note that this SDK might be different from Project SDK)
 *
 * Cache is needed because one and the same library might (and usually does)
 * participate as dependency in several modules, so if someone queries a lot
 * of modules for their SDKs (ex.: determine built-ins for each module in a
 * project), we end up inspecting one and the same dependency multiple times.
 *
 * With projects with abnormally high amount of dependencies this might lead
 * to performance issues.
 */
interface SdkInfoCache {
    fun findOrGetCachedSdk(moduleInfo: ModuleInfo): SdkInfo?

    companion object {
        fun getInstance(project: Project): SdkInfoCache = project.getServiceSafe()
    }
}

class SdkInfoCacheImpl(private val project: Project) : SdkInfoCache {
    private val cache: MutableMap<ModuleInfo, SdkInfo?>
        // Note: it's ok to use thread-unsafe map, because 'doFindSdk' is pure
        get() = project.cacheInvalidatingOnRootModifications { mutableMapOf() }

    override fun findOrGetCachedSdk(moduleInfo: ModuleInfo): SdkInfo? {
        if (!cache.containsKey(moduleInfo)) {
            cache[moduleInfo] = doFindSdk(moduleInfo)
        }

        return cache[moduleInfo]
    }

    private fun doFindSdk(moduleInfo: ModuleInfo): SdkInfo? =
        moduleInfo.dependencies().lazyClosure { it.dependencies() }.firstOrNull { it is SdkInfo } as SdkInfo?
}