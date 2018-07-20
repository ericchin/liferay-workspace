package com.liferay.content.setup.common;

import com.liferay.content.setup.ContentSetupPath;
import com.liferay.content.setup.constants.ContentSetupConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eric Chin
 */
public class DocumentTypeUtil {

	public static void addDocumentType(
			String filename, String name, DDM ddm,
			DDMBeanTranslator ddmBeanTranslator,
			DLFileEntryTypeLocalService dlFileEntryTypeLocalService)
		throws Exception {

		long companyId = PortalUtil.getDefaultCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Company company = CompanyLocalServiceUtil.fetchCompany(companyId);

		if (Objects.isNull(company)) {
			return;
		}

		Group globalGroup = company.getGroup();

		if (Objects.isNull(globalGroup)) {
			return;
		}

		StringBundler sb = new StringBundler(3);

		sb.append(ContentSetupConstants.RESOURCES_PATH);
		sb.append("/document_type/");
		sb.append(filename);

		Map<Locale, String> nameMap = new HashMap<>();

		if (Validator.isNull(name)) {
			name = FileUtil.stripExtension(filename);
		}

		nameMap.put(Locale.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		long[] ddmStructureIds = new long[0];

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(PortalUUIDUtil.generate());

		try (InputStream inputStream =
				ContentSetupPath.class.getResourceAsStream(sb.toString())) {

			String json = StringUtil.read(inputStream);

			DDMForm ddmForm = ddmBeanTranslator.translate(ddm.getDDMForm(json));

			serviceContext.setAttribute("ddmForm", ddmForm);

			String key = getFileEntryTypeKey(filename);

			DLFileEntryType dlFileEntryType =
				dlFileEntryTypeLocalService.fetchFileEntryType(
					globalGroup.getGroupId(), key);

			if (Objects.isNull(dlFileEntryType)) {
				dlFileEntryType = dlFileEntryTypeLocalService.addFileEntryType(
					userId, globalGroup.getGroupId(), key, nameMap,
					descriptionMap, ddmStructureIds, serviceContext);
			}
			else {
				dlFileEntryTypeLocalService.updateFileEntryType(
					userId, dlFileEntryType.getFileEntryTypeId(), nameMap,
					descriptionMap, ddmStructureIds, serviceContext);
			}

			if (!Objects.isNull(dlFileEntryType)) {
				_log.info(
					"File entry type {" + dlFileEntryType.getFileEntryTypeId() +
						", " + dlFileEntryType.getName(Locale.getDefault()) +
						"}");
			}
		}
	}

	protected static String getFileEntryTypeKey(String filename) {
		filename = FileUtil.stripExtension(filename);
		filename = StringUtil.upperCase(filename);
		filename = StringUtil.replace(
			filename, StringPool.DASH, StringPool.UNDERLINE);

		return filename;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentTypeUtil.class);

}
