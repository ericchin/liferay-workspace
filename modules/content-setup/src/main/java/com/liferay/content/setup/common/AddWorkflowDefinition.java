package com.liferay.content.setup.common;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.workflow.kaleo.definition.deployment.WorkflowDeployer;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowModelParser;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowValidator;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Eric Chin
 */
public class AddWorkflowDefinition extends UpgradeProcess {

	public AddWorkflowDefinition(
		WorkflowModelParser workflowModelParser,
		WorkflowValidator workflowValidator,
		WorkflowDeployer workflowDeployer) {

		_workflowModelParser = workflowModelParser;

		_workflowValidator = workflowValidator;

		_workflowDeployer = workflowDeployer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		WorkflowDefinitionUtil.deployWorkflowDefinition(
			_workflowModelParser, _workflowValidator, _workflowDeployer,
			"single-approver-test.xml");
	}

	private WorkflowModelParser _workflowModelParser;

	private WorkflowValidator _workflowValidator;

	private WorkflowDeployer _workflowDeployer;

	private BundleContext _bundleContext;

	private ServiceTracker<WorkflowDefinitionManager, WorkflowDefinitionManager> _serviceTracker;

}
