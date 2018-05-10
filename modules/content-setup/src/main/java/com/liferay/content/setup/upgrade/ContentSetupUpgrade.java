package com.liferay.content.setup.upgrade;

import com.liferay.content.setup.upgrade.v1_0_0.ContentSetupUpgrade_1_0_0;
import com.liferay.portal.scripting.executor.provider.ScriptBundleProvider;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ContentSetupUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		Bundle bundle = _scriptBundleProvider.getBundle();

		String bundleName = bundle.getSymbolicName();

		registry.register(
			bundleName, "0.0.0", "1.0.0", new ContentSetupUpgrade_1_0_0());
	}

	@Reference(target = "(component.name=*ContentSetupDependencyManager)")
	private ScriptBundleProvider _scriptBundleProvider;

}
