package com.liferay.content.setup.upgrade.v1_0_0;

import com.liferay.content.setup.common.WorkflowDefinitionUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Eric Chin
 */
public class AddWorkflowDefinition extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		WorkflowDefinitionUtil.deployWorkflowDefinition(
			"single-approver-test.xml");
	}

}
