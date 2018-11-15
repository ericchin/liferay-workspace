package com.liferay.ehcache.configuration;

import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.configurator.PortalCacheConfiguratorSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Chin
 */
@Component(
	property = {
		"portal.cache.manager.name=" + PortalCacheManagerNames.MULTI_VM
	},
	service = PortalCacheConfiguratorSettings.class
)
public class MultiVMCacheConfiguratorSettings
	extends PortalCacheConfiguratorSettings {

	public MultiVMCacheConfiguratorSettings() {
		super(
			MultiVMCacheConfiguratorSettings.class.getClassLoader(),
			"ehcache/liferay-multi-vm-clustered-ext.xml");
	}

}