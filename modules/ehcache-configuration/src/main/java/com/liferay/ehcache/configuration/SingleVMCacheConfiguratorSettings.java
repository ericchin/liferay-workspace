package com.liferay.ehcache.configuration;

import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.configurator.PortalCacheConfiguratorSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Chin
 */
@Component(
	property = {
		"portal.cache.manager.name=" + PortalCacheManagerNames.SINGLE_VM
	},
	service = PortalCacheConfiguratorSettings.class
)
public class SingleVMCacheConfiguratorSettings
	extends PortalCacheConfiguratorSettings {

	public SingleVMCacheConfiguratorSettings() {
		super(
			SingleVMCacheConfiguratorSettings.class.getClassLoader(),
			"ehcache/liferay-single-vm-ext.xml");
	}

}