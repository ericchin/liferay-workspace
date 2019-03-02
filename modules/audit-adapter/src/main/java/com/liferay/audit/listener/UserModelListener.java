package com.liferay.audit.listener;

import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 *
 * public AuditMessage(
 * 		String eventType, long companyId, long userId, String userName,
 * 		String className, String classPK, String message,
 * 		JSONObject additionalInfoJSONObject) {
 *
 * 	AuditMessage auditMessage = new AuditMessage(
 * 		ActionKeys.VIEW, user.getCompanyId(), user.getUserId(),
 * 		user.getFullName(), Layout.class.getName(),
 * 		String.valueOf(layout.getPlid()));
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onBeforeCreate(User user) throws ModelListenerException {

	}

	@Override
	public void onBeforeRemove(User user) throws ModelListenerException {

	}

	@Override
	public void onBeforeUpdate(User user) throws ModelListenerException {
		User oldUser = _userLocalService.fetchUser(user.getUserId());

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			JSONObject oldUserJSON =
				_jsonFactory.createJSONObject(_jsonFactory.serialize(oldUser));

			JSONObject newUserJSON = _jsonFactory.createJSONObject(
				_jsonFactory.serialize(user));

			jsonObject.put("old", oldUserJSON);
			jsonObject.put("new", newUserJSON);

			AuditMessage auditMessage = _createAuditMessage(
				ActionKeys.UPDATE, user, jsonObject);

			_routeAuditMessage(auditMessage);
		}
		catch (Exception e) {
			_log.error(e.getMessage());

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	private AuditMessage _createAuditMessage(
		String actionKey, User user, JSONObject additionalJSONObject) {

		long currentUserId = PrincipalThreadLocal.getUserId();
		long companyId = CompanyThreadLocal.getCompanyId();

		AuditMessage auditMessage = new AuditMessage(
			actionKey, companyId, currentUserId, user.getFullName(),
			User.class.getName(), String.valueOf(user.getUserId()),
			StringPool.BLANK, additionalJSONObject);

		return auditMessage;
	}

	private void _routeAuditMessage(AuditMessage auditMessage)
		throws AuditException {

		AuditRouterUtil.route(auditMessage);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

}
