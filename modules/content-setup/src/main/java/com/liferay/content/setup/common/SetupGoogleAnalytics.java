package com.liferay.content.setup.common;

import com.liferay.content.setup.constants.ContentSetupConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Eric Chin
 */
public class SetupGoogleAnalytics extends UpgradeProcess {

	public SetupGoogleAnalytics(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long companyId = PortalUtil.getDefaultCompanyId();

		String[] groupKeys = PropsUtil.getArray(
			ContentSetupConstants.PROPERTIES_GROUP_KEYS);

		String defaultGoogleAnalyticsId = PropsUtil.get(
			ContentSetupConstants.PROPERTIES_DEFAULT_GOOGLE_ANALYTICS_ID);

		for (String groupKey : groupKeys) {
			if (groupKey.equals(ContentSetupConstants.GROUP_GLOBAL)) {
				if (_log.isInfoEnabled()) {
					_log.info("Skipping Global group");
				}

				continue;
			}

			Group group = _groupLocalService.fetchGroup(companyId, groupKey);

			if (Objects.isNull(group)) {
				if (_log.isInfoEnabled()) {
					_log.info("Group with groupKey " + groupKey + " is null");
				}

				continue;
			}

			UnicodeProperties typeSettingsProperties =
				group.getTypeSettingsProperties();

			typeSettingsProperties.setProperty(
				"googleAnalyticsId", defaultGoogleAnalyticsId);

			group = _groupLocalService.updateGroup(
				group.getGroupId(), typeSettingsProperties.toString());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Updated " + group.getName(Locale.getDefault()) +
						" with Google Analytics ID " +
						defaultGoogleAnalyticsId);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SetupGoogleAnalytics.class);

	private GroupLocalService _groupLocalService;

}
