package com.liferay.content.setup.common;

import com.liferay.content.setup.ContentSetupPath;
import com.liferay.content.setup.constants.ContentSetupConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.deployment.WorkflowDeployer;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowModelParser;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowValidator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric Chin
 */
public class WorkflowDefinitionUtil {

	public static WorkflowDefinition deployWorkflowDefinition(
			WorkflowModelParser workflowModelParser,
			WorkflowValidator workflowValidator,
			WorkflowDeployer workflowDeployer,
			String filename)
		throws PortalException {

		long companyId = PortalUtil.getDefaultCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		StringBundler sb = new StringBundler(3);

		sb.append(ContentSetupConstants.RESOURCES_PATH);
		sb.append("/workflow/");
		sb.append(filename);

		try (InputStream inputStream =
				ContentSetupPath.class.getResourceAsStream(sb.toString())) {

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			Definition definition = workflowModelParser.parse(inputStream);

			if (workflowValidator != null) {
				workflowValidator.validate(definition);
			}

			WorkflowDefinition workflowDefinition = workflowDeployer.deploy(
				StringPool.BLANK, definition, serviceContext);

			return workflowDefinition;
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	public static List<WorkflowDefinition> deployWorkflowDefinitions(
			WorkflowModelParser workflowModelParser,
			WorkflowValidator workflowValidator,
			WorkflowDeployer workflowDeployer,
			String[] filenames)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = new ArrayList<>();

		for (String filename : filenames) {
			WorkflowDefinition workflowDefinition = deployWorkflowDefinition(
				workflowModelParser, workflowValidator, workflowDeployer,
				filename);

			workflowDefinitions.add(workflowDefinition);
		}

		return workflowDefinitions;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowDefinitionUtil.class);

}
