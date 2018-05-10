package com.liferay.content.setup;

import com.liferay.portal.scripting.executor.provider.ScriptBundleProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eric Chin
 */
@Component(immediate = true, service = ScriptBundleProvider.class)
public class ContentSetupDependencyManager implements ScriptBundleProvider {

	@Override
	public Bundle getBundle() {
		return _bundleContext.getBundle();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
	}

	private BundleContext _bundleContext;

}
