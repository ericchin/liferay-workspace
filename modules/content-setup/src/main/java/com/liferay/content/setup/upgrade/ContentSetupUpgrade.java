package com.liferay.content.setup.upgrade;

import com.liferay.content.setup.common.AddWorkflowDefinition;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.scripting.executor.provider.ScriptBundleProvider;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.definition.deployment.WorkflowDeployer;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowModelParser;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowValidator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ContentSetupUpgrade implements UpgradeStepRegistrator {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void register(Registry registry) {
		Bundle bundle = _scriptBundleProvider.getBundle();

		String bundleName = bundle.getSymbolicName();

		registry.register(
			bundleName, "0.0.0", "1.0.0",
			new AddWorkflowDefinition(
				_workflowModelParser, _workflowValidator, _workflowDeployer));
	}

	private BundleContext _bundleContext;

	@Reference
	private Portal _portal;

	@Reference(target = "(component.name=*ContentSetupDependencyManager)")
	private ScriptBundleProvider _scriptBundleProvider;

	@Reference
	private WorkflowDeployer _workflowDeployer;

	@Reference
	private WorkflowModelParser _workflowModelParser;

	@Reference
	private WorkflowValidator _workflowValidator;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}
