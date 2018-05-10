package com.liferay.content.setup.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Eric Chin
 */
public class ContentSetupUpgrade_1_0_0 extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(AddWorkflowDefinition.class);
	}

}
