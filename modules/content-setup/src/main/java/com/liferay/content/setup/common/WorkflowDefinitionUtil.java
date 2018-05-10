package com.liferay.content.setup.common;

import com.liferay.content.setup.ContentSetupPath;
import com.liferay.content.setup.constants.ContentSetupConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;

import java.io.InputStream;

/**
 * @author Eric Chin
 */
public class WorkflowDefinitionUtil {

	public static WorkflowDefinition deployWorkflowDefinition(String filename)
		throws PortalException {

		long companyId = PortalUtil.getDefaultCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		StringBundler sb = new StringBundler(3);

		sb.append(ContentSetupConstants.RESOURCES_PATH);
		sb.append("/workflow/");
		sb.append(filename);

		try (InputStream inputStream =
				ContentSetupPath.class.getResourceAsStream(sb.toString())) {

			byte[] bytes = FileUtil.getBytes(inputStream);

			return WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
				companyId, userId, StringPool.BLANK, bytes);
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowDefinitionUtil.class);

}
