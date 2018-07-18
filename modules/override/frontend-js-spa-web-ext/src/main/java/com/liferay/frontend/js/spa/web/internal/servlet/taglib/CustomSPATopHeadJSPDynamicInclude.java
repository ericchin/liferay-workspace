package com.liferay.frontend.js.spa.web.internal.servlet.taglib;

import com.liferay.frontend.js.spa.web.internal.servlet.taglib.util.SPAUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Props;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Eric Chin
 */
@Component(immediate = true, service = DynamicInclude.class)
public class CustomSPATopHeadJSPDynamicInclude
	extends SPATopHeadJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		_log.info(
			">>> Running " + CustomSPATopHeadJSPDynamicInclude.class.getName());

		// TODO: add condition here to determine whether to include SPA code

		super.include(request, response, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		super.register(dynamicIncludeRegistry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomSPATopHeadJSPDynamicInclude.class);

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setSPAUtil(SPAUtil spaUtil) {
		super.setSPAUtil(spaUtil);
	}

	@Override
	protected void unsetSPAUtil(SPAUtil spaUtil) {
		unsetSPAUtil(spaUtil);
	}

	@Reference
	protected void setHtml(Html html) {
		super.setHtml(html);
	}

	@Reference
	protected void setLanguage(Language language) {
		super.setLanguage(language);
	}

	@Reference
	protected void setProps(Props props) {
		super.setProps(props);
	}

}
